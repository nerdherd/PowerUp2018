package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.drive.DriveAtHeading;
import com.team687.frc2018.commands.drive.ResetDriveEncoders;
import com.team687.frc2018.commands.superstructure.BackwardsScaleToStow;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterToRightScale extends CommandGroup{

	public CenterToRightScale() {
		addSequential(new ResetDriveEncoders());
		addParallel(new BackwardsScaleToStow());
		addSequential(new DriveAtHeading(-0.3, 180, 2500, 0.08));
		addSequential(new DriveAtHeading(-0.4, 120, 10000, 0.08));
		
	}
}