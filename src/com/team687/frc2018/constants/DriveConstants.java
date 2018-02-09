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

    // Safety
    public final static int kPeakCurrentLimit = 60;
    public final static int kContinuousCurrentLimit = 50;
    public final static double kVoltageRampRate = 0.05; // seconds

    // Velocity PIDF
    public final static double kRightVelocityF = 0;
    public final static double kRightVelocityP = 0;
    public final static double kRightVelocityI = 0;
    public final static double kRightVelocityD = 0;

    public final static double kLeftVelocityF = 0;
    public final static double kLeftVelocityP = 0;
    public final static double kLeftVelocityI = 0;
    public final static double kLeftVelocityD = 0;

    public final static double kMaxVelocity = 0; // max velocity on ground

    // Distance PID
    public final static PGains kDistRightPGains = new PGains(0, 0, 1.0);
    public final static PGains kDistLeftPGains = new PGains(0, 0, 1.0);
    public final static double kDistD = 0;
    public final static double kDriveDistanceTolerance = 0;
    public final static double kDriveDistanceOscillationCount = 0;

    // Rotation PID
    public final static PGains kRotPGains = new PGains(0, 0, 1.0);
    public final static double kDriveRotationTolerance = 0.5;
    public final static double kDriveRotationDeadband = 0.5;
    public final static int kDriveRotationCounter = 3;

    // Motion Profiling
    public final static double kCruiseVelocity = 0;
    public final static double kMaxAcceleration = 0;
    public final static double kMaxJerk = 0;
    public final static double kV = 0;
    public final static double kA = 0;
    public final static double kDt = 0.01;
    public final static double kDtInMinutes = kDt / 60;

    // Collision Detection
    public final static double kCollisionThreshold = 100000;

    // Teleop
    public final static double kSensitivity = 0.85;
    public final static double kDriveAlpha = 0.125; // Cheesy Drive
    public final static double kJoystickDeadband = 0.02;

    // Bezier Curves
    public final static double kBezierStep = 60;
    public final static PGains kBezierRotPGains = new PGains(0, 0, 1.0);

    // the higher the distance kP is, the less time to declerate
    public final static PGains kBezierDistPGains = new PGains(0, 0, 1.0);

    // the higher the curvature function is, the slower the robot will go during a
    // sharp turn; keep this value under 148 to be safe; if it is zero, the straight
    // power will be static even if dynamic straight power is enabled
    public final static double kCurvatureFunction = 85;

    // subsystem testing
    public final static double kVelocityEpsilon = 254;
    public final static double kCurrentEpsilon = 10;
    public final static double kVoltageEpsilon = 2;

}
