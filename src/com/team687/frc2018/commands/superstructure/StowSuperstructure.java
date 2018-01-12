package com.team687.frc2018.commands.superstructure;

import com.team687.frc2018.commands.arm.SetArmPosition;
import com.team687.frc2018.commands.intake.SetIntakeRollerPower;
import com.team687.frc2018.commands.wrist.SetWristPosition;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StowSuperstructure extends CommandGroup {

    public StowSuperstructure() {
	addSequential(new SetIntakeRollerPower(0));
	addSequential(new SetWristPosition(SuperstructureConstants.kWristStowPos));
	addSequential(new SetArmPosition(SuperstructureConstants.kArmDownPos));
    }

}
