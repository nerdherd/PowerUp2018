package com.team687.frc2018;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Vision adapter for use with NerdyVision
 */

public class VisionAdapter {

    private static VisionAdapter m_instance = null;

    public static VisionAdapter getInstance() {
	if (m_instance == null) {
	    m_instance = new VisionAdapter();
	}
	return m_instance;
    }

    private NetworkTable m_visionTable;

    protected VisionAdapter() {
	m_visionTable = Robot.networkTable.getTable("NerdyVision");
    }

    public boolean isAligned() {
	return m_visionTable.getEntry("IS_ALIGNED").getBoolean(false);
    }

    public double getAngleToTurn() {
	return m_visionTable.getEntry("ANGLE_TO_TURN").getDouble(0);
    }

    public double getTargetArea() {
	return m_visionTable.getEntry("TARGET_AREA").getDouble(0);
    }

    public double getDistanceFromTarget() {
	return m_visionTable.getEntry("DISTANCE_FROM_TARGET").getDouble(0);
    }

    public double getProcessedTime() {
	return m_visionTable.getEntry("PROCESSED_TIME").getDouble(0);
    }

    public void reportToSmartDashboard() {
	SmartDashboard.putNumber("Angle to turn from NerdyVision", getAngleToTurn());
	SmartDashboard.putNumber("Distance from target from NerdyVision", getDistanceFromTarget());
	SmartDashboard.putNumber("Target area from NerdyVision", getTargetArea());
	SmartDashboard.putNumber("Image processing time (seconds)", getProcessedTime());
	SmartDashboard.putBoolean("Aligned to vision target", isAligned());
    }

}
