package com.team687.frc2018.commands.wrist;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetWristAngle extends Command {

    private double m_angle;

    public SetWristAngle(double angle) {
	m_angle = angle;

	requires(Robot.wrist);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Wrist Command", "SetWristAngle");
    }

    @Override
    protected void execute() {
	Robot.wrist.setAngleAbsolute(m_angle);
    }

    @Override
    protected boolean isFinished() {
	return false;
    }

    @Override
    protected void end() {
    	Robot.wrist.setPercentOutput(0);
    }

    @Override
    protected void interrupted() {
    	end();
    }
}
