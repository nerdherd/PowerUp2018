package com.team687.frc2018.commands.drive.teleop;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 33's Culver drive (Halo drive but with θ of joystick * radius as wheel
 * component). Uses gamepad.
 */

public class CulverDrive extends Command {

    public CulverDrive() {
	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "CulverDrive");
	Robot.drive.stopDrive();
    }

    @Override
    protected void execute() {
	double wheelX = Robot.oi.getDriveJoyRightX();
	double wheelY = Robot.oi.getDriveJoyRightY();

	double theta = Math.abs(Math.atan(wheelX / wheelY)) * 57.29578;
	double sign = Math.signum(Robot.oi.getDriveJoyRightX());

	double radiusMagnitude = Math.abs(Math.sqrt(Math.pow(wheelX, 2) + Math.pow(wheelY, 2)));
	if (radiusMagnitude < 0.5) {
	    radiusMagnitude = 0;
	} else if (radiusMagnitude > 0.5) {
	    radiusMagnitude = 1;
	}

	double wheel = radiusMagnitude * theta / 90 * sign;
	// wheel = Math.pow(wheel, 2) * sign; // sensitivity
	double throttle = Robot.oi.getDriveJoyLeftY();

	SmartDashboard.putNumber("Culver Wheel", wheel);
	SmartDashboard.putNumber("Culver Throttle", throttle);

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
