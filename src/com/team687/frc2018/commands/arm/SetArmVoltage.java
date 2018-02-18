package com.team687.frc2018.commands.arm;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetArmVoltage extends Command {

    private double m_voltage;

    public SetArmVoltage(double voltage) {
	m_voltage = voltage;

	requires(Robot.arm);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Arm Command", "SetArmVoltgae");

	Robot.arm.setVoltage(m_voltage);
    }

    @Override
    protected void execute() {
	Robot.arm.setVoltage(m_voltage);
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
