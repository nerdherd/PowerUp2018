package auto;

import com.team687.frc2018.commands.drive.DriveAtHeading;
import com.team687.frc2018.commands.drive.ResetEncoders;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BlueNonLonerToFarScale extends CommandGroup {

	public BlueNonLonerToFarScale() {
		addSequential(new DriveAtHeading(-0.5, 0, 40000, false, 0.004));
		// addSequential(new ArcTurn(90, false, 1.0));
		addSequential(new ResetEncoders());
		addSequential(new DriveAtHeading(-0.5, 90, 100000, false, 0.004));
		// addSequential(new ArcTurn(-100, false, 1.0));
		addSequential(new DriveAtHeading(-0.5, 90, 2500, false, 0.004));
		// addSequential(new ClawReverse());
	}

}