package com.team687.frc2018.commands.superstructure;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StowToForwardsScaleTimeout extends Command {

	private double m_timeout;
	private double m_startTime;
	
	public StowToForwardsScaleTimeout(double timeout) {
		requires(Robot.arm);
		requires(Robot.wrist);
		requires(Robot.intake);
		m_timeout = timeout;
	}

	@Override
	protected void initialize() {
		SmartDashboard.putString("Current Command", "StowToForwardsScale");
		m_startTime = Timer.getFPGATimestamp();
	}

	@Override
	protected void execute() {
		Robot.intake.setRollerPower(-0.3); // hold cube in place as we go up
		Robot.arm.setPosition(SuperstructureConstants.kArmVerticalPos);
		Robot.wrist.setAngleAbsolute(Robot.wrist.getDesiredAbsoluteAngleGoingUp());
	}

	@Override
	protected boolean isFinished() {
		return Timer.getFPGATimestamp() - m_startTime > m_timeout;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		end();
	}

}