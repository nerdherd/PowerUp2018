package com.team687.frc2018.commands.intake;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ClawOpen extends Command {

    public ClawOpen() {
	requires(Robot.intake);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "ClawOpen");
	Robot.intake.openClaw();
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
	end();
    }

}
