package com.team687.frc2018.commands.drive;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Reset encoders
 */

public class ResetDriveEncoders extends Command {

    public ResetDriveEncoders() {
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "ResetDriveEncoders");
	Robot.drive.resetEncoders();
    }

    @Override
    protected void execute() {
	Robot.drive.resetEncoders();
    }

    @Override
    protected boolean isFinished() {
	return Robot.drive.getDrivetrainPosition() == 0;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }

}
