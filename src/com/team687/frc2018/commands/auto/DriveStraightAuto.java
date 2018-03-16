package com.team687.frc2018.commands.auto;

import com.team687.frc2018.Robot;
import com.team687.frc2018.commands.drive.DriveStraightDistance;
import com.team687.frc2018.commands.drive.WaitTime;
import com.team687.frc2018.commands.intake.OuttakeRollers;
import com.team687.frc2018.commands.superstructure.DefaultStow;
import com.team687.frc2018.commands.wrist.SetWristPosition;
import com.team687.frc2018.constants.AutoConstants;
import com.team687.frc2018.constants.DriveConstants;
import com.team687.frc2018.utilities.NerdyMath;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveStraightAuto extends CommandGroup {

    public DriveStraightAuto() {
    	addParallel(new DefaultStow());
//    	addParallel(new SetWristPosition(Robot.wrist.angleAbsoluteToTicks(80)));
    	addSequential(new DriveStraightDistance(NerdyMath.inchesToTicks(AutoConstants.kRedStartingWallToSwitchInches - DriveConstants.kDrivetrainLength), 0, 4));
    	addParallel(new OuttakeRollers(0.7));
    }
}
