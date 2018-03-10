package com.team687.frc2018.commands.superstructure;



import com.team687.frc2018.Robot;
import com.team687.frc2018.commands.intake.SetIntakeRollerPower;
import com.team687.frc2018.commands.wrist.SetWristPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeToStow extends CommandGroup {

    public IntakeToStow() {
       addSequential(new SetWristPosition(Robot.wrist.angleAbsoluteToTicks(90)));
       addSequential(new SetIntakeRollerPower(-0.1254));
    }
}
