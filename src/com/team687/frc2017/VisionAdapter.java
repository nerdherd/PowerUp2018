package com.team687.frc2017;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
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
	m_visionTable = NetworkTable.getTable("NerdyVision");
    }

    public boolean isAligned() {
	return m_visionTable.getBoolean("IS_ALIGNED", true);
    }

    public double getAngleToTurn() {
	return m_visionTable.getNumber("ANGLE_TO_TURN", 0);
    }

    public double getTargetArea() {
	return m_visionTable.getNumber("TARGET_AREA", 0);
    }

    public double getDistanceFromTarget() {
	return m_visionTable.getNumber("DISTANCE_FROM_TARGET", 0);
    }

    public double getProcessedTime() {
	return m_visionTable.getNumber("PROCESSED_TIME", 0);
    }

    public void reportToSmartDashboard() {
	SmartDashboard.putNumber("Angle to turn from NerdyVision", getAngleToTurn());
	SmartDashboard.putNumber("Distance from target from NerdyVision", getDistanceFromTarget());
	SmartDashboard.putNumber("Target area from NerdyVision", getTargetArea());
	SmartDashboard.putNumber("Image processing time (seconds)", getProcessedTime());
	SmartDashboard.putBoolean("Aligned to vision target", isAligned());
    }

}
