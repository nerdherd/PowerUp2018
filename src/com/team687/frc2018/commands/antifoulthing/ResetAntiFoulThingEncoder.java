package com.team687.frc2018.commands.antifoulthing;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ResetAntiFoulThingEncoder extends Command {

    public ResetAntiFoulThingEncoder() {
	requires(Robot.antiFoulThing);
    }

    protected void initialize() {
    }

    protected void execute() {
	Robot.antiFoulThing.resetEncoder();
    }

    protected boolean isFinished() {
	return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
