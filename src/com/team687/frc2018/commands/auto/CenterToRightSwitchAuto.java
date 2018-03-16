package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.drive.DriveBezierPath;
import com.team687.frc2018.commands.intake.SetIntakeRollerPower;
import com.team687.frc2018.commands.superstructure.DefaultStow;
import com.team687.frc2018.constants.AutoConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterToRightSwitchAuto extends CommandGroup {

    public CenterToRightSwitchAuto() {
	addParallel(new DefaultStow());
	addSequential(new DriveBezierPath(AutoConstants.kRedCenterToRightSwitchPath, 0.5, 0.1, 0.001, false));
	addParallel(new SetIntakeRollerPower(0.4));
    }

}
