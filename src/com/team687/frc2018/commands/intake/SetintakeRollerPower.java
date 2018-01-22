package com.team687.frc2018.commands.intake;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetIntakeRollerPower extends Command {

    private double m_power;

    public SetIntakeRollerPower(double power) {
	m_power = power;
	requires(Robot.intake);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Intake Command", "SetIntakeRollerPower: " + m_power);
    }

    @Override
    protected void execute() {
	Robot.intake.setRollerPower(m_power);
    }

    @Override
    protected boolean isFinished() {
	return m_power >= 0 && Robot.intake.hasCube();
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}