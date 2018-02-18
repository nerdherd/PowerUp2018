package com.team687.frc2018.commands.drive.teleop;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestDrive extends Command{
	
	private double m_power;
	
    public TestDrive(double power) {
    	m_power = power;
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "TestRightDrive");
    }

    @Override
    protected void execute() {
    	Robot.drive.setPower(m_power, m_power);
    }

    @Override
    protected boolean isFinished() {
	return false;
    }

    @Override
    protected void end() {
    	Robot.drive.setPower(0, 0);
    }

    @Override
    protected void interrupted() {
	end();
    }

}
