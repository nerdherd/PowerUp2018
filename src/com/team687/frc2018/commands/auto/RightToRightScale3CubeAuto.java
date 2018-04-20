package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.drive.DriveAtHeading;
import com.team687.frc2018.commands.drive.DriveStraightDistance;
import com.team687.frc2018.commands.drive.DriveTime;
import com.team687.frc2018.commands.drive.ResetDriveEncoders;
import com.team687.frc2018.commands.drive.TurnToAngle;
import com.team687.frc2018.commands.drive.WaitTime;
import com.team687.frc2018.commands.intake.OuttakeRollers;
import com.team687.frc2018.commands.intake.SetIntakeRollerPower;
import com.team687.frc2018.commands.superstructure.DefaultIntake;
import com.team687.frc2018.commands.superstructure.DefaultStow;
import com.team687.frc2018.commands.superstructure.StowToBackwardsScale;
import com.team687.frc2018.constants.AutoConstants;
import com.team687.frc2018.utilities.NerdyMath;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightToRightScale3CubeAuto extends CommandGroup {

    public RightToRightScale3CubeAuto() {
	// doot doot doot
	addParallel(new DefaultStow());
	// addSequential(new DriveAtHeading(-0.4, 180,
	// NerdyMath.inchesToTicks(0.05 *AutoConstants.kRedStartingWallToSwitchInches),
	// 0.004));
	addSequential(new DriveAtHeading(-0.9, 180,
		NerdyMath.inchesToTicks(AutoConstants.kRedStartingWallToSwitchInches), 0.004));

	// curve to scale and score
	addParallel(new StowToBackwardsScale());

	addSequential(new DriveAtHeading(-0.6, 150,
		NerdyMath.inchesToTicks(
			AutoConstants.kRedStartingWallToSwitchInches + 0.5 * AutoConstants.kRedLeftSwitchToFrontScale),
		0.002));
	addSequential(new DriveAtHeading(-0.3, 150,
		NerdyMath.inchesToTicks(
			AutoConstants.kRedStartingWallToSwitchInches + 1.2 * AutoConstants.kRedLeftSwitchToFrontScale),
		0.004));

	addParallel(new SetIntakeRollerPower(0.2));
	addSequential(new WaitTime(0.6));

	// // stow and turn
	addParallel(new DefaultIntake());
	// addSequential(new WaitTime(0.5));
	addSequential(new ResetDriveEncoders());
	addSequential(new WaitTime(1)); // tune this value for optimization
	addSequential(new DriveAtHeading(0.3, 10, NerdyMath.inchesToTicks(AutoConstants.kRobotToSecondCubeScale * 0.8),
		0.008));

	// // get second cube
	addSequential(new ResetDriveEncoders());
	addParallel(new DefaultStow());
	addSequential(new DriveStraightDistance(0.5 * -NerdyMath.inchesToTicks(AutoConstants.kRobotToSecondCubeScale),
		-165, 2, 0.7));
	addParallel(new StowToBackwardsScale());
	addParallel(new TurnToAngle(-30, 2, 2));
	// // score second cube and stow
	addSequential(new WaitTime(0.2));
	addSequential(new DriveTime(-0.5, 0.2));
	addParallel(new OuttakeRollers(0.5));
	addSequential(new WaitTime(0.3));
	addParallel(new DefaultIntake());
	addSequential(new ResetDriveEncoders());
	addSequential(new WaitTime(1)); // tune this value for optimization
	addSequential(
		new DriveAtHeading(0.3, 30, NerdyMath.inchesToTicks(AutoConstants.kRobotToSecondCubeScale * 1), 0.008));
	addSequential(new ResetDriveEncoders());
	addParallel(new DefaultStow());
	// addSequential(new DriftTurnToAngle(-0.5, -150,
	// NerdyMath.inchesToTicks(AutoConstants.kRobotToSecondCubeScale * 1), 0.008));
    }
}