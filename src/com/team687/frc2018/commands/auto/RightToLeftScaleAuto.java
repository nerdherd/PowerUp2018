package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.drive.DriveStraightDistance;
import com.team687.frc2018.commands.drive.ResetDriveEncoders;
import com.team687.frc2018.commands.drive.TurnToAngle;
import com.team687.frc2018.commands.superstructure.DefaultStow;
import com.team687.frc2018.constants.AutoConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightToLeftScaleAuto extends CommandGroup {

    public RightToLeftScaleAuto() {
	addParallel(new DefaultStow());
	addSequential(new DriveStraightDistance(-AutoConstants.kRedWallToPivotPoint, -180, 5));
	addSequential(new TurnToAngle(90, 2, 2));
	addSequential(new ResetDriveEncoders());
	addSequential(new DriveStraightDistance(AutoConstants.kRedPivotPointToMidField, 90, 10));
    }
}
