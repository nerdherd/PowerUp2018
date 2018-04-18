package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.drive.DriveStraightDistance;
import com.team687.frc2018.commands.superstructure.DefaultStow;
import com.team687.frc2018.constants.AutoConstants;
import com.team687.frc2018.utilities.NerdyMath;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */

public class DriveStraightForwardsWithoutCube extends CommandGroup {

    public DriveStraightForwardsWithoutCube() {
	addParallel(new DefaultStow());
	addSequential(
		new DriveStraightDistance(NerdyMath.inchesToTicks(AutoConstants.kRedStartingWallToSwitchInches), 0, 6));
    }
}
