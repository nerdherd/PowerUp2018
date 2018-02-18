package com.team687.frc2018.commands.drive.teleop;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetDriveVoltage extends Command {
	
	private double m_voltage;
	
	private double m_startTime;

    public SetDriveVoltage(double voltage) {
    	m_voltage = voltage;
    	
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "SetDriveVoltage");
	m_startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
    	Robot.drive.setVoltage(m_voltage, m_voltage);
    }

    @Override
    protected boolean isFinished() {
//	return Timer.getFPGATimestamp() - m_startTime > 4;
    	return false;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }

}
