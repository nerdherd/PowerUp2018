package com.team687.frc2018.commands.superstructure;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WristStow extends Command {

	public WristStow() {
		requires(Robot.arm);
		requires(Robot.wrist);
		requires(Robot.intake);
	}

	@Override
	protected void initialize() {
		SmartDashboard.putString("Current Command", "WristStow");
	}

	@Override
	protected void execute() {
		Robot.wrist.setAngleAbsolute(90);
		Robot.arm.setPosition(SuperstructureConstants.kArmOffsetPos);
//		Robot.intake.setRollerPower(-0.3);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		end();
	}

}
