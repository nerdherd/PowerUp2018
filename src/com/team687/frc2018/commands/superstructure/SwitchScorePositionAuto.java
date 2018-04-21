package com.team687.frc2018.commands.superstructure;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SwitchScorePositionAuto extends Command {

    public SwitchScorePositionAuto() {
	requires(Robot.wrist);
	requires(Robot.arm);
	requires(Robot.intake);
    }

    protected void initialize() {
    }

    protected void execute() {
	Robot.intake.setRollerPower(-0.1);
	Robot.wrist.setAngleAbsolute(80);
	Robot.arm.setPosition(SuperstructureConstants.kArmOffsetPos);
    }

    protected boolean isFinished() {
	return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
