package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.drive.DriveStraightDistance;
import com.team687.frc2018.commands.drive.ResetDriveEncoders;
import com.team687.frc2018.commands.drive.TurnToAngle;
import com.team687.frc2018.commands.intake.SetIntakeRollerPower;
import com.team687.frc2018.commands.superstructure.DefaultStow;
import com.team687.frc2018.constants.AutoConstants;
import com.team687.frc2018.constants.DriveConstants;
import com.team687.frc2018.utilities.NerdyMath;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterToRightSwitchAuto extends CommandGroup {

    public CenterToRightSwitchAuto() {
    	addParallel(new DefaultStow());
    	
//    	addSequential(new DriveBezierCurve(AutoConstants.kRedCenterToRightSwitchPath, 0.5, 
    	addSequential(new DriveStraightDistance(AutoConstants.kRedSwitchFrontY/3, 0, 3));
    	addSequential(new TurnToAngle(30));
    	addSequential(new ResetDriveEncoders());
    	addSequential(new DriveStraightDistance(AutoConstants.kRedSwitchRightX * 2, 30, 3));
    	addSequential(new TurnToAngle(0));
    	addSequential(new ResetDriveEncoders());
    	addSequential(new DriveStraightDistance(AutoConstants.kRedSwitchFrontY * 2 / 3 - 
    			AutoConstants.kRedSwitchRightX * Math.sqrt(3), 0, 3));
    	
    	addParallel(new SetIntakeRollerPower(0.4));

    }

}
