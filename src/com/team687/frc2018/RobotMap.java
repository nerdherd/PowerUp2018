package com.team687.frc2018;

import edu.wpi.first.wpilibj.I2C.Port;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */

public class RobotMap {

	public static final int kRightSlaveTalon1ID = 4;
	public static final int kLeftSlaveTalon1ID = 5;
	public static final int kLeftMasterTalonID = 1;
	public static final int kRightMasterTalonID = 0;

	public static final Port navID = Port.kMXP;

	public static final int kArmID = 7;
	public static final int kWristID = 2;
	public static final int kIntakeRollersID = 6;

	public static final int kLimitSwitchID = 9;
	
	public static final int kClawSolenoid1ID = 0;
	public static final int kClawSolenoid2ID = 2;

}