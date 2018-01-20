package auto;

import com.team687.frc2018.commands.drive.DriveAtHeading;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BlueNonLonerToCloseScale extends CommandGroup {

    public BlueNonLonerToCloseScale() {
    	addSequential(new DriveAtHeading(-0.5, 0, 175000, false, 0.004)); // check angles
    	addSequential(new DriveAtHeading(-0.5, 30, 5000, false, 0.004));
    	addSequential(new ClawReverse());
    }
}
