package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.drive.DriveAtHeading;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterToSwitch extends CommandGroup {

	public CenterToSwitch() {
		// addParallel(new WristStow());
		addSequential(new DriveAtHeading(1.0, 0, 2500, 0.004));
		addSequential(new DriveAtHeading(0.8, 40, 20000, 0.008));
		addSequential(new DriveAtHeading(0.8, 0, 24000, 0.008));
		addSequential(new DriveAtHeading(0.2, 0, 32000, 0.004));
		// addParallel(new
		// SetWristPosition(Robot.wrist.angleAbsoluteToTicks(70)));
		// addParallel(new SetIntakeRollerPower(0.5));
	}

}
