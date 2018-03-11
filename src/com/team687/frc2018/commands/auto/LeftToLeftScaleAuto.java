package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.drive.DriveBezierPath;
import com.team687.frc2018.commands.intake.SetIntakeRollerPower;
import com.team687.frc2018.commands.superstructure.DefaultStow;
import com.team687.frc2018.commands.superstructure.StowToBackwardsScale;
import com.team687.frc2018.constants.AutoConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftToLeftScaleAuto extends CommandGroup {

    public LeftToLeftScaleAuto() {
    	addParallel(new DefaultStow());
    	addSequential(new DriveBezierPath(AutoConstants.kRedLeftSameSideScalePath, -0.6, 0.008, 0.001, true));
//    	addParallel(new StowToBackwardsScale());
//    	addParallel(new SetIntakeRollerPower(0.7));
    }

}
