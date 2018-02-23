package com.team687.frc2018.constants;

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

}
