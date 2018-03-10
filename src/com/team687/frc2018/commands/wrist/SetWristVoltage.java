package com.team687.frc2018.commands.wrist;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetWristVoltage extends Command {

    private double m_voltage;

    public SetWristVoltage(double voltage) {
	m_voltage = voltage;

	requires(Robot.wrist);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Wrist Command", "SetArmVoltgae");

	Robot.wrist.setVoltage(m_voltage);
    }

    @Override
    protected void execute() {
	Robot.wrist.setVoltage(m_voltage);
    }

    @Override
    protected boolean isFinished() {
	return false;
    }

    @Override
    protected void end() {
	Robot.wrist.setVoltage(0);
    }

    @Override
    protected void interrupted() {
	end();
    }
}