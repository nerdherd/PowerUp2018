package com.team687.frc2018.commands.arm;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetZeroArmVoltage extends Command {

    public SetZeroArmVoltage() {
	requires(Robot.arm);
    }

    protected void initialize() {
	SmartDashboard.putString("Current Arm Command", "SetZeroArmVoltage");
    }

    protected void execute() {
	Robot.arm.setZeroVoltage();
    }

    protected boolean isFinished() {
	return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
