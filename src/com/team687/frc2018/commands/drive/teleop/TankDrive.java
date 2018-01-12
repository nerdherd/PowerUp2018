package com.team687.frc2018.commands.drive.teleop;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Tank drive with sensitivity adjustments
 */

public class TankDrive extends Command {

    public TankDrive() {
	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "TankDrive");
	Robot.drive.stopDrive();
    }

    @Override
    protected void execute() {
	// double leftPow = Robot.drive.addLeftSensitivity(Robot.oi.getDriveJoyLeftY());
	// double rightPow =
	// Robot.drive.addRightSensitivity(Robot.oi.getDriveJoyRightY());

	double leftPow = Robot.oi.getDriveJoyLeftY();
	double rightPow = Robot.oi.getDriveJoyRightY();
	Robot.drive.setPower(leftPow, -rightPow);
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
