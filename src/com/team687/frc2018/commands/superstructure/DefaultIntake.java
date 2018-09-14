package com.team687.frc2018.commands.superstructure;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DefaultIntake extends Command {

    public DefaultIntake() {
	requires(Robot.arm);
	requires(Robot.wrist);
	requires(Robot.intake);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "IntakePosition");
    }

    @Override
    protected void execute() {
	Robot.arm.setPosition(SuperstructureConstants.kArmOffsetPos);
	if (Robot.arm.getPosition() > SuperstructureConstants.kArmSwitchPos - 1000) {
	    Robot.wrist.setPosition(SuperstructureConstants.kWristStowArmOffsetPos);
	} else {
	    Robot.wrist.setAngleAbsolute(-2);
	}
	Robot.intake.setRollerPower(-1);
	Robot.intake.closeClaw();
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
