package com.team687.frc2018.commands.superstructure;

import com.team687.frc2018.commands.WaitTime;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Superstructure2MinTest extends CommandGroup {
	
    public Superstructure2MinTest() {
    	//This worked first try, with the older values, on the practice bot.
    	addSequential(new StowToForwardsScaleTimeout(4));
    	addSequential(new ForwardsScaleToStowTimeout(4));
    	addSequential(new StowToForwardsScaleTimeout(4));
    	addSequential(new ForwardsScaleToStowTimeout(4));
    	addSequential(new StowToForwardsScaleTimeout(4));
    	addSequential(new ForwardsScaleToStowTimeout(4));
    	addSequential(new StowToForwardsScaleTimeout(4));
    	addSequential(new ForwardsScaleToStowTimeout(4));
    	addSequential(new StowToForwardsScaleTimeout(4));
    	addSequential(new ForwardsScaleToStowTimeout(4));
    	addSequential(new StowToForwardsScaleTimeout(4));
    	addSequential(new ForwardsScaleToStowTimeout(4));
    	addSequential(new StowToForwardsScaleTimeout(4));
    	addSequential(new ForwardsScaleToStowTimeout(4));
    	addSequential(new StowToForwardsScaleTimeout(4));
    	addSequential(new ForwardsScaleToStowTimeout(4));
    	addSequential(new StowToForwardsScaleTimeout(4));
    	addSequential(new ForwardsScaleToStowTimeout(4));
    	addSequential(new StowToForwardsScaleTimeout(4));
    	addSequential(new ForwardsScaleToStowTimeout(4));
    	addSequential(new StowToForwardsScaleTimeout(4));
    	addSequential(new ForwardsScaleToStowTimeout(4));
    	addSequential(new StowToForwardsScaleTimeout(4));
    	addSequential(new ForwardsScaleToStowTimeout(4));
    	addSequential(new StowToForwardsScaleTimeout(4));
    	addSequential(new ForwardsScaleToStowTimeout(4));
    	addSequential(new StowToForwardsScaleTimeout(4));
    	addSequential(new ForwardsScaleToStowTimeout(4));
    	addSequential(new StowToForwardsScaleTimeout(4));
    	addSequential(new ForwardsScaleToStowTimeout(4));
    }
}
