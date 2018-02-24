package com.team687.frc2018.commands.superstructure;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Perform Pigeon temperature calibration
 */

public class CalibrateGyro extends Command {

	public CalibrateGyro() {
		requires(Robot.wrist);
		requires(Robot.arm);
	}

	@Override
	protected void initialize() {
		Robot.wrist.enterCalibrationMode();
		Robot.arm.enterCalibrationMode();
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