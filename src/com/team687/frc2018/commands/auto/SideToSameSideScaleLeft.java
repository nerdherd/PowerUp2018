package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.WaitTime;
import com.team687.frc2018.commands.drive.DriveAtHeading;
import com.team687.frc2018.commands.drive.DriveBezierPath;
import com.team687.frc2018.commands.intake.SetIntakeRollerPower;
import com.team687.frc2018.commands.superstructure.BackwardsScaleToStow;
import com.team687.frc2018.commands.superstructure.StowToBackwardsScale;
import com.team687.frc2018.constants.AutoConstants;
import com.team687.frc2018.utilities.NerdyMath;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SideToSameSideScaleLeft extends CommandGroup {

    public SideToSameSideScaleLeft() {
//        addSequential(new DriveAtHeading(-0.5, 180, NerdyMath.inchesToTicks(AutoConstants.kRedStartingWallToPivotPoint/10), 0.004));
//        addParallel(new BackwardsScaleToStow());
//        addSequential(new DriveAtHeading(-0.5, 180, NerdyMath.inchesToTicks(AutoConstants.kRedStartingWallToPivotPoint), 0.004));
//        addSequential(new DriveAtHeading(-0.5, 150, NerdyMath.inchesToTicks(AutoConstants.kRedStartingWallToPivotPoint + 90), 0.004));
//        addParallel(new StowToBackwardsScale());
//        addSequential(new WaitTime(2));
//        addSequential(new DriveAtHeading(-0.5, 150, NerdyMath.inchesToTicks(AutoConstants.kRedStartingWallToPivotPoint + 100), 0.004));
//        addSequential(new WaitTime(1));
//        addParallel(new SetIntakeRollerPower(0.5));
//        addSequential(new WaitTime(3));
//        addParallel(new BackwardsScaleToStow());
    	addSequential(new DriveBezierPath(AutoConstants.kRedLeftSameSideScalePath, -0.5, .00811, .00118, true));
    	
    }
}
