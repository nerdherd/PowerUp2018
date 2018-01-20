package auto;

import com.team687.frc2018.commands.drive.DriveAtHeading;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BlueLonerToCloseSwitch extends CommandGroup {

    public BlueLonerToCloseSwitch() {
    	addSequential(new DriveAtHeading(-0.5, 0, 10000, false, 0.004));
    }
}
