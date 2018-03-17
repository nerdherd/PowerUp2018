package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.antifoulthing.DeployAntiFoulThing;
import com.team687.frc2018.commands.drive.DriveStraightDistance;
import com.team687.frc2018.commands.drive.ResetDriveEncoders;
import com.team687.frc2018.commands.drive.TurnToAngle;
import com.team687.frc2018.commands.drive.WaitTime;
import com.team687.frc2018.commands.intake.SetIntakeRollerPower;
import com.team687.frc2018.commands.superstructure.BackwardsScaleToStow;
import com.team687.frc2018.commands.superstructure.StowToBackwardsScale;
import com.team687.frc2018.constants.AutoConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftToRightScaleAuto extends CommandGroup {

    public LeftToRightScaleAuto() {
    addParallel(new DeployAntiFoulThing());
	addSequential(new DriveStraightDistance(-AutoConstants.kRedWallToPivotPoint, -180, 5));
	addSequential(new TurnToAngle(-90));
	addSequential(new ResetDriveEncoders());
	addSequential(new DriveStraightDistance(AutoConstants.kRedLeftSwitchToRightScale, -90, 10));
	addSequential(new TurnToAngle(0));
	addSequential(new ResetDriveEncoders());
	addSequential(
		new DriveStraightDistance(-AutoConstants.kRedScaleLeftY + AutoConstants.kRedWallToPivotPoint, -180, 5));

	addParallel(new StowToBackwardsScale());
	addSequential(new WaitTime(3));
	addParallel(new SetIntakeRollerPower(0.7));
	addSequential(new WaitTime(1));
	addParallel(new BackwardsScaleToStow());
    }
}
