
package com.team687.frc2018.constants;

import com.team687.frc2018.utilities.BezierCurve;
import com.team687.frc2018.utilities.NerdyMath;

public class AutoConstants {

    // field measurements (absolute inches)
    // change these
    public static final double kRedStartingWallToSwitchInches = 140;
    public static final double kRedCenterToRightSwitchFence = 70;
    public static final double kRedCenterToRightSwitchInner = 30; // X = 54.75
    public static final double kRedCenterToLeftSwitchFence = 60;
    public static final double kRedCenterToLeftSwitchInner = 20; // X = 50
    public static final double kRedRightSwitchLength = 56;
    public static final double kRedLeftSwitchLength = 56;
    public static final double kRedRightSwitchToScale = 105;
    public static final double kRedLeftSwitchToScale = 104;
//    public static final double kRedRightSideWallToScale = 70;
//    public static final double kRedLeftSideWallToScale = 72; // semi good
    public static final double kRedRightSideWallToScale = 57; // 60 is good
    public static final double kRedLeftSideWallToScale = 59; // 62 is good
//    public static final double kRedRightSideWallToCenterOfScale = 112; // good for scale auto
//    public static final double kRedLeftSideWallToCenterOfScale = 110;
    public static final double kRedRightSideWallToCenterOfScale = 254; // good for incomplete scale auto
    public static final double kRedLeftSideWallToCenterOfScale = 254;
    public static final double kPowerCubeLength = 13;

    public static final double kRobotToSecondCubeScale = 50;
    public static final double kBackUpFromSwitch = 50;
    public static final double kRobotToSecondCubeSwitch = 40;

    // field measurements for origin (absolute inches)
    // don't change these unless we have time and they aren't far off from expected
    public static final double kRedExchangeLineToCenterLine = 12;
    public static final double kRedRightSideWallToAllianceStationEdge = 30;
    public static final double kRedLeftSideWallToAllianceStationEdge = 29;

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
    public static double kRedWallToPivotPoint = NerdyMath.inchesToTicks(kRedStartingWallToSwitchInches
	    + kRedLeftSwitchLength + 2 * kPowerCubeLength - 0.5 * DriveConstants.kDrivetrainLength);
    public static double kRedLeftPivotPointToRightScale = NerdyMath.inchesToTicks(324 - kRedLeftSideWallToCenterOfScale)
	    - kRobotLeftOriginX;
    public static double kRedRightPivotPointToLeftScale = NerdyMath
	    .inchesToTicks(324 - kRedRightSideWallToCenterOfScale) - kRobotLeftOriginX;

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
