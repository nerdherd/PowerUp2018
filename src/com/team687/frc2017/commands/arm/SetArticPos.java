package com.team687.frc2017.commands.arm;

import com.team687.frc2017.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */

public class SetArticPos extends Command {

    private double m_position;

    public SetArticPos(double pos) {
	requires(Robot.arm);
	m_position = pos;
    }

    protected void initialize() {
	Robot.arm.resetEncoder();
    }

    protected void execute() {
	Robot.arm.setPos(m_position);
    }

    protected boolean isFinished() {
	return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
