package com.team687.frc2018.commands.superstructure;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StackCubes extends Command {

	private double m_angle;
	
    public StackCubes(double angle) {
    	m_angle = angle;
        requires(Robot.arm);
        requires(Robot.wrist);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.wrist.setAngleAbsolute(0);
    	Robot.arm.setAngle(m_angle);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
