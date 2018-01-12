package com.team687.frc2018.commands.wrist;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetWristPosition extends Command {

    private double m_position;

    public SetWristPosition(double position) {
	requires(Robot.wrist);
	m_position = position;
    }

    protected void initialize() {
	SmartDashboard.putString("Current Wrist Command", "SetWristPosition: " + m_position);
    }

    protected void execute() {
	Robot.wrist.setPosition(m_position);
    }

    protected boolean isFinished() {
	return Math.abs(Robot.wrist.getPosition() - m_position) < SuperstructureConstants.kWristTolerance;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
