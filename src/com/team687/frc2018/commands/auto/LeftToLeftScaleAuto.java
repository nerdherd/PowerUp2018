package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.drive.DriveAtHeading;
import com.team687.frc2018.commands.drive.DriveStraightDistance;
import com.team687.frc2018.commands.drive.ResetDriveEncoders;
import com.team687.frc2018.commands.drive.TurnToAngle;
import com.team687.frc2018.commands.drive.WaitTime;
import com.team687.frc2018.commands.intake.SetIntakeRollerPower;
import com.team687.frc2018.commands.superstructure.BackwardsScaleToStow;
import com.team687.frc2018.commands.superstructure.DefaultIntake;
import com.team687.frc2018.commands.superstructure.DefaultStow;
import com.team687.frc2018.commands.superstructure.StowToBackwardsScale;
import com.team687.frc2018.constants.AutoConstants;
import com.team687.frc2018.utilities.NerdyMath;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftToLeftScaleAuto extends CommandGroup {

    public LeftToLeftScaleAuto() {
	// doot doot doot
	addParallel(new DefaultStow());
	addSequential(new DriveAtHeading(-0.4, 180,
		NerdyMath.inchesToTicks(0.1 * AutoConstants.kRedStartingWallToSwitchInches), 0.008));
	addSequential(new DriveAtHeading(-0.8, 180,
		NerdyMath.inchesToTicks(AutoConstants.kRedStartingWallToSwitchInches), 0.008));

	// curve to scale and score
	addParallel(new StowToBackwardsScale());
	addSequential(
		new DriveAtHeading(-0.6, 180,
			NerdyMath.inchesToTicks(
				AutoConstants.kRedStartingWallToSwitchInches + AutoConstants.kRedLeftSwitchLength),
			0.008));
	addSequential(
		new DriveAtHeading(-0.5, 220,
			NerdyMath.inchesToTicks(AutoConstants.kRedStartingWallToSwitchInches
				+ AutoConstants.kRedLeftSwitchLength + 0.3 * AutoConstants.kRedLeftSwitchToScale),
			0.008));
	addSequential(
		new DriveAtHeading(-0.3, 220,
			NerdyMath.inchesToTicks(AutoConstants.kRedStartingWallToSwitchInches
				+ AutoConstants.kRedLeftSwitchLength + 0.7 * AutoConstants.kRedLeftSwitchToScale),
			0.008));
	addParallel(new SetIntakeRollerPower(0.7));
	addSequential(new WaitTime(0.2));

	// stow and turn
	addParallel(new BackwardsScaleToStow());
	addSequential(new WaitTime(1));
	addSequential(new TurnToAngle(-5, 2, 2));
	addSequential(new ResetDriveEncoders());

	// get second cube
	addParallel(new DefaultIntake());
	addSequential(
		new DriveStraightDistance(NerdyMath.inchesToTicks(AutoConstants.kRobotToSecondCubeScale), -5, 2, 0.5));
	addSequential(new WaitTime(0.2));
	addSequential(new ResetDriveEncoders());
	addSequential(new DriveStraightDistance(-NerdyMath.inchesToTicks(AutoConstants.kRobotToSecondCubeScale), -185,
		2, 0.5));
	addParallel(new DefaultStow());
	addSequential(new TurnToAngle(-40, 2, 2));

	// score second cube and stow
	addParallel(new StowToBackwardsScale());
	addSequential(new WaitTime(2));
	addParallel(new SetIntakeRollerPower(0.7));
	addSequential(new WaitTime(0.5));
	addParallel(new BackwardsScaleToStow());
    }

}
