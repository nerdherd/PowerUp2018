package com.team687.frc2018.commands.antifoulthing;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetAntiFoulThingPower extends Command {
	
	private double m_power;

    public SetAntiFoulThingPower(double power) {
    	m_power = power;
    	
    	requires(Robot.antiFoulThing);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.antiFoulThing.setPower(m_power);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.antiFoulThing.setPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
