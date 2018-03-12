package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.drive.DriveStraightDistance;
import com.team687.frc2018.commands.drive.TurnToAngle;
import com.team687.frc2018.commands.drive.WaitTime;
import com.team687.frc2018.constants.AutoConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftToRightScaleAuto extends CommandGroup {

    public LeftToRightScaleAuto() {
    	addSequential(new DriveStraightDistance(-AutoConstants.kRedLeftWallToPivotPoint, -180, 5));
    	addSequential(new TurnToAngle(90));
    	addSequential(new WaitTime(0.000001));
    	addSequential(new DriveStraightDistance(-AutoConstants., -180, ))
    }

}
