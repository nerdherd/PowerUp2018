package com.team687.frc2018.commands.antifoulthing;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RetractAntiFoulThing extends Command {

    public RetractAntiFoulThing() {
	requires(Robot.antiFoulThing);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "RetractAntiFoulThing");
    }

    @Override
    protected void execute() {
    	Robot.antiFoulThing.setPosition(0);
    }

    @Override
    protected boolean isFinished() {
	return false;
    }

    @Override
    protected void end() {
    	Robot.antiFoulThing.setPower(0);
    }

    @Override
    protected void interrupted() {
	end();
    }
}
