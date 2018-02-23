
package com.team687.frc2018.commands.auto;

import com.team687.frc2018.commands.WaitTime;
import com.team687.frc2018.commands.drive.DriveAtHeading;
import com.team687.frc2018.commands.intake.SetIntakeRollerPower;
import com.team687.frc2018.commands.superstructure.BackwardsScaleToStow;
import com.team687.frc2018.commands.superstructure.DefaultStow;
import com.team687.frc2018.commands.superstructure.StowToBackwardsScale;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterToRightScale extends CommandGroup {

    public CenterToRightScale() {
	addParallel(new DefaultStow());
	addSequential(new DriveAtHeading(-0.6, 180, 1000, 0.01));
	addSequential(new DriveAtHeading(-0.4, 240, 26000, 0.006));
	addSequential(new DriveAtHeading(-0.4, 180, 35000, 0.003));
	addSequential(new DriveAtHeading(-0.5, 180, 60000, 0.004));
	addSequential(new DriveAtHeading(-0.4, 140, 70000, 0.003));

	addParallel(new StowToBackwardsScale());
	addSequential(new WaitTime(2));
	addSequential(new DriveAtHeading(-0.4, 140, 77000, 0.003));
	addParallel(new SetIntakeRollerPower(0.5));
	addSequential(new WaitTime(1));
	addParallel(new BackwardsScaleToStow());
    }
}