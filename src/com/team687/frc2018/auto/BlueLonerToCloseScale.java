package auto;

import com.team687.frc2018.commands.drive.ArcTurn;
import com.team687.frc2018.commands.drive.DriveAtHeading;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BlueLonerToCloseScale extends CommandGroup {

    public BlueLonerToCloseScale() {
    	
    	// measurements are estimates; change later
    	
    	addSequential(new DriveAtHeading(-0.5, 0, 175000, false, 0.004));
    	addSequential(new DriveAtHeading(-0.5, 30, 5000, false, 0.004));
    	addSequential(new ClawReverse());
    }
}
