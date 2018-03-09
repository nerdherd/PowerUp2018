package com.team687.frc2018.commands.superstructure;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BackwardsScaleToStow extends Command {

    public BackwardsScaleToStow() {
	requires(Robot.arm);
	requires(Robot.wrist);
	requires(Robot.intake);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "BackwardsScaleToStow");
    }

    @Override
    protected void execute() {
	Robot.intake.setRollerPower(0);
	Robot.wrist.setPosition(SuperstructureConstants.kWristStowArmOffsetPos);
	if (Robot.wrist.getPosition() > SuperstructureConstants.kWristStowArmOffsetPos - 200) {
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
