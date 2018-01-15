package com.team687.frc2018.constants;

/**
 * Superstructure (arm + wrist + intake) constants
 */

public class SuperstructureConstants {

    public static final double kArmP = 0;
    public static final double kArmI = 0;
    public static final double kArmD = 0;
    public static final double kArmMaxVoltageForward = 2;
    public static final double kArmMaxVoltageReverse = -2;
    public static final double kArmMaxCurrent = 40;

    public static final int kArmDownPos = 0;
    public static final int kArmSwitchPos = 118;
    public static final int kArmScaleBottomPos = 687;
    public static final int kArmScaleMidPos = 971;
    public static final int kArmScaleTopPos = 987;
    public static final int kArmScaleBackwardPos = 1114;
    public static final int kArmWristSafePos = 254; // arm position where wrist can still safely point down

    public static final int kArmTolerance = 687;
    public static final int kArmForwardSoftLimit = kArmScaleBackwardPos;
    public static final int kArmReverseSoftLimit = kArmDownPos;

    public static final double kWristP = 0;
    public static final double kWristI = 0;
    public static final double kWristD = 0;
    public static final double kWristMaxVoltageForward = 2;
    public static final double kWristMaxVoltageReverse = -2;
    public static final double kWristMaxCurrent = 40;

    public static final int kWristIntakePos = 0;
    public static final int kWristStowPos = 330;
    public static final int kWristSwitchPos = 148;
    public static final int kWristScaleTopPos = -2122;
    public static final int kWristScaleMidPos = -2056;
    public static final int kWristScaleBottomPos = -2052;
    public static final int kWristScaleBackwardPos = 125;

    public static final int kWristTolerance = 687;
    public static final int kWristForwardSoftLimit = kWristStowPos;
    public static final int kWristReverseSoftLimit = kWristScaleTopPos;

    public static final double kRollerPower = 0.2;

}
