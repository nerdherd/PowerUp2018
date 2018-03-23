package com.team687.frc2018.commands.drive;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.DriveConstants;
import com.team687.frc2018.utilities.NerdyMath;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Turn to a specified angle (no vision, absolute)
 */

public class TurnToAngle extends Command {

    private double m_desiredAngle;
    private double m_startTime, m_timeout;
    private double m_error;
    private double m_prevTimestamp;
    private double m_prevError;
    private double m_dTerm;

    private int m_counter;

    public TurnToAngle(double angle) {
	m_desiredAngle = angle;
	m_timeout = 2; // default timeout is 10 seconds

	requires(Robot.drive);
    }

    /**
     * @param angle
     * @param timeout
     */
    public TurnToAngle(double angle, double timeout) {
	m_desiredAngle = angle;
	m_timeout = timeout;

	// subsystem dependencies
	requires(Robot.drive);
    }
    
    public TurnToAngle(double angle, double timeout, double tolerance) {
    	
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "TurnToAngle");
	m_startTime = Timer.getFPGATimestamp();
	m_counter = 0;
    }

    @Override
    protected void execute() {
	double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
	m_error = -m_desiredAngle - robotAngle;
	m_error = (m_error > 180) ? m_error - 360 : m_error;
	m_error = (m_error < -180) ? m_error + 360 : m_error;

	m_dTerm = (m_prevError - m_error) / (m_prevTimestamp - Timer.getFPGATimestamp());
	m_prevTimestamp = Timer.getFPGATimestamp();

	double power = DriveConstants.kRotP * m_error + DriveConstants.kRotD * m_dTerm;
	power = NerdyMath.threshold(power, DriveConstants.kRotMinPower, DriveConstants.kRotPMaxPower);
	// power = Math.min(DriveConstants.kRotPMaxPower,
	// Math.max(-DriveConstants.kRotPMaxPower, power));

	Robot.drive.setPower((-DriveConstants.kLeftAdjustment * power), power);
	if (Math.abs(m_error) <= DriveConstants.kDriveRotationTolerance) {
	    m_counter += 1;
	} else {
	    m_counter = 0;
	}
	m_prevError = m_error;
    }

    @Override
    protected boolean isFinished() {
	return m_counter > 4 || Timer.getFPGATimestamp() - m_startTime > m_timeout;
	// return false;
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