package com.team687.frc2018.commands.drive.teleop;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetDriveVelocity extends Command {
	
	private double m_velocity;

    public SetDriveVelocity(double velocity) {
    	m_velocity = velocity;
    	
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "SetDriveVelocity");
	Robot.drive.stopDrive();
    }

    @Override
    protected void execute() {
    	Robot.drive.setPercentVelocity(m_velocity, m_velocity);
    }

    @Override
    protected boolean isFinished() {
	return false;
    }

    @Override
    protected void end() {
	Robot.drive.stopDrive();
    }

    @Override
    protected void interrupted() {
	end();
    }

}