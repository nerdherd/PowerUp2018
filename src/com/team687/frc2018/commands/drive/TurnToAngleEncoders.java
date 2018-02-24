package com.team687.frc2018.commands.drive;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.DriveConstants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnToAngleEncoders extends Command{
	
	private double m_angle;
	private double m_rightDesiredTicks;
	private double m_leftDesiredTicks;
	private double m_rightError;
	private double m_leftError;
	private double m_rightPower;
	private double m_leftPower;
	
	public TurnToAngleEncoders(double angle) {
		m_angle = angle;
		requires(Robot.drive);
	}
	
	@Override
    protected void initialize() {
	m_rightDesiredTicks = (m_angle * 46.44) + Robot.drive.getRightPosition();
	m_leftDesiredTicks = (m_angle * -46.44) + Robot.drive.getLeftPosition();
	Robot.drive.stopDrive();
	
    }

    @Override
    protected void execute() {
    	m_rightError = m_rightDesiredTicks - Robot.drive.getRightPosition();
    	m_leftError = m_leftDesiredTicks - Robot.drive.getLeftPosition();
    	m_rightPower = Math.signum(m_rightError) * Math.min(Math.abs(DriveConstants.kPEncoderTurning * m_rightError), 0.5);
    	m_leftPower = Math.signum(m_leftError) * Math.min(Math.abs(DriveConstants.kPEncoderTurning * m_leftError), 0.5);
    	
    	m_rightPower = Math.signum(m_rightPower) * Math.max(Math.abs(m_rightPower), 0.1);
    	m_leftPower = Math.signum(m_leftPower) * Math.max(Math.abs(m_leftPower), 0.1);
    	
    	SmartDashboard.putNumber("Error left", m_leftError);
    	SmartDashboard.putNumber("Error right", m_rightError);
    	
//    	Robot.drive.setLeftPosition(m_leftDesiredTicks);
//    	Robot.drive.setRightPosition(m_rightDesiredTicks);
    	
    	Robot.drive.setPower(m_leftPower, m_rightPower);
    	
    }

    @Override
    protected boolean isFinished() {
	return (Math.abs(m_rightError) < DriveConstants.kEncoderTurningThreshold) && (Math.abs(m_leftError) < DriveConstants.kEncoderTurningThreshold);
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
