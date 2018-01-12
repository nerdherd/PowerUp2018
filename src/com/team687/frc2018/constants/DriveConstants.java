package com.team687.frc2018.constants;

import com.team687.frc2018.utilities.PGains;

/**
 * Drive constants
 */

public class DriveConstants {

    // Conversions
    public final static double kDriveFeetToEncoderUnitsRight = 0;
    public final static double kDriveFeetToEncoderUnitsLeft = 0;
    public final static double kWheelDiameter = 2.54; // in inches
    public final static double kDrivetrainWidth = 25.4; // in inches

    // Distance PID
    public final static PGains kDistLowGearRightPGains = new PGains(0, 0, 1.0);
    public final static PGains kDistLowGearLeftPGains = new PGains(0, 0, 1.0);
    public final static double kDistD = 0;

    public final static PGains kDistHighGearRightPGains = new PGains(0, 0, 1.0);
    public final static PGains kDistHighGearLeftPGains = new PGains(0, 0, 1.0);

    public final static double kDriveDistanceTolerance = 0;
    public final static double kDriveDistanceOscillationCount = 0;

    // Rotation PID
    public final static PGains kRotLowGearPGains = new PGains(0, 0, 1.0);
    public final static PGains kRotHighGearPGains = new PGains(0, 0, 1.0);

    public final static double kDriveRotationTolerance = 0.5;
    public final static double kDriveRotationDeadband = 0.5;
    public final static int kDriveRotationCounter = 3;

    // Motion Profiling
    public final static double kMaxVelocity = 0;
    public final static double kMaxAcceleration = 0;
    public final static double kMaxJerk = 0;
    public final static double kV = 0;
    public final static double kA = 0;
    public final static double kDt = 0.01;
    public final static double kDtInMinutes = kDt / 60;
    public final static double kCruiseVelocity = 0;

    // Collision Detection
    public final static double kCollisionThreshold = 100000;

    // Teleop
    public final static double kSensitivityHigh = 0.85;
    public final static double kSensitivityLow = 0.75;
    public final static double kDriveAlpha = 0.125; // Cheesy Drive
    public final static double kJoystickDeadband = 0.02;

    // Bezier Curves
    public final static double kBezierStep = 60;
    public final static PGains kBezierRotLowGearPGains = new PGains(0, 0, 1.0);
    public final static PGains kBezierRotHighGearPGains = new PGains(0, 0, 1.0);

    // the higher the distance kP is, the less time to declerate
    public final static PGains kBezierDistLowGearPGains = new PGains(0, 0, 1.0);
    public final static PGains kBezierDistHighGearPGains = new PGains(0, 0, 1.0);

    // the higher the curvature function is, the slower the robot will go during a
    // sharp turn; keep this value under 148 to be safe; if it is zero, the straight
    // power will be static even if dynamic straight power is enabled
    public final static double kCurvatureFunction = 85;

    // subsystem testing
    public final static double rpmEpsilon = 254;

}
