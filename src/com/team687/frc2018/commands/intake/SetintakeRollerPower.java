package com.team687.frc2018.commands.intake;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SetIntakeRollerPower extends Command {

    private double m_power;

    public SetIntakeRollerPower(double power) {
	requires(Robot.intake);
    }

    protected void initialize() {
    }

    protected void execute() {
	Robot.intake.setRollerPower(m_power);
    }

    protected boolean isFinished() {
	return Robot.intake.getRollerVoltage() / 12 == m_power;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}