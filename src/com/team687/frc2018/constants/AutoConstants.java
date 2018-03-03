package com.team687.frc2018.constants;

import com.team687.frc2018.utilities.BezierCurve;
import com.team687.frc2018.utilities.NerdyMath;

public class AutoConstants {

    // field measurements (inches)
    // change these
    public static final double kRedStartingWallToSwitchInches = 140;
    public static final double kRedCenterToRightSwitchFence = 76.75;
    public static final double kRedCenterToRightSwitchInner = 32.25;
    public static final double kRedCenterToLeftSwitchFence = 76.75;
    public static final double kRedCenterToLeftSwitchInner = 32.25;

    // path parameters (ticks)
    public static double kRobotOriginX = NerdyMath.inchesToTicks((DriveConstants.kDrivetrainWidth / 2) - 12);
    public static double kRobotOriginY = NerdyMath.inchesToTicks(DriveConstants.kDrivetrainLength / 2);
    public static double kRedSwitchRightX = NerdyMath
	    .inchesToTicks((kRedCenterToRightSwitchFence + kRedCenterToRightSwitchInner) / 2); // duncan is a walrus
    public static double kRedSwitchLeftX = NerdyMath
	    .inchesToTicks((-kRedCenterToRightSwitchFence + kRedCenterToRightSwitchInner) / 2);
    public static double kRedSwitchFrontY = NerdyMath
	    .inchesToTicks(kRedStartingWallToSwitchInches - DriveConstants.kDrivetrainLength);

    // Bezier curve paths
    public static BezierCurve kRedCenterToRightSwitchPath = new BezierCurve(kRobotOriginX, kRobotOriginY, kRobotOriginX,
	    kRedSwitchFrontY / 2, kRedSwitchRightX, kRedSwitchFrontY / 3, kRedSwitchRightX, kRedSwitchFrontY);
    public static BezierCurve kRedCenterToLeftSwitchPath = new BezierCurve(kRobotOriginX, kRobotOriginY, kRobotOriginX,
	    kRedSwitchFrontY / 2, kRedSwitchLeftX, kRedSwitchFrontY / 3, kRedSwitchLeftX, kRedSwitchFrontY);
    public static BezierCurve kRedCenterToRightMidPath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
    public static BezierCurve kRedCenterToLeftMidPath = new BezierCurve(0, 0, 0, 0, 0, 0, 0, 0);
}
