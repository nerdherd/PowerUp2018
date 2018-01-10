package com.team687.frc2018;

import com.team687.frc2018.utilities.Kinematics;
import com.team687.frc2018.utilities.Pose;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Odometry of drivetrain. Keeps track of robot's pose (x, y, theta) over time
 * using pose transformations and kinematics.
 */

public class Odometry {

    private static Odometry m_instance = null;

    private double m_gyroYawDegrees, m_gyroYawRadians;
    private double m_derivedYaw;
    private double m_angularVelocity;
    private double m_arcRadius;

    private double m_leftDistance, m_rightDistance;
    private double m_lastLeftDistance, m_lastRightDistance;
    private double m_leftVelocity, m_rightVelocity;

    private double m_currentTime, m_lastTime, m_deltaTime;

    private Pose m_lastPose, m_newPose;

    public static Odometry getInstance() {
	if (m_instance == null) {
	    m_instance = new Odometry();
	}
	return m_instance;
    }

    protected Odometry() {
	m_lastTime = Timer.getFPGATimestamp();

	// starting configuration
	m_newPose = new Pose(0, 0, Robot.drive.getCurrentYawRadians());
	m_lastPose = new Pose(0, 0, Robot.drive.getCurrentYawRadians());
	m_derivedYaw = Robot.drive.getCurrentYawRadians();
    }

    /**
     * Update raw sensor readings and inherent calculations
     */
    public void update() {
	m_leftDistance = Robot.drive.getLeftPosition();
	m_rightDistance = Robot.drive.getRightPosition();
	m_leftVelocity = Robot.drive.getLeftSpeed();
	m_rightVelocity = Robot.drive.getRightSpeed();
	m_gyroYawDegrees = Robot.drive.getCurrentYaw();
	m_gyroYawRadians = Robot.drive.getCurrentYawRadians();

	SmartDashboard.putNumber("Left Position Ticks", m_leftDistance);
	SmartDashboard.putNumber("Right Position Ticks", m_rightDistance);
	SmartDashboard.putNumber("Drivetrain Position Ticks", Robot.drive.getDrivetrainPosition());
	SmartDashboard.putNumber("Left Speed Ticks", m_leftVelocity);
	SmartDashboard.putNumber("Right Speed Ticks", m_rightVelocity);

	SmartDashboard.putNumber("Yaw (degrees)", m_gyroYawDegrees);
	SmartDashboard.putNumber("Yaw (radians)", m_gyroYawRadians);
	SmartDashboard.putNumber("Accel X", Robot.drive.getCurrentAccelX());
	SmartDashboard.putNumber("Accel Y", Robot.drive.getCurrentAccelY());
	SmartDashboard.putNumber("Accel Z", Robot.drive.getCurrentAccelZ());

	m_derivedYaw += Kinematics.getDerivedDeltaYaw(m_rightVelocity, m_leftVelocity, m_deltaTime);
	m_angularVelocity = Kinematics.getAngularVelocity(m_rightVelocity, m_leftVelocity);
	m_arcRadius = Kinematics.getCurvatureRadius(m_rightVelocity, m_leftVelocity);

	SmartDashboard.putNumber("Yaw derived from encoders (radians)", m_derivedYaw);
	SmartDashboard.putNumber("Angular Velocity", m_angularVelocity);
	SmartDashboard.putNumber("Radius of Curvature", m_arcRadius);

	// estimatePoseVelocity();
	estimatePosePosition();

	SmartDashboard.putNumber("X", m_newPose.getX());
	SmartDashboard.putNumber("Y", m_newPose.getY());
	SmartDashboard.putNumber("Theta (radians)", m_newPose.getTheta()); // this is the same as yaw in radians

    }

    /**
     * Pose estimation based on velocity
     */
    public void estimatePoseVelocity() {
	m_currentTime = Timer.getFPGATimestamp();
	m_deltaTime = m_currentTime - m_lastTime;
	m_lastTime = m_currentTime;

	m_newPose = Kinematics.getNewPose(m_lastPose, m_rightVelocity, m_leftVelocity, m_deltaTime);
	m_newPose.setTimestamp(m_currentTime);
	m_lastPose = m_newPose;
    }

    /**
     * Pose estimation based on position instead of velocity
     * 
     * May be more robust but less accurate.
     */
    public void estimatePosePosition() {
	double deltaDistance = ((m_leftDistance - m_lastLeftDistance) + (m_rightDistance - m_lastRightDistance)) / 2;
	m_newPose.setX(m_lastPose.getX() + deltaDistance * Math.sin(m_gyroYawRadians));
	m_newPose.setY(m_lastPose.getY() + deltaDistance * Math.cos(m_gyroYawRadians));
	m_newPose.setTheta(m_gyroYawRadians);
	m_newPose.setTimestamp(Timer.getFPGATimestamp());

	m_lastPose = m_newPose;
    }

    /**
     * @return current estimated pose
     */
    public Pose getCurrentPose() {
	return m_newPose;
    }

}
