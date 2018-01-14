package com.team687.frc2018.commands.arm;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetArmPercentOuput extends Command {

    private double m_power;

    public SetArmPercentOuput(double power) {
	requires(Robot.arm);
    }

    protected void initialize() {
	SmartDashboard.putString("Current Arm Command", "SetArmPercentOutput");
    }

    protected void execute() {
	Robot.arm.setPercentOutput(m_power);
    }

    protected boolean isFinished() {
	return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
