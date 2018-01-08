package com.team687.frc2017.commands;

import com.team687.frc2017.Constants;
import com.team687.frc2017.Robot;
import com.team687.frc2017.utilities.NerdyMath;
import com.team687.frc2017.utilities.PGains;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Arc turning
 */

public class ArcTurn extends Command {

    private double m_desiredAngle;
    private boolean m_isRightPowered;
    private boolean m_isHighGear;

    private PGains m_rotPGains;

    private double m_startTime, m_timeout;
    private double m_error;

    private double m_sign;

    /**
     * Arc Turn
     * 
     * @param desiredAngle
     * @param isRightPowered
     * @param isHighGear
     * @param timeout
     * @param sign
     *            (+1.0 or -1.0)
     */
    public ArcTurn(double desiredAngle, boolean isRightPowered, boolean isHighGear, double timeout, double sign) {
	m_desiredAngle = desiredAngle;
	m_isRightPowered = isRightPowered;
	m_timeout = timeout;
	m_isHighGear = isHighGear;
	m_sign = Math.signum(sign);

	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "ArcTurn");

	m_startTime = Timer.getFPGATimestamp();
	if (m_isHighGear) {
	    Robot.drive.shiftUp();
	    m_rotPGains = Constants.kRotHighGearPGains;
	} else if (!m_isHighGear) {
	    Robot.drive.shiftDown();
	    m_rotPGains = Constants.kRotLowGearPGains;
	}
    }

    @Override
    protected void execute() {
	double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
	m_error = -m_desiredAngle - robotAngle;
	m_error = (m_error > 180) ? m_error - 360 : m_error;
	m_error = (m_error < -180) ? m_error + 360 : m_error;
	double rotPower = m_rotPGains.getP() * m_error * 1.95; // multiplied by 2 because the rotational component is
							       // only added to one side of the drivetrain

	double rawSign = Math.signum(rotPower);
	rotPower = NerdyMath.threshold(Math.abs(rotPower), m_rotPGains.getMinPower(), m_rotPGains.getMaxPower())
		* rawSign;
	rotPower = Math.abs(rotPower) * m_sign;

	if (m_isRightPowered) {
	    Robot.drive.setPower(0, rotPower);
	} else if (!m_isRightPowered) {
	    Robot.drive.setPower(rotPower, 0);
	}
    }

    @Override
    protected boolean isFinished() {
	return Math.abs(m_error) < Constants.kDriveRotationTolerance
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
