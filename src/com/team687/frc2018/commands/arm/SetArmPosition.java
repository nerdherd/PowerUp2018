package com.team687.frc2018.commands.arm;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */

public class SetArmPosition extends Command {

    private double m_position;

    public SetArmPosition(double pos) {
	requires(Robot.arm);
	m_position = pos;
    }

    protected void initialize() {
	Robot.arm.resetEncoder();
    }

    protected void execute() {
	Robot.arm.setPosition(m_position);
    }

    protected boolean isFinished() {
	return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
