package com.team687.frc2018;

import edu.wpi.first.wpilibj.I2C.Port;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */

public class RobotMap {

    public static final int kRightMasterTalonID = 15;
    public static final int kRightSlaveTalon1ID = 14;
    public static final int kLeftMasterTalonID = 12;
    public static final int kLeftSlaveTalon1ID = 13;

    public static final Port navID = Port.kMXP;

    public static final int kArmID = 3;
    public static final int kWristID = 2;
    public static final int kIntakeRollersID = 1;

    public static final int kTowerPigeonID = 3;
    public static final int kArmPigeonID = 2;
    public static final int kWristPigeonID = 1;

    public static final int kLimitSwitchID = 9;

}
