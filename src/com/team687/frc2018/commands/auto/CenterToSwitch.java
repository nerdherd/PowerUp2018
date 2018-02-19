package com.team687.frc2018.commands.auto;

import com.team687.frc2018.Robot;
import com.team687.frc2018.commands.drive.DriveAtHeading;
import com.team687.frc2018.commands.intake.SetIntakeRollerPower;
import com.team687.frc2018.commands.superstructure.DefaultStow;
import com.team687.frc2018.commands.wrist.SetWristPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterToSwitch extends CommandGroup {

    public CenterToSwitch() {
	addParallel(new DefaultStow());
	addSequential(new DriveAtHeading(0.6, 0, 2500, 0.004));
	addSequential(new DriveAtHeading(0.6, 40, 20000, 0.006));
	addSequential(new DriveAtHeading(0.6, 0, 24000, 0.006));
	addSequential(new DriveAtHeading(0.2, 0, 32000, 0.004));
	addParallel(new SetWristPosition(Robot.wrist.angleAbsoluteToTicks(70)));
	addParallel(new SetIntakeRollerPower(0.5));
    }

}
