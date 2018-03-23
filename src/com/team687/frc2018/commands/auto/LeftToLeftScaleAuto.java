package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.drive.DriveBezierPath;
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
	addParallel(new DefaultStow());
	addSequential(new DriveBezierPath(AutoConstants.kRedLeftSameSideScalePath, -0.6, 0.008, 0.0005, true));

	addParallel(new StowToBackwardsScale());
	addSequential(new WaitTime(2.5));
	addParallel(new SetIntakeRollerPower(0.7));
	addSequential(new WaitTime(0.5));
	addParallel(new BackwardsScaleToStow());
	addSequential(new WaitTime(2.5));
	addParallel(new DefaultStow());

	addSequential(new TurnToAngle(0));
	addParallel(new DefaultIntake());
	addSequential(new ResetDriveEncoders());
	addSequential(new DriveStraightDistance(NerdyMath.inchesToTicks(AutoConstants.kRobotToSecondCubeScale), -5, 5, 0.5));
	addSequential(new WaitTime(0.3));

	addSequential(new ResetDriveEncoders());
	addSequential(
		new DriveStraightDistance(-NerdyMath.inchesToTicks(AutoConstants.kRobotToSecondCubeScale), -185, 5, 0.5)); // check if this is too high
	addParallel(new DefaultStow());
	addSequential(new TurnToAngle(20));
//
//	addParallel(new StowToBackwardsScale());
//	addSequential(new WaitTime(2));
//	addParallel(new SetIntakeRollerPower(0.7));
//	addSequential(new WaitTime(0.5));
//	addParallel(new BackwardsScaleToStow());
    }

}
