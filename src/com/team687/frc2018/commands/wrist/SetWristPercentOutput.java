package com.team687.frc2018.commands.wrist;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetWristPercentOutput extends Command {

    private double m_power;

    public SetWristPercentOutput(double power) {
	m_power = power;

	requires(Robot.wrist);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Wrist Command", "SetWristPercentOutput");
    }

    @Override
    protected void execute() {
	Robot.wrist.setPercentOutput(m_power);
    }

    @Override
    protected boolean isFinished() {
	return false;
    }

    @Override
    protected void end() {
	Robot.wrist.setPercentOutput(0);
    }

    @Override
    protected void interrupted() {
	end();
    }
}
