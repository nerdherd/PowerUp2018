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
	SmartDashboard.putString("Current Arm Command", "ResetArmEncoders");
	Robot.arm.resetEncoder();
    }

    @Override
    protected void execute() {
	Robot.arm.resetEncoder();
    }

    @Override
    protected boolean isFinished() {
	return Robot.arm.getPosition() == 0;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }

}
