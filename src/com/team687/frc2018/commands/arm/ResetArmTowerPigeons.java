package com.team687.frc2018.commands.arm;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Reset angle of the arm and tower by setting offset
 */

public class ResetArmTowerPigeons extends Command {

    public ResetArmTowerPigeons() {
	requires(Robot.arm);
    }

    @Override
    protected void initialize() {
	Robot.arm.resetArmAngle();
	Robot.arm.resetTowerAngle();
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