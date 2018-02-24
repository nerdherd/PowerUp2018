package com.team687.frc2018.commands.superstructure;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Reset yaw, pitch, and roll of wrist by setting offset
 */

public class ResetPigeons extends Command {

	public ResetPigeons() {
		requires(Robot.wrist);
		requires(Robot.arm);
	}

	@Override
	protected void initialize() {
		SmartDashboard.putString("Current Superstructure Command", "ResetPigeons");
		Robot.wrist.resetYaw();
		Robot.wrist.resetPitch();
		Robot.wrist.resetRoll();
		Robot.arm.resetArmYaw();
		Robot.arm.resetArmPitch();
		Robot.arm.resetArmRoll();
		Robot.arm.resetTowerYaw();
		Robot.arm.resetTowerPitch();
		Robot.arm.resetTowerRoll();
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