package com.team687.frc2018.commands.drive;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Shift to high gear
 */

public class ShiftUp extends Command {

    public ShiftUp() {
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "ShiftUp");
	Robot.drive.shiftUp();
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
