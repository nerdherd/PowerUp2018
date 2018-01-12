package com.team687.frc2018.commands.drive;

import com.team687.frc2018.Robot;
import com.team687.frc2018.VisionAdapter;
import com.team687.frc2018.constants.DriveConstants;
import com.team687.frc2018.utilities.NerdyMath;
import com.team687.frc2018.utilities.PGains;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Live vision tracking (follows vision target around, no end)
 */

public class LiveTargetTracking extends Command {

    private boolean m_isHighGear;
    private PGains m_rotPGains;

    /**
     * @param isHighGear
     */
    public LiveTargetTracking(boolean isHighGear) {
	m_isHighGear = isHighGear;

	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "LiveTargetTracking");

	if (m_isHighGear) {
	    Robot.drive.shiftUp();
	    m_rotPGains = DriveConstants.kRotHighGearPGains;
	} else if (!m_isHighGear) {
	    Robot.drive.shiftDown();
	    m_rotPGains = DriveConstants.kRotLowGearPGains;
	}
    }

    @Override
    protected void execute() {
	double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
	double relativeAngleError = VisionAdapter.getInstance().getAngleToTurn();
	double processingTime = VisionAdapter.getInstance().getProcessedTime();
	double absoluteDesiredAngle = relativeAngleError + Robot.drive.timeMachineYaw(processingTime);
	double error = absoluteDesiredAngle - robotAngle;
	error = (error > 180) ? error - 360 : error;
	error = (error < -180) ? error + 360 : error;

	double power = m_rotPGains.getP() * error;
	power = NerdyMath.threshold(power, m_rotPGains.getMinPower(), m_rotPGains.getMaxPower());
	if (Math.abs(error) <= DriveConstants.kDriveRotationDeadband) {
	    power = 0;
	}

	Robot.drive.setPower(power, power);
    }

    @Override
    protected boolean isFinished() {
	return false;
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
