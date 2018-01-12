package com.team687.frc2018.commands.drive;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Reset encoders
 */

public class ResetEncoders extends Command {

    public ResetEncoders() {
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "Reset Encoders");
	Robot.drive.resetEncoders();
    }

    @Override
    protected void execute() {
	Robot.drive.resetEncoders();
    }

    @Override
    protected boolean isFinished() {
	return Robot.drive.getRightPosition() == 0 && Robot.drive.getLeftPosition() == 0;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }

}
