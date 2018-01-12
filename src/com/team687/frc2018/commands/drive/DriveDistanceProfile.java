package com.team687.frc2018.commands.drive;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.DriveConstants;
import com.team687.frc2018.utilities.MotionProfile;
import com.team687.frc2018.utilities.NerdyMath;
import com.team687.frc2018.utilities.PGains;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive a path with motion profiling with optional straight driving heading
 * correction with a P-loop on NavX reading
 */

public class DriveDistanceProfile extends Command {

    private double m_distance;
    private boolean m_isStraight;
    private boolean m_isHighGear;
    private double m_heading;
    private MotionProfile m_motionProfile;

    private double m_startTime, m_timestamp;

    private PGains m_leftPGains, m_rightPGains;
    private PGains m_rotPGains;

    private double m_leftError, m_rightError;
    private double m_lastLeftError, m_lastRightError;

    /**
     * @param distance
     * @param isHighGear
     * @param isStraight
     */
    public DriveDistanceProfile(double distance, boolean isHighGear, boolean isStraight) {
	m_distance = distance;
	m_isHighGear = isHighGear;
	m_isStraight = isStraight;

	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "DriveDistanceProfile");
	m_leftError = 0;
	m_rightError = 0;

	m_motionProfile = new MotionProfile(DriveConstants.kMaxVelocity, DriveConstants.kMaxAcceleration,
		-DriveConstants.kMaxAcceleration);
	m_motionProfile.generateProfile(m_distance);

	if (m_isHighGear) {
	    Robot.drive.shiftUp();
	    m_rightPGains = DriveConstants.kDistHighGearRightPGains;
	    m_leftPGains = DriveConstants.kDistHighGearLeftPGains;
	    m_rotPGains = DriveConstants.kRotHighGearPGains;
	} else if (!m_isHighGear) {
	    Robot.drive.shiftDown();
	    m_rightPGains = DriveConstants.kDistLowGearRightPGains;
	    m_leftPGains = DriveConstants.kDistLowGearLeftPGains;
	    m_rotPGains = DriveConstants.kRotLowGearPGains;
	}

	Robot.drive.resetEncoders();
	Robot.drive.shiftDown();

	m_heading = Robot.drive.getCurrentYaw();

	SmartDashboard.putNumber("Desired Distance", m_distance);
	m_startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
	m_lastLeftError = m_leftError;
	m_lastRightError = m_rightError;
	m_timestamp = Timer.getFPGATimestamp() - m_startTime;
	int index = (int) (m_timestamp / DriveConstants.kDt);
	if (m_timestamp > m_motionProfile.getAccelTime() * 60) {
	    index += 1;
	} else if (m_timestamp >= ((m_motionProfile.getAccelTime() + m_motionProfile.getCruiseTime()) * 60)) {
	    index += 2;
	}
	double setpoint = m_motionProfile.readPosition(index);
	double goalVelocity = m_motionProfile.readVelocity(index);
	double goalAccel = m_motionProfile.readAcceleration(index);

	double feedforward = (DriveConstants.kV * goalVelocity) + (DriveConstants.kA * goalAccel);

	m_leftError = setpoint - Robot.drive.getLeftPosition();
	m_rightError = setpoint - Robot.drive.getRightPosition();
	SmartDashboard.putNumber("Left error from setpoint", m_leftError);
	SmartDashboard.putNumber("Right error from setpoint", m_rightError);

	double leftPow = (m_leftPGains.getP() * m_leftError)
		+ (DriveConstants.kDistD * ((m_leftError - m_lastLeftError) / DriveConstants.kDt - goalVelocity))
		+ feedforward;
	double rightPow = (m_rightPGains.getP() * m_rightError)
		+ (DriveConstants.kDistD * ((m_rightError - m_lastRightError) / DriveConstants.kDt - goalVelocity))
		+ feedforward;

	double rotPow = 0;
	if (m_isStraight) {
	    double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
	    double error = m_heading - robotAngle;
	    error = (error > 180) ? error - 360 : error;
	    error = (error < -180) ? error + 360 : error;

	    rotPow = m_rotPGains.getP() * error;
	    leftPow += rotPow;
	    rightPow -= rotPow;
	}

	double[] pow = { rotPow + leftPow, rotPow - rightPow };
	NerdyMath.normalize(pow, false);

	Robot.drive.setPower(pow[0], -pow[1]);
    }

    @Override
    protected boolean isFinished() {
	return (Math.abs(Robot.drive.getLeftPosition() - m_distance) <= 1
		&& Math.abs(Robot.drive.getRightPosition() - m_distance) <= 1)
		|| m_timestamp >= m_motionProfile.getTotalTime() * 60;
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
