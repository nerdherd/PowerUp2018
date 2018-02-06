package com.team687.frc2018.commands.arm;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ResetArmEncoder extends Command {

    public ResetArmEncoder() {
	requires(Robot.arm);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Arm Command", "ResetArmEncoder");

	Robot.arm.resetEncoder();
    }

    @Override
    protected void execute() {
	Robot.arm.setVoltage(0.75); // get rid of backlash
	Robot.arm.resetEncoder();
    }

    @Override
    protected boolean isFinished() {
	return false;
    }

    @Override
    protected void end() {
	Robot.arm.setVoltage(0);
    }

    @Override
    protected void interrupted() {
	end();
    }
}