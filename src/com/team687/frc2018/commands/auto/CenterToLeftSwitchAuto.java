package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.drive.DriveBezierPath;
import com.team687.frc2018.commands.drive.DriveStraightDistance;
import com.team687.frc2018.commands.drive.DriveTime;
import com.team687.frc2018.commands.drive.ResetDriveEncoders;
import com.team687.frc2018.commands.drive.TurnTime;
import com.team687.frc2018.commands.drive.TurnToAngle;
import com.team687.frc2018.commands.drive.WaitTime;
import com.team687.frc2018.commands.intake.OuttakeRollers;
import com.team687.frc2018.commands.superstructure.DefaultIntake;
import com.team687.frc2018.commands.superstructure.DefaultStow;
import com.team687.frc2018.commands.superstructure.SwitchScorePosition;
import com.team687.frc2018.constants.AutoConstants;
import com.team687.frc2018.utilities.NerdyMath;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterToLeftSwitchAuto extends CommandGroup {

    public CenterToLeftSwitchAuto() {
	addParallel(new SwitchScorePosition());
	addSequential(new DriveBezierPath(AutoConstants.kRedCenterToLeftSwitchPath, 0.5, 0.008, 0.001, false));
	addParallel(new OuttakeRollers(0.4));
	addSequential(new DriveTime(0.5, 0.5));

	addSequential(new WaitTime(0.1));
	addParallel(new DefaultStow());
	addSequential(new ResetDriveEncoders());
	addSequential(
		new DriveStraightDistance(NerdyMath.inchesToTicks(-AutoConstants.kBackUpFromSwitch), -180, 3, 0.7));
	addParallel(new DefaultIntake());
	addSequential(new TurnToAngle(45, 2, 2));
	addSequential(new ResetDriveEncoders());
	addSequential(new DriveStraightDistance(NerdyMath.inchesToTicks(AutoConstants.kRobotToSecondCubeSwitch + 10),
		45, 3, 0.5));
	// addSequential(new WaitTime(0.1));
	addSequential(new TurnTime(0.5, 0.5));
	addSequential(new TurnTime(-0.5, 0.5));
	addSequential(new ResetDriveEncoders());
	addSequential(new DriveStraightDistance(NerdyMath.inchesToTicks(-AutoConstants.kRobotToSecondCubeSwitch - 30),
		-225, 4, 0.5));
	addSequential(new TurnToAngle(0, 2, 1.5));
	addSequential(new ResetDriveEncoders());
	addParallel(new SwitchScorePosition());
	addSequential(new DriveStraightDistance(NerdyMath.inchesToTicks(AutoConstants.kBackUpFromSwitch), 0, 3, 0.7));
	addSequential(new DriveTime(0.7, 1.0));
	addParallel(new OuttakeRollers(0.4));
    }
}
