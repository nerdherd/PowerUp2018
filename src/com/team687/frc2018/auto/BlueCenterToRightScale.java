package auto;

import com.team687.frc2018.commands.drive.DriveAtHeading;

import edu.wpi.first.wpilibj.command.CommandGroup;

import com.team687.frc2018.commands.drive.ArcTurn;
import com.team687.frc2018.commands.*;

/**
 *
 */
public class BlueCenterToRightScale extends CommandGroup {

	public BlueCenterToRightScale() {
		
		// new DriveAtHeading doesn't have isHighGear boolean
		// measurements are estimates; change later
		// new ArcTurn on new code has desiredAngle, isRightPowered, and timeOut
		
		addSequential(new DriveAtHeading(-0.5, 0, 10000, false, 0.004)); 
		addSequential(new DriveAtHeading(-0.5, 50, 140000, false, 0.004)); // change heading and distance based on encoder input
		addSequential(new DriveAtHeading(-0.5, 0, 260000, false, 0.004)); 
		addSequential(new DriveAtHeading(-0.5, -20, 2500, false, 0.004)); 
		addSequential(new ClawReverse()); // command is on new code

	}
}
