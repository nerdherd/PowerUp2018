package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.drive.DriveBezierPath;
import com.team687.frc2018.commands.drive.DriveStraightDistance;
import com.team687.frc2018.commands.drive.DriveTime;
import com.team687.frc2018.commands.drive.ResetDriveEncoders;
import com.team687.frc2018.commands.drive.WaitTime;
import com.team687.frc2018.commands.intake.OuttakeRollers;
import com.team687.frc2018.commands.superstructure.DefaultIntake;
import com.team687.frc2018.commands.superstructure.DefaultStow;
import com.team687.frc2018.commands.superstructure.SwitchScorePositionAuto;
import com.team687.frc2018.constants.AutoConstants;
import com.team687.frc2018.utilities.BezierCurve;
import com.team687.frc2018.utilities.NerdyMath;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterToRightSwitchAuto extends CommandGroup {

    public CenterToRightSwitchAuto() {
	addParallel(new SwitchScorePositionAuto());
	addSequential(new DriveBezierPath(AutoConstants.kRedCenterToRightSwitchPath, 0.5, 0.008, 0.001, false));
	addParallel(new OuttakeRollers(0.4));
	addSequential(new DriveTime(0.3, 0.5));
	addSequential(new ResetDriveEncoders());

	addSequential(new DriveBezierPath(AutoConstants.kRedRightSwitchToCenterPath, -0.5, 0.008, 0.001, false));
	addParallel(new DefaultIntake());
	addSequential(new WaitTime(0.1));
	addSequential(new ResetDriveEncoders());
	addSequential(
		new DriveStraightDistance(NerdyMath.inchesToTicks(AutoConstants.kRobotToSecondCubeSwitch), 0, 3, 0.5));
	addParallel(new DefaultStow());
	addSequential(new ResetDriveEncoders());
	addSequential(new DriveStraightDistance(NerdyMath.inchesToTicks(-AutoConstants.kRobotToSecondCubeSwitch * 1.25),
		-180, 3, 0.5));

	addParallel(new SwitchScorePositionAuto());
	addSequential(new ResetDriveEncoders());
	addSequential(new DriveBezierPath(new BezierCurve(AutoConstants.kRobotCenterOriginX,
		AutoConstants.kRobotOriginY, AutoConstants.kRobotCenterOriginX, AutoConstants.kRedSwitchFrontY / 2,
		AutoConstants.kRedSwitchRightX, AutoConstants.kRedSwitchFrontY / 3, AutoConstants.kRedSwitchRightX,
		AutoConstants.kRedSwitchFrontY), 0.5, 0.008, 0.001, false));
	addParallel(new OuttakeRollers(0.4));
	addSequential(new DriveTime(0.5, 0.5));
    }
}
