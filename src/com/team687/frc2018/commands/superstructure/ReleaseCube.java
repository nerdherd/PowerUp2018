package com.team687.frc2018.commands.superstructure;

import com.team687.frc2018.commands.intake.OpenClaw;
import com.team687.frc2018.commands.intake.SetIntakeRollerPower;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ReleaseCube extends CommandGroup {

    public ReleaseCube() {
	addSequential(new SetIntakeRollerPower(0));
	addSequential(new OpenClaw());
    }

}
