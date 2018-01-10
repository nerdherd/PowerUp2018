package com.team687.frc2018.commands.intake;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CloseClaw extends Command {

    public CloseClaw() {
	requires(Robot.intake);
    }

    protected void initialize() {
    }

    protected void execute() {
	Robot.intake.closeClaw(true);
    }

    protected boolean isFinished() {
	return Robot.intake.isClawClosed() == true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
