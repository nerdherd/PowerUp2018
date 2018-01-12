package com.team687.frc2018.commands.drive;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Shift to low gear
 */

public class ShiftDown extends Command {

    public ShiftDown() {
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "ShiftDown");
	Robot.drive.shiftDown();
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
	return true;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }

}
