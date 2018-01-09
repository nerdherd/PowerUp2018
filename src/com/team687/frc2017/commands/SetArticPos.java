package com.team687.frc2017.commands;

import com.team687.frc2017.Robot;
import com.team687.frc2017.constants.ArmConstants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetArticPos extends Command {

	private double position;
	
    public SetArticPos(double pos) {
        requires(Robot.arm);
        position = pos;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.arm.setZeroVoltage();
    	Robot.arm.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.arm.setPos(position);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.arm.setZeroVoltage();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
