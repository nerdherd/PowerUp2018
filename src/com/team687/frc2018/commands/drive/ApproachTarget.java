package com.team687.frc2018.commands.drive;

import com.team687.frc2018.Robot;
import com.team687.frc2018.VisionAdapter;
import com.team687.frc2018.constants.DriveConstants;
import com.team687.frc2018.utilities.NerdyMath;
import com.team687.frc2018.utilities.PGains;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Approach a target based on vision and gyro. Used with NerdyVision.
 */

public class ApproachTarget extends Command {

    private double m_distance;
    private double m_straightPower;
    private double m_startTime, m_timeout;
    private boolean m_softStop;
    private boolean m_isHighGear;

    private PGains m_leftPGains, m_rightPGains;
    private PGains m_rotPGains;

    public ApproachTarget(double distance, double straightPower, boolean softStop, boolean isHighGear) {
	m_distance = distance;
	m_straightPower = straightPower;
	m_timeout = 6.87;
	m_softStop = softStop;
	m_isHighGear = isHighGear;

	requires(Robot.drive);
    }

    /**
     * @param distance
     * @param straightPower
     * @param softStop
     * @param isHighGear
     * @param timeout
     */
    public ApproachTarget(double distance, double straightPower, boolean softStop, boolean isHighGear, double timeout) {
	m_distance = distance;
	m_straightPower = straightPower;
	m_timeout = timeout;
	m_softStop = softStop;
	m_isHighGear = isHighGear;

	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "ApproachTarget");

	Robot.drive.stopDrive();

	if (m_isHighGear) {
	    Robot.drive.shiftUp();
	    m_rightPGains = DriveConstants.kDistHighGearRightPGains;
	    m_leftPGains = DriveConstants.kDistHighGearLeftPGains;
	    m_rotPGains = DriveConstants.kRotHighGearPGains;
	} else if (!m_isHighGear) {
	    Robot.drive.shiftDown();
	    m_rightPGains = DriveConstants.kDistLowGearRightPGains;
	    m_leftPGains = DriveConstants.kDistLowGearLeftPGains;
	    m_rotPGains = DriveConstants.kRotLowGearPGains;
	}

	m_startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
	double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
	double relativeAngleError = VisionAdapter.getInstance().getAngleToTurn();
	double processingTime = VisionAdapter.getInstance().getProcessedTime();
	double absoluteDesiredAngle = relativeAngleError + Robot.drive.timeMachineYaw(processingTime);
	double rotError = absoluteDesiredAngle - robotAngle;
	rotError = (rotError > 180) ? rotError - 360 : rotError;
	rotError = (rotError < -180) ? rotError + 360 : rotError;
	double rotPower = m_rotPGains.getP() * rotError;
	if (Math.abs(rotError) <= DriveConstants.kDriveRotationDeadband) {
	    rotPower = 0;
	}

	double straightRightPower = m_straightPower; // default
	double straightLeftPower = m_straightPower;
	double straightRightError = m_distance - Robot.drive.getRightPosition();
	double straightLeftError = m_distance - Robot.drive.getLeftPosition();

	if (m_softStop) {
	    straightRightPower = m_rightPGains.getP() * straightRightError;
	    straightLeftPower = m_leftPGains.getP() * straightLeftError;
	}
	straightRightPower = NerdyMath.threshold(straightRightPower, m_rightPGains.getMinPower(),
		m_rightPGains.getMaxPower());
	straightLeftPower = NerdyMath.threshold(straightLeftPower, m_leftPGains.getMinPower(),
		m_rightPGains.getMaxPower());

	Robot.drive.setPower(rotPower + straightLeftPower, rotPower - straightRightPower);
    }

    @Override
    protected boolean isFinished() {
	return Timer.getFPGATimestamp() - m_startTime > m_timeout || Robot.drive.getDrivetrainPosition() > m_distance;
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
