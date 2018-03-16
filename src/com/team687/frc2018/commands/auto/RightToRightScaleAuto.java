package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.drive.DriveStraightDistance;
import com.team687.frc2018.commands.drive.ResetDriveEncoders;
import com.team687.frc2018.commands.drive.TurnToAngle;
import com.team687.frc2018.commands.drive.WaitTime;
import com.team687.frc2018.commands.intake.SetIntakeRollerPower;
import com.team687.frc2018.commands.superstructure.DefaultStow;
import com.team687.frc2018.commands.superstructure.StowToBackwardsScale;
import com.team687.frc2018.constants.AutoConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightToRightScaleAuto extends CommandGroup {

    public RightToRightScaleAuto() {
    	addParallel(new DefaultStow());
    	
    	addSequential(new DriveStraightDistance(AutoConstants.kRedScaleRightY - 
    			(AutoConstants.kRedMidRightY - AutoConstants.kRobotRightOriginX) * Math.sqrt(3), 0, 3));
    	addSequential(new TurnToAngle(30));
    	addSequential(new ResetDriveEncoders());
    	addSequential(new DriveStraightDistance((AutoConstants.kRedMidRightY - AutoConstants.kRobotRightOriginX) * 2
    			, 30, 3));
    	
    	addParallel(new StowToBackwardsScale());
    	addSequential(new WaitTime(3));
    	addParallel(new SetIntakeRollerPower(0.7));
    }

}
