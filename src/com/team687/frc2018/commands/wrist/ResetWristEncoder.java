package com.team687.frc2018.commands.wrist;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ResetWristEncoder extends Command {

    public ResetWristEncoder() {
	requires(Robot.wrist);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Wrist Command", "ResetArmEncoders");
	Robot.wrist.resetEncoder();
    }

    @Override
    protected void execute() {
	Robot.wrist.resetEncoder();
    }

    @Override
    protected boolean isFinished() {
	return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }

}
