package com.team687.frc2018.constants;

import com.team687.frc2018.utilities.BezierCurve;
import com.team687.frc2018.utilities.NerdyMath;

public class AutoConstants {

    // field measurements (inches)
    // change these
    public static final double kRedStartingWallToSwitchInches = 135;
    public static final double kRedSwitchLengthInches = 53;
    public static final double kRedSwitchToScaleInches = 98;
    public static final double kRedSideWallToSwitchInches = 80;
    public static final double kRedSideWallToScaleInches = 70;

    public static final double kBlueStartingWallToSwitchInches = 135;
    public static final double kBlueSwitchLengthInches = 53;
    public static final double kBlueSwitchToScaleInches = 98;
    public static final double kBlueSideWallToSwitchInches = 80;
    public static final double kBlueSideWallToScaleInches = 70;

    // path parameters (ticks)
    public static final double kRedStartingWallToSwitch = NerdyMath.inchesToTicks(kRedStartingWallToSwitchInches);
    public static final double kRedSwitchLength = NerdyMath.inchesToTicks(kRedSwitchLengthInches);
    public static final double kRedSwitchToScale = NerdyMath.inchesToTicks(kRedSwitchToScaleInches);
    public static final double kRedSideWallToSwitch = NerdyMath.inchesToTicks(kRedSideWallToSwitchInches);
    public static final double kRedSideWallToScale = NerdyMath.inchesToTicks(kRedSideWallToScaleInches);

    public static final double kBlueStartingWallToSwitch = NerdyMath.inchesToTicks(kBlueStartingWallToSwitchInches);
    public static final double kBlueSwitchLength = NerdyMath.inchesToTicks(kBlueSwitchLengthInches);
    public static final double kBlueSwitchToScale = NerdyMath.inchesToTicks(kBlueSwitchToScaleInches);
    public static final double kBlueSideWallToSwitch = NerdyMath.inchesToTicks(kBlueSideWallToSwitchInches);
    public static final double kBlueSideWallToScale = NerdyMath.inchesToTicks(kBlueSideWallToScaleInches);

    // aliasing
    public static double kSwitchX = NerdyMath.inchesToTicks(162 - kRedSideWallToSwitchInches - 20);
    public static double kSwitchBackY = NerdyMath
	    .inchesToTicks(kRedStartingWallToSwitchInches + kRedSwitchLengthInches);
    public static double kMidX = NerdyMath.inchesToTicks(162 - (3 * kRedSideWallToSwitchInches / 5));
    public static double kMidY = kRedStartingWallToSwitch;
    public static double kScaleX = NerdyMath.inchesToTicks(162 - kRedSideWallToScaleInches);
    public static double kScaleY = kSwitchBackY + NerdyMath.inchesToTicks(kRedSwitchToScaleInches);

    // Bezier curve paths
    public static BezierCurve kRedCenterToRightSwitchPath = new BezierCurve(0, 0, 0, kRedStartingWallToSwitch / 2,
	    kSwitchX, kRedStartingWallToSwitch / 3, kSwitchX, kRedStartingWallToSwitch);
    public static BezierCurve kRedCenterToLeftSwitchPath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kRedCenterToRightMidPath = new BezierCurve(0, 0, 0, 4 * kRedStartingWallToSwitch / 5,
	    kMidX, 1 * kRedStartingWallToSwitch / 5, kMidX, kMidY);
    public static BezierCurve kRedCenterToLeftMidPath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kRedRightMidToRightScalePath = new BezierCurve(kMidX, kMidY, kMidX,
	    kMidY + kRedSwitchLength, kMidX, kMidY + (2 * kRedSwitchLength), kScaleX, kScaleY);
    public static BezierCurve kRedLeftMidToLeftScalePath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kRedRightSideToRightScalePath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kRedRightSideToLeftScalePath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kRedLeftSideToRightScalePath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kRedLeftSideToLeftScalePath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kRedRightScaleToCubePath = new BezierCurve(kScaleX, kScaleY, kMidX,
	    kSwitchBackY + (kRedSwitchToScale / 2), kSwitchX, kSwitchBackY + (kRedSwitchToScale / 2), kSwitchX,
	    kSwitchBackY);

    public static BezierCurve kBlueCenterToRightSwitchPath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kBlueCenterToLeftSwitchPath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kBlueCenterToRightMidPath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kBlueCenterToLeftMidPath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kBlueRightMidToRightScalePath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kBlueLeftMidToLeftScalePath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kBlueRightSideToRightScalePath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kBlueRightSideToLeftScalePath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kBlueLeftSideToRightScalePath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kBlueLeftSideToLeftScalePath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);

}
