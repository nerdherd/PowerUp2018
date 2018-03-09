package com.team687.frc2018.commands.wrist;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Reset wrist angle by setting offset
 */

public class ResetWristPigeon extends Command {

    public ResetWristPigeon() {
	requires(Robot.wrist);
    }

    @Override
    protected void initialize() {
	Robot.wrist.resetAngle();
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
	return true;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }

}