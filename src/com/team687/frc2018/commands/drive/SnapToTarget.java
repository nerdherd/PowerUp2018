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
 * Alignment based on vision and gyro. Ends when the shoot button is released
 */

public class SnapToTarget extends Command {

    private double m_startTime, m_timeout;
    private int m_counter;
    private boolean m_isHighGear;

    private PGains m_rotPGains;

    public SnapToTarget(boolean isHighGear) {
	m_timeout = 3.3;
	m_isHighGear = isHighGear;

	requires(Robot.drive);
    }

    /**
     * @param isAuto
     * @param isHighGear
     * @param timeout
     */
    public SnapToTarget(boolean isHighGear, double timeout) {
	m_timeout = timeout;
	m_isHighGear = isHighGear;

	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "SnapToTarget");

	Robot.drive.stopDrive();
	m_counter = 0;

	if (m_isHighGear) {
	    Robot.drive.shiftUp();
	    m_rotPGains = DriveConstants.kRotHighGearPGains;
	} else if (!m_isHighGear) {
	    Robot.drive.shiftDown();
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
	double error = -absoluteDesiredAngle - robotAngle;
	error = (error > 180) ? error - 360 : error;
	error = (error < -180) ? error + 360 : error;

	double rotPower = m_rotPGains.getP() * error;
	rotPower = NerdyMath.threshold(rotPower, m_rotPGains.getMinPower(), m_rotPGains.getMaxPower());
	if (Math.abs(error) <= DriveConstants.kDriveRotationDeadband) {
	    rotPower = 0;
	    m_counter++;
	} else {
	    m_counter = 0;
	}

	Robot.drive.setPower(rotPower, rotPower);
    }

    @Override
    protected boolean isFinished() {
	return Timer.getFPGATimestamp() - m_startTime > m_timeout || m_counter > DriveConstants.kDriveRotationCounter;
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
