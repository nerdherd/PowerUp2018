package com.team687.frc2018.commands.wrist;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetWristPosition extends Command {

	private double m_position;

	public SetWristPosition(double position) {
		requires(Robot.wrist);
		m_position = position;
	}

	@Override
	protected void initialize() {
		SmartDashboard.putString("Current Wrist Command", "SetWristPosition: " + m_position);
	}

	@Override
	protected void execute() {
		Robot.wrist.setPosition(m_position);
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
