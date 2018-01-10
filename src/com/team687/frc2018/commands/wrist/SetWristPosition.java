package com.team687.frc2018.commands.wrist;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SetWristPosition extends Command {

    private double m_position;

    public SetWristPosition(double pos) {
	requires(Robot.wrist);
	m_position = pos;
    }

    protected void initialize() {
    }

    protected void execute() {
	Robot.wrist.setPosition(m_position);
    }

    protected boolean isFinished() {
	return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
