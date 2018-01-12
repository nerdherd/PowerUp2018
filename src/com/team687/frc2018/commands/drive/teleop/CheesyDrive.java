package com.team687.frc2018.commands.drive.teleop;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.DriveConstants;
import com.team687.frc2018.utilities.NerdyMath;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * An implementation of 254's CheesyDrive
 */

public class CheesyDrive extends Command {

    private double m_quickStopAccumulator;

    public CheesyDrive() {
	// subsystem requirements
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "CheesyDrive");
	Robot.drive.stopDrive();
    }

    @Override
    protected void execute() {
	double rightPower, leftPower;
	boolean isQuickTurn = Robot.oi.getQuickTurn();

	double wheel = Robot.drive.handleDeadband(Robot.oi.getDriveJoyRightX(), DriveConstants.kJoystickDeadband);
	double throttle = -Robot.drive.handleDeadband(Robot.oi.getDriveJoyLeftY(), DriveConstants.kJoystickDeadband);

	rightPower = leftPower = throttle;

	double sensitivity;
	if (Robot.drive.isHighGear()) {
	    sensitivity = DriveConstants.kSensitivityHigh;
	} else {
	    sensitivity = DriveConstants.kSensitivityLow;
	}

	double angularPow;
	if (isQuickTurn) {
	    angularPow = wheel;
	    m_quickStopAccumulator = (1 - DriveConstants.kDriveAlpha) * m_quickStopAccumulator
		    + DriveConstants.kDriveAlpha * NerdyMath.limit(wheel, 1.0) * 2;
	    throttle = 0;
	} else {
	    angularPow = Math.abs(throttle) * wheel * sensitivity - m_quickStopAccumulator;
	    if (m_quickStopAccumulator > 1) {
		m_quickStopAccumulator -= 1;
	    } else if (m_quickStopAccumulator < -1) {
		m_quickStopAccumulator += 1;
	    } else {
		m_quickStopAccumulator = 0;
	    }
	}

	leftPower = angularPow + leftPower;
	rightPower = angularPow - rightPower;

	double[] pow = { leftPower, rightPower };
	pow = NerdyMath.normalize(pow, false);

	SmartDashboard.putNumber("Cheesy Left Power", pow[0]);
	SmartDashboard.putNumber("Cheesy Right Power", pow[1]);

	// Robot.drive.setPower(pow[0], pow[1]);
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
