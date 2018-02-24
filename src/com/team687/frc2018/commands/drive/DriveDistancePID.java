package com.team687.frc2018.commands.drive;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.DriveConstants;
import com.team687.frc2018.utilities.NerdyMath;
import com.team687.frc2018.utilities.PGains;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Pure PID distance control
 */

public class DriveDistancePID extends Command {

	private double m_rightDistance, m_leftDistance;
	private double m_rightError, m_leftError;

	private PGains m_rightPGains, m_leftPGains;

	private double m_startTime, m_timeout;

	/**
	 * @param rightDistance
	 * @param leftDistance
	 * @param timeout
	 */
	public DriveDistancePID(double rightDistance, double leftDistance, double timeout) {
		m_timeout = timeout;
		m_rightDistance = rightDistance;
		m_leftDistance = leftDistance;

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
		m_rightError = m_rightDistance - Robot.drive.getRightPosition();
		m_leftError = m_leftDistance - Robot.drive.getLeftPosition();

		double straightRightPower = m_rightPGains.getP() * m_rightError;
		double straightLeftPower = m_leftPGains.getP() * m_leftError;

		straightRightPower = NerdyMath.threshold(straightRightPower, m_rightPGains.getMinPower(),
				m_rightPGains.getMaxPower());
		straightLeftPower = NerdyMath.threshold(straightLeftPower, m_leftPGains.getMinPower(),
				m_leftPGains.getMaxPower());

		Robot.drive.setPower(straightLeftPower, straightRightPower);
	}

	@Override
	protected boolean isFinished() {
		boolean reachedGoal = Math.abs(m_leftError) < DriveConstants.kDriveDistanceTolerance
				&& Math.abs(m_rightError) < DriveConstants.kDriveDistanceTolerance;
		return reachedGoal; //|| Timer.getFPGATimestamp() - m_startTime > m_timeout;
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