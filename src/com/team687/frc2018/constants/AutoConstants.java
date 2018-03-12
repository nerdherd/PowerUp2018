package com.team687.frc2018.constants;

import com.team687.frc2018.utilities.BezierCurve;
import com.team687.frc2018.utilities.NerdyMath;

public class AutoConstants {

    // field measurements (absolute inches)
    // change these
    public static final double kRedStartingWallToSwitchInches = 143;
    public static final double kRedCenterToRightSwitchFence = 76.75;
    public static final double kRedCenterToRightSwitchInner = 32.25;
    public static final double kRedCenterToLeftSwitchFence = 76.75;
    public static final double kRedCenterToLeftSwitchInner = 32.25;
    public static final double kRedRightSwitchLength = 52;
    public static final double kRedLeftSwitchLength = 52;
    public static final double kRedRightSwitchToScale = 110;
    public static final double kRedLeftSwitchToScale = 110;
    public static final double kRedRightSideWallToScale = 72;
    public static final double kRedLeftSideWallToScale = 72;
    public static final double kPowerCubeLength = 13;

    // field measurements for origin (absolute inches)
    // don't change these unless we have time and they aren't far off from expected
    public static final double kRedExchangeLineToCenterLine = 12;
    public static final double kRedRightSideWallToAllianceStationEdge = 31;
    public static final double kRedLeftSideWallToAllianceStationEdge = 31;

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
    public static double kRedMidRightY = NerdyMath
	    .inchesToTicks(kRedStartingWallToSwitchInches + kRedRightSwitchLength - kYOffset);
    public static double kRedMidLeftY = NerdyMath
	    .inchesToTicks(kRedStartingWallToSwitchInches + kRedLeftSwitchLength - kYOffset);
    public static double kRedScaleRightX = NerdyMath.inchesToTicks(-kRedRightSideWallToScale);
    public static double kRedScaleLeftX = NerdyMath.inchesToTicks(kRedLeftSideWallToScale);
    public static double kRedScaleRightY = NerdyMath.inchesToTicks(
	    kRedStartingWallToSwitchInches + kRedRightSwitchLength + kRedRightSwitchToScale - 1.25 * kYOffset);
    public static double kRedScaleLeftY = NerdyMath.inchesToTicks(
	    kRedStartingWallToSwitchInches + kRedLeftSwitchLength + kRedLeftSwitchToScale - 1.25 * kYOffset);

    // straight and turn autos
    public static double kRedLeftWallToPivotPoint = NerdyMath.inchesToTicks(kRedStartingWallToSwitchInches
	    + kRedLeftSwitchLength + 2 * kPowerCubeLength - 0.5 * DriveConstants.kDrivetrainLength);
    public static double kRedLeftRedSwitchToRightRedScale = NerdyMath.inchesToTicks(240) - kRobotLeftOriginX;

    // Bezier curve paths
    public static BezierCurve kRedCenterToRightSwitchPath = new BezierCurve(kRobotCenterOriginX, kRobotOriginY,
	    kRobotCenterOriginX, kRedSwitchFrontY / 2, kRedSwitchRightX, kRedSwitchFrontY / 3, kRedSwitchRightX,
	    kRedSwitchFrontY);
    public static BezierCurve kRedCenterToLeftSwitchPath = new BezierCurve(kRobotCenterOriginX, kRobotOriginY,
	    kRobotCenterOriginX, kRedSwitchFrontY / 2, kRedSwitchLeftX, kRedSwitchFrontY / 3, kRedSwitchLeftX,
	    kRedSwitchFrontY);
    public static BezierCurve kRedRightSameSideScalePath = new BezierCurve(-kRobotRightOriginX, -kRobotOriginY,
	    -kRobotRightOriginX, -kRedMidRightY, -kRobotRightOriginX, -kRedMidRightY, -kRedScaleRightX,
	    -kRedScaleRightY);
    public static BezierCurve kRedLeftSameSideScalePath = new BezierCurve(-kRobotLeftOriginX, -kRobotOriginY,
	    -kRobotLeftOriginX, -kRedMidLeftY, -kRobotLeftOriginX, -kRedMidLeftY, -kRedScaleLeftX, -kRedScaleLeftY);

}
