package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.drive.DriveBezierPath;
import com.team687.frc2018.commands.intake.SetIntakeRollerPower;
import com.team687.frc2018.commands.superstructure.BackwardsScaleToStow;
import com.team687.frc2018.constants.AutoConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterToRightSwitch extends CommandGroup {

    public CenterToRightSwitch() {
       addParallel(new BackwardsScaleToStow());
       addSequential(new DriveBezierPath(AutoConstants.kRedCenterToRightSwitchPath, 0.5, 0.008, 0.001, true));
       addParallel(new SetIntakeRollerPower(0.4));
    }
}