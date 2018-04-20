package com.team687.frc2018.commands.superstructure;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class EmergencyWristSave extends Command {

    public EmergencyWristSave() {
    	requires(Robot.wrist);
    }

    protected void initialize() {
    	Robot.wrist.setPercentOutput(0);
    }

    protected void execute() {
    	Robot.wrist.setPercentOutput(-Robot.oi.getArticJoyY());
    	Robot.wrist.resetEncoder();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.wrist.setPercentOutput(0);
    }

    protected void interrupted() {
    	Robot.wrist.setPercentOutput(0);
    }
}
