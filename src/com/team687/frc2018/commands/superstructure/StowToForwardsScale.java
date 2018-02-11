package com.team687.frc2018.commands.superstructure;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StowToForwardsScale extends Command {

    private double m_startTime;

    public StowToForwardsScale() {
	requires(Robot.arm);
	requires(Robot.wrist);
	requires(Robot.intake);
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
	if (Robot.arm.getPosition() > SuperstructureConstants.kArmWristSafePos) {
	    Robot.wrist.setPosition(SuperstructureConstants.kWristScoreForwardsScalePos);
	} else if (Timer.getFPGATimestamp() - m_startTime > 0.5) {
	    Robot.wrist.setPosition(SuperstructureConstants.kWristStowArmHorizontalPos);
	} else {
	    Robot.wrist.setPosition(SuperstructureConstants.kWristStowArmOffsetPos);
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
