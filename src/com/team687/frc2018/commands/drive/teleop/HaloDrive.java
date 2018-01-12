package com.team687.frc2018.commands.drive.teleop;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Simple split joystick arcade
 */

public class HaloDrive extends Command {

    public HaloDrive() {
	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "HaloDrive");
	Robot.drive.stopDrive();
    }

    @Override
    protected void execute() {
	double throttle = Robot.oi.getDriveJoyLeftY();
	double wheel = Robot.oi.getDriveJoyRightX();
	// double sign = Math.signum(wheel);
	// wheel = Math.pow(wheel, 2) * sign; // sensitivity

	SmartDashboard.putNumber("Halo Wheel", wheel);
	SmartDashboard.putNumber("Halo Throttle", throttle);

	double leftPower = wheel + throttle;
	double rightPower = wheel - throttle;

	Robot.drive.setPower(leftPower, rightPower);
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
