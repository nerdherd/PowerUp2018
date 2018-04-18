package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.drive.DriveAtHeading;
import com.team687.frc2018.commands.drive.DriveTime;
import com.team687.frc2018.commands.drive.TurnToAngle;
import com.team687.frc2018.commands.intake.OuttakeRollers;
import com.team687.frc2018.commands.superstructure.SwitchScorePositionAuto;
import com.team687.frc2018.constants.AutoConstants;
import com.team687.frc2018.utilities.NerdyMath;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftToLeftSwitchAuto extends CommandGroup {

    public LeftToLeftSwitchAuto() {
	addParallel(new SwitchScorePositionAuto());
	addSequential(new DriveAtHeading(-0.8, 180,
		NerdyMath.inchesToTicks(0.6 * AutoConstants.kRedStartingWallToSwitchInches), 0.004));
	addSequential(new TurnToAngle(-90, 2, 2));
	addSequential(new DriveTime(0.7, 0.6));
	addParallel(new OuttakeRollers(0.7));
	addSequential(new DriveTime(-0.7, 0.6));
    }

}
