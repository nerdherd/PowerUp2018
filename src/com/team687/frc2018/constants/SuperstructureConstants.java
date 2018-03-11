package com.team687.frc2018.constants;

/**
 * Superstructure (arm + wrist + intake) constants
 */

public class SuperstructureConstants {

    public static final double kArmF = 0.8197;
    public static final double kArmP = 4;
    public static final double kArmI = 0;
    public static final double kArmD = 0;
    public static final int kArmCruiseVelocity = 1114;
    public static final int kArmAcceleration = 971;

    public static final double kArmMaxVoltageForward = 12;
    public static final double kArmMaxVoltageReverse = -12;
    public static final double kArmRampRate = 0;
    public static final int kArmPeakCurrent = 60;
    public static final int kArmContinuousCurrent = 60;

    public static final double kArmGearRatio = 8.18;
    public static final int kArmAngleOffsetWhenDown = 55;

    public static final int kArmDownPos = 0;
    public static final int kArmOffsetPos = 409;
    public static final int kArmHorizontalPos = 5200;
    public static final int kArmVerticalPos = 12000;
    public static final int kArmAutoScaleScorePos = 11000;
    public static final int kArmWristSafePos = 12000; // arm position where wrist can start extending (scale scoring)

    public static final int kArmTolerance = 67;
    public static final int kArmForwardSoftLimit = kArmVerticalPos + 409;
    public static final int kArmReverseSoftLimit = kArmDownPos - 409;

    public static final double kShoulderPivotX = 5;
    public static final double kShoulderPivotY = 41.75;
    public static final double kShoulderToWristPivot = 41;
    public static final double kWristPivotToTip = 20;

    public static final double kWristF = 0.681318681;
    public static final double kWristP = 3;
    public static final double kWristI = 0;
    public static final double kWristD = 0;
    public static final int kWristCruiseVelocity = 1241;
    public static final int kWristAcceleration = 1254;
    public static final double kWristMaxVoltageForward = 12;
    public static final double kWristMaxVoltageReverse = -12;

    public static final double kWristRampRate = 0;
    public static final int kWristPeakCurrent = 40;
    public static final int kWristContinuousCurrent = 40;

    public static final int kWristResetPosition = 0;
    public static final int kWristIntakePos = -3000;
    public static final int kWristStowPos = 0;
    public static final int kWristStowArmOffsetPos = -1000;
    public static final int kWristBackwardsScorePos = -4000;
    public static final int kWristScoreBackwardsScalePos = -3000;

    public static final int kWristTolerance = 67;
    public static final int kWristForwardSoftLimit = -1678;
    public static final int kWristReverseSoftLimit = -7150;

    public static final double kRollerPower = 0.5;
    public static final int kRollerMaxCurrent = 8;
    public static final double kRollerOuttakeTime = 3.3;

}
