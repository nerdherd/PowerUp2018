package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.drive.DriveStraightDistance;
import com.team687.frc2018.commands.drive.ResetDriveEncoders;
import com.team687.frc2018.commands.drive.TurnToAngle;
import com.team687.frc2018.commands.drive.WaitTime;
import com.team687.frc2018.commands.intake.OuttakeRollers;
import com.team687.frc2018.commands.superstructure.BackwardsScaleToStow;
import com.team687.frc2018.commands.superstructure.DefaultStow;
import com.team687.frc2018.commands.superstructure.StowToBackwardsScale;
import com.team687.frc2018.constants.AutoConstants;
import com.team687.frc2018.utilities.NerdyMath;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftToLeftCompatibleScaleAuto extends CommandGroup {

    public LeftToLeftCompatibleScaleAuto() {
	addParallel(new DefaultStow());
	addSequential(new DriveStraightDistance(-AutoConstants.kRedScaleSideY, -180, 5, 0.7));
	addSequential(new TurnToAngle(90, 2, 2));
	addSequential(new WaitTime(0.1));
	addSequential(new ResetDriveEncoders());
	addParallel(new StowToBackwardsScale());

	addSequential(new DriveStraightDistance(NerdyMath.inchesToTicks(0.3 * -AutoConstants.kRedLeftSideWallToScale),
		-90, 4, 0.4));
	addSequential(new TurnToAngle(-110, 2, 2));
	addParallel(new OuttakeRollers(0.7));
	addSequential(new WaitTime(0.3));
	addParallel(new BackwardsScaleToStow());
	addSequential(new ResetDriveEncoders());
	addSequential(new DriveStraightDistance(NerdyMath.inchesToTicks(0.3 * AutoConstants.kRedLeftSideWallToScale),
		90, 4, 0.4));
	addSequential(new TurnToAngle(-170, 2, 2));
    }

}
