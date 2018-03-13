package com.team687.frc2018.commands.climber;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ResetClimberEncoders extends Command {

    public ResetClimberEncoders() {
        requires(Robot.climber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.climber.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.climber.resetEncoders();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.climber.getClimber1Position() == 0 && Robot.climber.getClimber2Position() == 0;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
