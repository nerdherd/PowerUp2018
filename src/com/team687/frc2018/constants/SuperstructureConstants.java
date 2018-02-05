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

    public static final int kArmDownPos = 0;
    public static final int kArmOffsetPos = 682;
    public static final int kArmHorizontalPos = 7173;
    public static final int kArmVerticalPos = 20057;

    public static final int kArmTolerance = 67;
    public static final int kArmForwardSoftLimit = kArmVerticalPos;
    public static final int kArmReverseSoftLimit = kArmDownPos;

    public static final double kWristP = 0;
    public static final double kWristI = 0;
    public static final double kWristD = 0;
    public static final double kWristMaxVoltageForward = 1;
    public static final double kWristMaxVoltageReverse = -1;
    public static final double kWristRampRate = 1;
    public static final int kWristPeakCurrent = 40;
    public static final int kWristContinuousCurrent = 40;

    public static final int kWristIntakePos = 0;
    public static final int kWristStowPos = 330;
    public static final int kWristSwitchPos = 148;
    public static final int kWristScaleTopPos = -2122;
    public static final int kWristScaleMidPos = -2056;
    public static final int kWristScaleBottomPos = -2052;
    public static final int kWristScaleBackwardPos = 125;

    public static final int kWristTolerance = 67;
    public static final int kWristForwardSoftLimit = kWristStowPos;
    public static final int kWristReverseSoftLimit = kWristScaleTopPos;

    public static final double kRollerPower = 0.5;
    public static final int kRollerMaxCurrent = 20;
    public static final double kRollerOuttakeTime = 3.3;

}
