package com.team687.frc2018.commands.superstructure;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ForwardsScaleToStow extends Command {

    private double m_startTime;

    public ForwardsScaleToStow() {
	requires(Robot.arm);
	requires(Robot.wrist);
	requires(Robot.intake);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "ForwardsScaleToStow");

	m_startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
	Robot.intake.setRollerPower(0);
	Robot.wrist.setPosition(SuperstructureConstants.kWristStowArmOffsetPos);
	// give the wrist a head start
	if (Timer.getFPGATimestamp() - m_startTime > 0.5) {
	    Robot.arm.setPosition(SuperstructureConstants.kArmOffsetPos);
	}
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
