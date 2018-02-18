package com.team687.frc2018.commands.intake;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetIntakeRollerPower extends Command {

    private double m_power;
    private double m_startTime;

    public SetIntakeRollerPower(double power) {
	m_power = power;
	requires(Robot.intake);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Intake Command", "SetIntakeRollerPower: " + m_power);
	m_startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
	Robot.intake.setRollerPower(m_power);
    }

    @Override
    protected boolean isFinished() {
    	return false;
    }

    @Override
    protected void end() {
	Robot.intake.setRollerPower(0);
    }

    @Override
    protected void interrupted() {
	end();
    }
}