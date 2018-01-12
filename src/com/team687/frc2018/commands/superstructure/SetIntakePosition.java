package com.team687.frc2018.commands.superstructure;

import com.team687.frc2018.commands.arm.SetArmPosition;
import com.team687.frc2018.commands.intake.OpenClaw;
import com.team687.frc2018.commands.intake.SetIntakeRollerPower;
import com.team687.frc2018.commands.wrist.SetWristPosition;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SetIntakePosition extends CommandGroup {

    public SetIntakePosition() {
	addSequential(new SetArmPosition(SuperstructureConstants.kArmDownPos));
	addSequential(new SetWristPosition(SuperstructureConstants.kWristIntakePos));
	addSequential(new OpenClaw());
	addSequential(new SetIntakeRollerPower(SuperstructureConstants.kRollerPower));
    }

}
