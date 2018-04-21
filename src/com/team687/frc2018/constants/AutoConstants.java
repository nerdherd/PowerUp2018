package com.team687.frc2018.constants;

import com.team687.frc2018.utilities.BezierCurve;
import com.team687.frc2018.utilities.NerdyMath;

public class AutoConstants {

    // field measurements (absolute inches)
    // change these
    public static final double kRedStartingWallToSwitchInches = 140;
    public static final double kRedCenterToRightSwitchFence = 70;
    public static final double kRedCenterToRightSwitchInner = 30;
    public static final double kRedCenterToLeftSwitchFence = 60;
    public static final double kRedCenterToLeftSwitchInner = 20;
    public static final double kRedRightSwitchLength = 56;
    public static final double kRedLeftSwitchLength = 56;
    public static final double kRedRightSwitchToFrontScale = 105;
    public static final double kRedLeftSwitchToFrontScale = 105;
    public static final double kRedRightSwitchToMidScale = 130;
    public static final double kRedLeftSwitchToMidScale = 130;
    public static final double kRedRightSideWallToScale = 72;
    public static final double kRedLeftSideWallToScale = 72;
    public static final double kPowerCubeLength = 13;

    public static final double kRobotToSecondCubeScale = 65;
    public static final double kBackUpFromSwitch = 40;
    public static final double kRobotToSecondCubeSwitch = 40;

    // field measurements for origin (absolute inches)
    // don't change these unless we have time and they aren't far off from expected
    public static final double kRedExchangeLineToCenterLine = 12;
    public static final double kRedRightSideWallToAllianceStationEdge = 30;
    public static final double kRedLeftSideWallToAllianceStationEdge = 30;

    // robot origins
    // center means center line is y-axis
    // right/left means right/left side wall is y-axis
    public static double kRobotCenterOriginX = NerdyMath
	    .inchesToTicks((DriveConstants.kDrivetrainWidth / 2) - kRedExchangeLineToCenterLine);
    public static double kRobotRightOriginX = NerdyMath
	    .inchesToTicks(-(DriveConstants.kDrivetrainWidth / 2) - kRedRightSideWallToAllianceStationEdge);
    public static double kRobotLeftOriginX = NerdyMath
	    .inchesToTicks((DriveConstants.kDrivetrainWidth / 2) + kRedLeftSideWallToAllianceStationEdge);
    public static double kRobotOriginY = NerdyMath.inchesToTicks(DriveConstants.kDrivetrainLength / 2);

    // path parameters
    public static double kXOffset = 9.5763;
    public static double kYOffset = 35.73925;
    public static double kXHalfOffset = kXOffset / 2;
    public static double kYHalfOffset = kYOffset / 2;
    public static double kRedSwitchRightX = NerdyMath
	    .inchesToTicks((kRedCenterToRightSwitchFence + kRedCenterToRightSwitchInner) / 2); // duncan is a walrus
    public static double kRedSwitchLeftX = NerdyMath
	    .inchesToTicks((-kRedCenterToRightSwitchFence - kRedCenterToRightSwitchInner) / 2);
    public static double kRedSwitchFrontY = NerdyMath
	    .inchesToTicks(kRedStartingWallToSwitchInches - DriveConstants.kDrivetrainLength);
    public static double kRedScaleSideY = NerdyMath.inchesToTicks(kRedStartingWallToSwitchInches + kRedRightSwitchLength
	    + kRedRightSwitchToMidScale - DriveConstants.kDrivetrainLength);

    // straight and turn autos
    public static double kRedWallToPivotPoint = NerdyMath.inchesToTicks(kRedStartingWallToSwitchInches
	    + kRedLeftSwitchLength + 2 * kPowerCubeLength - 0.5 * DriveConstants.kDrivetrainLength);
    public static double kRedPivotPointToMidField = NerdyMath.inchesToTicks(75);

    // Bezier curve paths
    public static BezierCurve kRedCenterToRightSwitchPath = new BezierCurve(kRobotCenterOriginX, kRobotOriginY,
	    kRobotCenterOriginX, kRedSwitchFrontY / 2, kRedSwitchRightX, kRedSwitchFrontY / 3, kRedSwitchRightX,
	    kRedSwitchFrontY);
    public static BezierCurve kRedCenterToLeftSwitchPath = new BezierCurve(kRobotCenterOriginX, kRobotOriginY,
	    kRobotCenterOriginX, kRedSwitchFrontY / 2, kRedSwitchLeftX, kRedSwitchFrontY / 3, kRedSwitchLeftX,
	    kRedSwitchFrontY);
    public static BezierCurve kRedRightSwitchToCenterPath = new BezierCurve(kRedSwitchRightX, 0.8 * kRedSwitchFrontY,
	    kRedSwitchRightX, kRedSwitchFrontY / 3, kRobotCenterOriginX, kRedSwitchFrontY / 2, kRobotCenterOriginX,
	    kRobotOriginY);
    public static BezierCurve kRedLeftSwitchToCenterPath = new BezierCurve(kRedSwitchLeftX, 0.8 * kRedSwitchFrontY,
	    kRedSwitchLeftX, kRedSwitchFrontY / 3, kRobotCenterOriginX, kRedSwitchFrontY / 2, kRobotCenterOriginX,
	    kRobotOriginY);

}