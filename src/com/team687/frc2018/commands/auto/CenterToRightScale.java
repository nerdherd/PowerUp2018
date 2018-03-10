package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.WaitTime;
import com.team687.frc2018.commands.drive.DriveAtHeading;
import com.team687.frc2018.commands.drive.DriveBezierPath;
import com.team687.frc2018.commands.drive.ResetDriveEncoders;
import com.team687.frc2018.commands.intake.SetIntakeRollerPower;
import com.team687.frc2018.commands.superstructure.BackwardsScaleToStow;
import com.team687.frc2018.commands.superstructure.BackwardsScaleToStowPosition;
import com.team687.frc2018.commands.superstructure.StowToBackwardsScale;
import com.team687.frc2018.constants.AutoConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterToRightScale extends CommandGroup {

	public CenterToRightScale() {
//		addParallel(new BackwardsScaleToStowPosition());
//		addSequential(new DriveAtHeading(-0.6, 180, 2000, 0.004));
//		addSequential(new DriveAtHeading(-0.4, 240, 26000, 0.003));
//		addSequential(new DriveAtHeading(-0.4, 180, 35000, 0.0022));
//		addSequential(new DriveAtHeading(-0.5, 180, 60000, 0.003));
//		addSequential(new DriveAtHeading(-0.4, 140, 70000, 0.003));
//		addParallel(new StowToBackwardsScale());
//		addSequential(new WaitTime(2));
//		addSequential(new DriveAtHeading(-0.4, 140, 77000, 0.003));
//		addParallel(new SetIntakeRollerPower(0.5));
//		addSequential(new WaitTime(1));
//		addParallel(new BackwardsScaleToStowPosition());
//		addSequential(new DriveAtHeading(0.4, ));
		
		addSequential(new DriveBezierPath(AutoConstants.kRedCenterToRightSwitchPath, 0.3, 0.004, 1, true));
	}
}
