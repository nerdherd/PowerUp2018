package com.team687.frc2018.commands.superstructure;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StowToBackwardsScale extends Command {

    public StowToBackwardsScale() {
	requires(Robot.arm);
	requires(Robot.wrist);
	requires(Robot.intake);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "ScaleToBackwardsScale");
    }

    @Override
    protected void execute() {
	Robot.intake.setRollerPower(-0.3); // hold cube in place as we go up
	Robot.arm.setPosition(SuperstructureConstants.kArmAutoScaleScorePos);
	if (Robot.arm.getPosition() > SuperstructureConstants.kArmWristSafePos) {
	    Robot.wrist.setPosition(-500);
	} else {
	    Robot.wrist.setPosition(SuperstructureConstants.kWristBackwardsScorePos);
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
