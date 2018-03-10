package com.team687.frc2018.commands.drive;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.DriveConstants;
import com.team687.frc2018.utilities.NerdyMath;
import com.team687.frc2018.utilities.PGains;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Distance + heading control
 */

public class DriveStraightDistance extends Command {

    private double m_distance, m_heading;
    private double m_rightError, m_leftError;

    private PGains m_rightPGains, m_leftPGains;

    private double m_startTime, m_timeout;

    /**
     * @param distance
     * @param heading
     * @param timeout
     */
    public DriveStraightDistance(double distance, double heading, double timeout) {
	m_distance = distance;
	m_heading = heading;
	m_timeout = timeout;

	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "DriveDistancePID");
	Robot.drive.stopDrive();
	Robot.drive.resetEncoders();

	m_rightPGains = DriveConstants.kDistRightPGains;
	m_leftPGains = DriveConstants.kDistLeftPGains;

	m_startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
	m_rightError = m_distance - Robot.drive.getRightPosition();
	m_leftError = m_distance - Robot.drive.getLeftPosition();

	double straightRightPower = m_rightPGains.getP() * m_rightError;
	double straightLeftPower = m_leftPGains.getP() * m_leftError;

	straightRightPower = NerdyMath.threshold(straightRightPower, m_rightPGains.getMinPower(),
		m_rightPGains.getMaxPower());
	straightLeftPower = NerdyMath.threshold(straightLeftPower, m_leftPGains.getMinPower(),
		m_leftPGains.getMaxPower());

	double yaw = Robot.drive.getCurrentYaw();
	if (m_distance < 0) {
	    yaw += 180;
	}
	double robotAngle = (360 - yaw) % 360;
	double rotError = -m_heading - robotAngle;
	rotError = (rotError > 180) ? rotError - 360 : rotError;
	rotError = (rotError < -180) ? rotError + 360 : rotError;
	double rotPower = DriveConstants.kDistRotP * rotError;

	Robot.drive.setPower(straightLeftPower - rotPower, straightRightPower + rotPower);
    }

    @Override
    protected boolean isFinished() {
	boolean reachedGoal = Math.abs(Robot.drive.getRightPosition()) > Math.abs(m_distance)
		&& Math.abs(Robot.drive.getLeftPosition()) > Math.abs(m_distance);
	return reachedGoal || Timer.getFPGATimestamp() - m_startTime > m_timeout;
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