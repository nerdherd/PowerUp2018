package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.drive.DriveBezierPath;
import com.team687.frc2018.commands.drive.DriveStraightDistance;
import com.team687.frc2018.commands.drive.DriveTime;
import com.team687.frc2018.commands.drive.TurnToAngle;
import com.team687.frc2018.commands.drive.WaitTime;
import com.team687.frc2018.commands.intake.OuttakeRollers;
import com.team687.frc2018.commands.intake.SetIntakeRollerPower;
import com.team687.frc2018.commands.superstructure.BackwardsScaleToStow;
import com.team687.frc2018.commands.superstructure.DefaultIntake;
import com.team687.frc2018.commands.superstructure.DefaultStow;
import com.team687.frc2018.commands.superstructure.StowToBackwardsScale;
import com.team687.frc2018.commands.superstructure.SwitchScorePosition;
import com.team687.frc2018.constants.AutoConstants;
import com.team687.frc2018.utilities.NerdyMath;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftToLeftScaleSwitchAuto extends CommandGroup {

    public LeftToLeftScaleSwitchAuto() {
	addParallel(new DefaultStow());
	addSequential(new DriveBezierPath(AutoConstants.kRedLeftSameSideScalePath, -0.6, 0.008, 0.0005, true));

	addParallel(new StowToBackwardsScale());
	addSequential(new WaitTime(2));
	addParallel(new SetIntakeRollerPower(0.7));
	addSequential(new WaitTime(0.5));
	addParallel(new BackwardsScaleToStow());
	addSequential(new WaitTime(2));

	addSequential(new TurnToAngle(5));
	addParallel(new DefaultIntake());
	addSequential(new DriveStraightDistance(NerdyMath.inchesToTicks(AutoConstants.kRobotToSecondCube), 5, 5, 0.5));

	addParallel(new SwitchScorePosition());
	addSequential(new DriveTime(-0.4, 0.687));
	addSequential(new DriveTime(0.4, 1));
	addParallel(new OuttakeRollers(0.5));
    }

}
