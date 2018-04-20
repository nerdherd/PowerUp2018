package com.team687.frc2018.commands.drive;

import java.util.ArrayList;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.DriveConstants;
import com.team687.frc2018.utilities.BezierCurve;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive a path generated from Bezier curve
 */

public class DriveBezierPath extends Command {

    private BezierCurve m_path;
    private double m_straightPower;
    private final double m_kRotP, m_kDistP;
    private boolean m_softStop;

    private ArrayList<Double> m_headingList, m_arcLengthList;
    private double m_desiredHeading;

    private int m_counter;
    private boolean m_pathIsFinished;
    private double m_direction;

    public DriveBezierPath(double[] path, double straightPower, double kRotP, double kDistP, boolean softStop) {
	m_path = new BezierCurve(path[0], path[1], path[2], path[3], path[4], path[5], path[6], path[7]);
	m_straightPower = straightPower;
	m_kRotP = kRotP;
	m_kDistP = kDistP;
	m_softStop = softStop;

	requires(Robot.drive);
    }

    /**
     * @param path
     *            (BezierCurve)
     * @param straightPower
     *            (keep this under 0.75)
     * @param kRotP
     * @param kDistP
     *            (the higher the distance kP is, the less time to declerate)
     * @param softStop
     *            (if you want to slow down near end)
     */
    public DriveBezierPath(BezierCurve path, double straightPower, double kRotP, double kDistP, boolean softStop) {
	m_path = path;
	m_straightPower = straightPower;
	m_kRotP = kRotP;
	m_kDistP = kDistP;
	m_softStop = softStop;

	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "DriveBezierPath");
	Robot.drive.stopDrive();
	Robot.drive.resetEncoders();

	m_path.calculateBezier();
	m_headingList = m_path.getHeadingList();
	m_arcLengthList = m_path.getArcLengthList();

	m_counter = 0;
	m_pathIsFinished = false;
	m_direction = Math.signum(m_straightPower);
    }

    @Override
    protected void execute() {
	if (m_counter < m_arcLengthList.size()) {
	    if (Math.abs(Robot.drive.getDrivetrainPosition()) < m_arcLengthList.get(m_counter)) {
		double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
		m_desiredHeading = m_headingList.get(m_counter);

		// going reverse
		if (m_direction < 0) {
		    m_desiredHeading += 180;
//		    if (m_desiredHeading > 0)	{
//		    	m_desiredHeading -= 180;
//		    }
//		    else if	(m_desiredHeading < 0)	{
//		    	m_desiredHeading += 180;
		    	
//		    }
		    
		}
		// change in sign is necessary because of how P loop is structured
		m_desiredHeading = -m_desiredHeading;

		double rotError = m_desiredHeading - robotAngle;
		rotError = (rotError > 180) ? rotError - 360 : rotError;  //possible fix for spinny problems
		rotError = (rotError > 180) ? rotError - 360 : rotError;
		rotError = (rotError < -180) ? rotError + 360 : rotError;
		rotError = (rotError < -180) ? rotError + 360 : rotError;
//		rotError = rotError % 360;
//		rotError = (rotError + 360) % 360;
//		if (rotError >= 180)	{
//			rotError = rotError - 360;
//		}

		double rotPower = m_kRotP * rotError;
		Robot.drive.updateBezierData(m_desiredHeading, rotError, rotPower);
		// default is specified straight power
		double straightPower = m_straightPower;
		double maxStraightPower = Math.abs(m_straightPower);
		if (m_softStop) {
		    double straightError = m_arcLengthList.get(m_arcLengthList.size() - 1)
			    - Math.abs(Robot.drive.getDrivetrainPosition());
		    double newMaxStraightPower = m_kDistP * straightError;
		    maxStraightPower = Math.min(Math.abs(maxStraightPower), Math.abs(newMaxStraightPower));
		}

		// limit straight power to maintain rotPower to straightPower ratio
		// also for soft stops
		if (Math.abs(straightPower) > maxStraightPower) {
		    straightPower = maxStraightPower * m_direction;
		}

		// make sure robot reaches end point
		if (Math.abs(straightPower) < DriveConstants.kMinStraightPower) {
		    straightPower = DriveConstants.kMinStraightPower * m_direction;
		}

		double leftPow = (DriveConstants.kLeftAdjustment * straightPower) - rotPower;
		double rightPow = straightPower + rotPower;
		Robot.drive.setPower(leftPow, rightPow);
	    } else {
		m_counter++;
	    }
	} else {
	    m_pathIsFinished = true;
	}
    }

    @Override
    protected boolean isFinished() {
	return m_pathIsFinished || Math.abs(Robot.drive.getDrivetrainPosition()) > Math
		.abs(m_arcLengthList.get(m_arcLengthList.size() - 1));
    }

    @Override
    protected void end() {
	Robot.drive.stopDrive();
    }

    @Override
    protected void interrupted() {
	end();
    }

}
