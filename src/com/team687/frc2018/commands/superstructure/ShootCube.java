package com.team687.frc2018.commands.superstructure;

import com.team687.frc2018.commands.WaitTime;
import com.team687.frc2018.commands.intake.OpenClaw;
import com.team687.frc2018.commands.intake.SetIntakeRollerPower;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShootCube extends CommandGroup {

    public ShootCube() {
	addSequential(new SetIntakeRollerPower(-SuperstructureConstants.kRollerPower));
	addSequential(new WaitTime(1));
	addSequential(new OpenClaw());
    }

}
