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
	public static final int kArmPeakCurrent = 0;
	public static final int kArmContinuousCurrent = 60;
	//
	// public static final int kArmDownPos = 0;
	// public static final int kArmOffsetPos = 682;
	// public static final int kArmHorizontalPos = 7000;
	// public static final int kArmVerticalPos = 18550;

	public static final int kArmDownPos = 0;
	public static final int kArmOffsetPos = 409;
	public static final int kArmHorizontalPos = 5200;
	public static final int kArmVerticalPos = 12000;

	// public static final int kArmAutoScaleScorePos = 18025; // scoring
	// backwards
	// public static final int kArmWristSafePos = 10000; // arm position where
	// // wrist can start
	// // extending (scale
	// // scoring)

	public static final int kArmAutoScaleScorePos = -9900; // scoring backwards
	public static final int kArmWristSafePos = 10000; // arm position where
														// wrist can start
														// extending (scale
														// scoring)
	public static final int kArmTolerance = 67;
	public static final int kArmForwardSoftLimit = kArmVerticalPos + 409;
	public static final int kArmReverseSoftLimit = kArmDownPos - 409;

	// independent wrist movement
	// public static final double kWristP = 0.2046;
	// public static final double kWristI = 0;
	// public static final double kWristD = 0;
	// public static final double kWristMaxVoltageForward = 4;
	// public static final double kWristMaxVoltageReverse = -4;

	// wrist synced with arm
	// public static final double kWristF = 0.68; disabled b/c is not working
	public static final double kWristF = 0;
	// public static final double kWristP = 3.5;
	public static final double kWristP = .3;
	public static final double kWristI = 0;
	public static final double kWristD = 0;
	// public static final int kWristCruiseVelocity = 1128;
	// public static final int kWristAcceleration = 1128;

	public static final int kWristCruiseVelocity = 500;
	public static final int kWristAcceleration = 500;

	public static final double kWristMaxVoltageForward = 12;
	public static final double kWristMaxVoltageReverse = -12;
	public static final double kWristRampRate = 0;
	public static final int kWristPeakCurrent = 0;
	public static final int kWristContinuousCurrent = 40;
	//
	// public static final int kWristIntakePos = -2500;
	// public static final int kWristStowPos = 0;
	// public static final int kWristStowArmOffsetPos = -262;
	// public static final int kWristScoreForwardsScalePos = -6300;
	// public static final int kWristStowArmHorizontalPos = -2000;
	// public static final int kWristScoreBackwardsScalePos = -3000;

	public static final int kWristIntakePos = -3480;
	public static final int kWristStowPos = -50;
	public static final int kWristStowArmOffsetPos = -919;
	public static final int kWristScoreForwardsScalePos = -6770;
	// public static final int kWristStowArmHorizontalPos = -2000;
	public static final int kWristScoreBackwardsScalePos = -1330;

	public static final int kWristTolerance = 67;
	public static final int kWristForwardSoftLimit = 0;
	public static final int kWristReverseSoftLimit = 0;

	public static final int kWristTestingForwardSoftLimit = -1678;
	public static final int kWristTestingReverseSoftLimit = -7150;

	public static final double kRollerPower = 1;
	public static final int kRollerMaxCurrent = 40;
	public static final double kRollerOuttakeTime = 3.3;

}
