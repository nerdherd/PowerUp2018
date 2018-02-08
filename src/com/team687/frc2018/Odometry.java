package com.team687.frc2018;

import com.team687.frc2018.utilities.CSVDatum;
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

    private double m_yawDegrees, m_pitchDegrees, m_rollDegrees;
    private double m_yawRadians;
    private double m_accelX, m_accelY, m_accelZ;
    private double m_derivedYaw;
    private double m_angularVelocity;
    private double m_arcRadius;

    private double m_leftPosition, m_rightPosition;
    private double m_lastLeftPosition, m_lastRightPosition;
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
	m_leftPosition = Robot.drive.getLeftPosition();
	m_rightPosition = Robot.drive.getRightPosition();
	m_leftVelocity = Robot.drive.getLeftVelocity();
	m_rightVelocity = Robot.drive.getRightVelocity();

	m_yawDegrees = Robot.drive.getCurrentYaw();
	m_pitchDegrees = Robot.drive.getCurrentPitch();
	m_rollDegrees = Robot.drive.getCurrentRoll();
	m_yawRadians = Robot.drive.getCurrentYawRadians();
	m_accelX = Robot.drive.getCurrentAccelX();
	m_accelY = Robot.drive.getCurrentAccelY();
	m_accelZ = Robot.drive.getCurrentAccelZ();

	m_derivedYaw += Kinematics.getDerivedDeltaYaw(m_rightVelocity, m_leftVelocity, m_deltaTime);
	m_angularVelocity = Kinematics.getAngularVelocity(m_rightVelocity, m_leftVelocity);
	m_arcRadius = Kinematics.getCurvatureRadius(m_rightVelocity, m_leftVelocity);

	// estimatePoseVelocity();
	estimatePosePosition();
    }

    public void reportToSmartDashboard() {
	SmartDashboard.putNumber("Left Position", m_leftPosition);
	SmartDashboard.putNumber("Right Position", m_rightPosition);
	SmartDashboard.putNumber("Left Velocity", m_leftVelocity);
	SmartDashboard.putNumber("Right Velocity", m_rightVelocity);

	SmartDashboard.putNumber("Yaw (degrees)", m_yawDegrees);
	SmartDashboard.putNumber("Pitch (degrees)", m_pitchDegrees);
	SmartDashboard.putNumber("Roll (degrees)", m_rollDegrees);
	SmartDashboard.putNumber("Accel X", m_accelX);
	SmartDashboard.putNumber("Accel Y", m_accelY);
	SmartDashboard.putNumber("Accel Z", m_accelZ);

	SmartDashboard.putNumber("X", m_newPose.getX());
	SmartDashboard.putNumber("Y", m_newPose.getY());

	// SmartDashboard.putNumber("Yaw derived from encoders (radians)",
	// m_derivedYaw);
	// SmartDashboard.putNumber("Angular Velocity", m_angularVelocity);
	// SmartDashboard.putNumber("Radius of Curvature", m_arcRadius);
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
	double deltaDistance = ((m_leftPosition - m_lastLeftPosition) + (m_rightPosition - m_lastRightPosition)) / 2;
	m_newPose.setX(m_lastPose.getX() + deltaDistance * Math.sin(m_yawRadians));
	m_newPose.setY(m_lastPose.getY() + deltaDistance * Math.cos(m_yawRadians));
	m_newPose.setTheta(m_yawRadians);
	m_newPose.setTimestamp(Timer.getFPGATimestamp());

	m_lastPose = m_newPose;
    }

    /**
     * @return current estimated pose
     */
    public Pose getCurrentPose() {
	return m_newPose;
    }

    private CSVDatum m_leftPositionData, m_rightPositionData, m_leftVelocityData, m_rightVelocityData;
    private CSVDatum m_yawData, m_pitchData, m_rollData, m_accelXData, m_accelYData, m_accelZData;
    private CSVDatum m_xData, m_yData;

    public void addLoggedData() {
	m_leftPositionData = new CSVDatum("drive_leftPosition");
	m_rightPositionData = new CSVDatum("drive_rightPosition");
	m_leftVelocityData = new CSVDatum("drive_leftVelocity");
	m_rightVelocityData = new CSVDatum("drive_rightVelocity");

	m_yawData = new CSVDatum("drive_yaw");
	m_pitchData = new CSVDatum("drive_pitch");
	m_rollData = new CSVDatum("drive_roll");
	m_accelXData = new CSVDatum("drive_accelX");
	m_accelYData = new CSVDatum("drive_accelY");
	m_accelZData = new CSVDatum("drive_accelZ");

	m_xData = new CSVDatum("drive_x");
	m_yData = new CSVDatum("drive_y");

	Robot.logger.addCSVDatum(m_leftPositionData);
	Robot.logger.addCSVDatum(m_rightPositionData);
	Robot.logger.addCSVDatum(m_leftVelocityData);
	Robot.logger.addCSVDatum(m_rightVelocityData);

	Robot.logger.addCSVDatum(m_yawData);
	Robot.logger.addCSVDatum(m_pitchData);
	Robot.logger.addCSVDatum(m_rollData);
	Robot.logger.addCSVDatum(m_accelXData);
	Robot.logger.addCSVDatum(m_accelYData);
	Robot.logger.addCSVDatum(m_accelZData);

	Robot.logger.addCSVDatum(m_xData);
	Robot.logger.addCSVDatum(m_yData);
    }

    public void updateLog() {
	m_leftPositionData.updateValue(m_leftPosition);
	m_rightPositionData.updateValue(m_rightPosition);
	m_leftVelocityData.updateValue(m_leftVelocity);
	m_rightVelocityData.updateValue(m_rightVelocity);

	m_yawData.updateValue(m_yawDegrees);
	m_pitchData.updateValue(m_pitchDegrees);
	m_rollData.updateValue(m_rollDegrees);
	m_accelXData.updateValue(m_accelX);
	m_accelYData.updateValue(m_accelY);
	m_accelZData.updateValue(m_accelZ);

	m_xData.updateValue(m_newPose.getX());
	m_yData.updateValue(m_newPose.getY());
    }

}
