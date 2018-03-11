package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.drive.DriveBezierPath;
import com.team687.frc2018.commands.intake.OuttakeRollers;
import com.team687.frc2018.commands.superstructure.DefaultStow;
import com.team687.frc2018.constants.AutoConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterToLeftSwitchAuto extends CommandGroup {

    public CenterToLeftSwitchAuto() {
	addParallel(new DefaultStow());
	addSequential(new DriveBezierPath(AutoConstants.kRedCenterToLeftSwitchPath, 0.5, 0.008, 0, false));
	addParallel(new OuttakeRollers(0.5));
    }
}