package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.antifoulthing.DeployAntiFoulThing;
import com.team687.frc2018.commands.drive.DriveBezierPath;
import com.team687.frc2018.commands.drive.DriveStraightDistance;
import com.team687.frc2018.commands.drive.DriveTime;
import com.team687.frc2018.commands.drive.ResetDriveEncoders;
import com.team687.frc2018.commands.drive.TurnToAngle;
import com.team687.frc2018.commands.drive.WaitTime;
import com.team687.frc2018.commands.intake.OuttakeRollers;
import com.team687.frc2018.commands.superstructure.DefaultIntake;
import com.team687.frc2018.commands.superstructure.DefaultStow;
import com.team687.frc2018.commands.superstructure.SwitchScorePosition;
import com.team687.frc2018.constants.AutoConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterToRightSwitchAuto extends CommandGroup {

    public CenterToRightSwitchAuto() {
	addParallel(new DeployAntiFoulThing());
	addParallel(new SwitchScorePosition());
	addSequential(new DriveBezierPath(AutoConstants.kRedCenterToRightSwitchPath, 0.5, 0.008, 0.001, false));
	addParallel(new OuttakeRollers(0.5));
	addSequential(new DriveTime(0.5, 0.5));

//	addSequential(new WaitTime(1));
//	addParallel(new DefaultStow());
//	addSequential(new ResetDriveEncoders());
//	addSequential(new DriveStraightDistance(-AutoConstants.kBackUpFromSwitch, -180, 3, 0.5));
//	addParallel(new DefaultIntake());
//	addSequential(new TurnToAngle(-60));
//	addSequential(new ResetDriveEncoders());
//	addSequential(new DriveStraightDistance(AutoConstants.kRobotToSecondCubeSwitch, -60, 3, 0.4));
//	addSequential(new WaitTime(0.5));
//	addSequential(new ResetDriveEncoders());
//	addSequential(new DriveStraightDistance(-AutoConstants.kRobotToSecondCubeSwitch, -240, 3, 0.4));
//	addParallel(new DefaultStow());
//	addSequential(new TurnToAngle(0));
//	addSequential(new ResetDriveEncoders());
//	addSequential(new DriveStraightDistance(AutoConstants.kBackUpFromSwitch, 0, 3, 0.5));
//	addParallel(new OuttakeRollers(0.5));
//	addSequential(new DriveTime(0.5, 1.0));
    }

}
