package com.team687.frc2018.commands.arm;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetArmPercentOuput extends Command {

    private double m_power;

    public SetArmPercentOuput(double power) {
	requires(Robot.arm);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Arm Command", "SetArmPercentOutput");
    }

    @Override
    protected void execute() {
	Robot.arm.setPercentOutput(m_power);
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
