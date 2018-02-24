package com.team687.frc2018.commands.auto;

import com.team687.frc2018.Robot;
import com.team687.frc2018.commands.WaitTime;
import com.team687.frc2018.commands.arm.ResetArmEncoder;
import com.team687.frc2018.commands.drive.DriveAtHeading;
import com.team687.frc2018.commands.drive.ResetDriveEncoders;
import com.team687.frc2018.commands.drive.ResetGyro;
import com.team687.frc2018.commands.intake.SetIntakeRollerPower;
import com.team687.frc2018.commands.superstructure.WristStow;
import com.team687.frc2018.commands.wrist.ResetWristEncoder;
import com.team687.frc2018.commands.wrist.SetWristPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterToSwitch extends CommandGroup {
	
	public CenterToSwitch() {
		addParallel(new WristStow());
		addSequential(new DriveAtHeading(1.0, 0, 2500, 0.004));
		addSequential(new DriveAtHeading(0.8, 40, 20000, 0.008));
		addSequential(new DriveAtHeading(0.8, 0, 24000, 0.008));
		addSequential(new DriveAtHeading(0.2, 0, 32000, 0.004));
//		addParallel(new SetWristPosition(Robot.wrist.angleAbsoluteToTicks(70)));
//		addParallel(new SetIntakeRollerPower(0.5));
	}

}

