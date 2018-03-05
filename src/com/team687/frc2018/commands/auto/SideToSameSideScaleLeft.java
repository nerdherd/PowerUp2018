package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.drive.DriveAtHeading;
import com.team687.frc2018.constants.AutoConstants;
import com.team687.frc2018.utilities.NerdyMath;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SideToSameSideScaleLeft extends CommandGroup {

    public SideToSameSideScaleLeft() {
	addSequential(
		new DriveAtHeading(.2, 180, NerdyMath.inchesToTicks(AutoConstants.kRedStartingWallToPivotPoint), .004));
	addSequential(new DriveAtHeading(.2, 180, 10000, .004));
    }

}
