package com.team687.frc2018.commands.drive;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Test drive subsystem hardware. Make sure drivetrain wheels are not in contact
 * with the ground. Left joystick sets power for both sides
 */

public class TestDriveSubsystem extends Command {

    public TestDriveSubsystem() {
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "TestDriveSubsystem");
	Robot.drive.stopDrive();
    }

    @Override
    protected void execute() {
	double power = Robot.oi.getDriveJoyLeftY();
	Robot.drive.setPower(power, power);

	boolean failed = Robot.drive.testDriveSubsystem();
	if (failed) {
	    SmartDashboard.putBoolean("DRIVE SUBSYSTEM HEALTHY", false);
	} else {
	    SmartDashboard.putBoolean("DRIVE SUBSYSTEM HEALTHY", true);
	}
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
