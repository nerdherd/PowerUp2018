package com.team687.frc2018.commands.arm;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Set arm position
 */

public class SetArmPosition extends Command {

    private double m_position;

    public SetArmPosition(double position) {
	m_position = position;

	requires(Robot.arm);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Arm Command", "SetArmPosition");

	Robot.arm.setPosition(m_position);
    }

    @Override
    protected void execute() {
	Robot.arm.setPosition(m_position);
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
