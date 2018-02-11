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

    // confirmed
    public static final int kArmDownPos = 0;
    public static final int kArmOffsetPos = 682;
    public static final int kArmHorizontalPos = 7000;
    public static final int kArmVerticalPos = 18550;
    public static final int kArmAutoScaleScorePos = 14000;
    public static final int kArmWristSafePos = 10000; // arm position where wrist can start extending (backwards)

    public static final int kArmTolerance = 67;
    public static final int kArmForwardSoftLimit = kArmVerticalPos + 409;
    public static final int kArmReverseSoftLimit = kArmDownPos - 409;

    public static final double kWristP = 0.2046;
    public static final double kWristI = 0;
    public static final double kWristD = 0;
    public static final double kWristMaxVoltageForward = 4;
    public static final double kWristMaxVoltageReverse = -4;
    public static final double kWristRampRate = 1;
    public static final int kWristPeakCurrent = 0;
    public static final int kWristContinuousCurrent = 40;

    // confirmed
    public static final int kWristIntakePos = -2700;
    public static final int kWristStowPos = 0;
    public static final int kWristStowOffsetPos = -262;
    public static final int kWristScoreScalePos = -2056;

    public static final int kWristTolerance = 67;
    public static final int kWristForwardSoftLimit = 0;
    public static final int kWristReverseSoftLimit = 0;

    public static final double kRollerPower = 1;
    public static final int kRollerMaxCurrent = 40;
    public static final double kRollerOuttakeTime = 3.3;

}
