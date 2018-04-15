package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.drive.DriveBezierPath;
import com.team687.frc2018.constants.AutoConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestBackwardsBezier extends CommandGroup {

    public TestBackwardsBezier() {
    	addSequential(new DriveBezierPath(AutoConstants.kRedRightSwitchToCenterPath, -0.5, 0.008, 0.001, false));
    }
}
