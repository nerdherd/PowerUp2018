package com.team687.frc2018.commands.drive;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.DriveConstants;
import com.team687.frc2018.utilities.NerdyMath;
import com.team687.frc2018.utilities.PGains;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Aligns the distance from a target using vision.
 */

public class AlignDistanceToTarget extends Command {

    private double m_distanceFromTargetInFeet;
    private double m_error;
    private boolean m_isHighGear;

    private PGains m_rightPGains, m_leftPGains;

    private double m_startTime, m_timeout;

    public AlignDistanceToTarget(double distanceFromTargetInFeet) {
	m_distanceFromTargetInFeet = distanceFromTargetInFeet;
	m_isHighGear = false;
	m_timeout = 6.87;

	requires(Robot.drive);
    }

    /**
     * @param distanceFromTargetInFeet
     * @param isHighGear
     * @param timeout
     */
    public AlignDistanceToTarget(double distanceFromTargetInFeet, boolean isHighGear, double timeout) {
	m_distanceFromTargetInFeet = distanceFromTargetInFeet;
	m_isHighGear = isHighGear;
	m_timeout = timeout;

	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "AlignDistanceToTarget");
	Robot.drive.stopDrive();

	if (m_isHighGear) {
	    Robot.drive.shiftUp();
	    m_rightPGains = DriveConstants.kDistHighGearRightPGains;
	    m_leftPGains = DriveConstants.kDistHighGearLeftPGains;
	} else if (!m_isHighGear) {
	    Robot.drive.shiftDown();
	    m_rightPGains = DriveConstants.kDistLowGearRightPGains;
	    m_leftPGains = DriveConstants.kDistLowGearLeftPGains;
	}

	m_startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
	double robotInchesFromTarget = Robot.visionAdapter.getDistanceFromTarget();
	m_error = NerdyMath.inchesToTicks(NerdyMath.feetToInches(m_distanceFromTargetInFeet) - robotInchesFromTarget);

	double straightRightPower = m_rightPGains.getP() * m_error;
	straightRightPower = NerdyMath.threshold(straightRightPower, m_rightPGains.getMinPower(),
		m_rightPGains.getMaxPower());
	double straightLeftPower = m_leftPGains.getP() * m_error;
	straightLeftPower = NerdyMath.threshold(straightLeftPower, m_leftPGains.getMinPower(),
		m_leftPGains.getMaxPower());

	Robot.drive.setPower(straightLeftPower, -straightRightPower);
    }

    @Override
    protected boolean isFinished() {
	return Math.abs(m_error) < DriveConstants.kDriveDistanceTolerance
		|| Timer.getFPGATimestamp() - m_startTime > m_timeout;
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
