package com.team687.frc2018.constants;

import com.team687.frc2018.utilities.BezierCurve;
import com.team687.frc2018.utilities.NerdyMath;

public class AutoConstants {

    // field measurements (inches)
    // change these
    public static final double kRedStartingWallToSwitchInches = 0;
    public static final double kRedSwitchLengthInches = 0;
    public static final double kRedSwitchToScaleInches = 0;
    public static final double kRedSideWallToSwitchInches = 0;
    public static final double kRedSideWallToScaleInches = 0;

    public static final double kBlueStartingWallToSwitchInches = 0;
    public static final double kBlueSwitchLengthInches = 0;
    public static final double kBlueSwitchToScaleInches = 0;
    public static final double kBlueSideWallToSwitchInches = 0;
    public static final double kBlueSideWallToScaleInches = 0;

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

    // Bezier curve paths
    public static BezierCurve kRedCenterToRightSwitchPath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kRedCenterToLeftSwitchPath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kRedCenterToRightScalePath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kRedCenterToLeftScalePath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kRedRightSideToRightScalePath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kRedRightSideToLeftScalePath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kRedLeftSideToRightScalePath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kRedLeftSideToLeftScalePath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);

    public static BezierCurve kBlueCenterToRightSwitchPath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kBlueCenterToLeftSwitchPath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kBlueCenterToRightScalePath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kBlueCenterToLeftScalePath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kBlueRightSideToRightScalePath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kBlueRightSideToLeftScalePath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kBlueLeftSideToRightScalePath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kBlueLeftSideToLeftScalePath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);

}
