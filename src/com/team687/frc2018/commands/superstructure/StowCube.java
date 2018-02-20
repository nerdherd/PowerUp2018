package com.team687.frc2018.commands.superstructure;

import com.team687.frc2018.commands.intake.SetIntakeRollerPower;
import com.team687.frc2018.commands.wrist.SetWristPosition;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StowCube extends CommandGroup {

	public StowCube() {
		addParallel(new SetIntakeRollerPower(0.1));
		addSequential(new SetWristPosition(SuperstructureConstants.kWristStowArmOffsetPos));
	}
}
