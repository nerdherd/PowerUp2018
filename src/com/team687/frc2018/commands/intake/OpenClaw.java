package com.team687.frc2018.commands.intake;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OpenClaw extends Command {

    public OpenClaw() {
	requires(Robot.intake);
    }

    protected void initialize() {
	SmartDashboard.putString("Current Intake Command", "OpenClaw");
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
