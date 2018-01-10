package com.team687.frc2018.commands.intake;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class OpenClaw extends Command {

    public OpenClaw() {
	requires(Robot.intake);
    }

    protected void initialize() {
    }

    protected void execute() {
	Robot.intake.closeClaw(false);
    }

    protected boolean isFinished() {
	return Robot.intake.isClawClosed() == false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
