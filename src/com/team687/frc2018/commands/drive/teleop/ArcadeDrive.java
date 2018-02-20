package com.team687.frc2018.commands.drive.teleop;

import com.team687.frc2018.Robot;
import com.team687.frc2018.utilities.NerdyMath;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Tank drive with sensitivity adjustments
 */

public class ArcadeDrive extends Command {

	public ArcadeDrive() {
		// subsystem dependencies
		requires(Robot.drive);
	}

	@Override
	protected void initialize() {
		SmartDashboard.putString("Current Drive Command", "ArcadeDrive");
		Robot.drive.stopDrive();
	}

	@Override
	protected void execute() {
		// double leftPow =
		// Robot.drive.addLeftSensitivity(Robot.oi.getDriveJoyLeftY());
		// double rightPow =
		// Robot.drive.addRightSensitivity(Robot.oi.getDriveJoyRightY());

		double straightPower = NerdyMath.handleDeadband(-Math.pow(Robot.oi.getDriveJoyLeftY(), 3), .05);
		double rotPower = NerdyMath.handleDeadband(Math.pow(Robot.oi.getDriveJoyLeftX(), 3) * 0.5, .05);
		Robot.drive.setPower(straightPower + rotPower, straightPower - rotPower);
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
