package com.team687.frc2018.commands.wrist;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SetZeroWristVoltage extends Command {

    public SetZeroWristVoltage() {
	requires(Robot.arm);
    }

    protected void initialize() {
    }

    protected void execute() {
	Robot.wrist.setZeroVoltage();
    }

    protected boolean isFinished() {
	return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
