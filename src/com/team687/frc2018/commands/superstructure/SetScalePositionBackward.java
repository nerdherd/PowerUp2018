package com.team687.frc2018.commands.superstructure;

import com.team687.frc2018.commands.arm.SetArmPosition;
import com.team687.frc2018.commands.wrist.SetWristPosition;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SetScalePositionBackward extends CommandGroup {

    public SetScalePositionBackward() {
	addSequential(new SetArmPosition(SuperstructureConstants.kArmScaleBackwardPos));
	addSequential(new SetWristPosition(SuperstructureConstants.kWristScaleBackwardPos));
    }

}
