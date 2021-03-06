package com.team687.frc2018.commands.superstructure;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DefaultStow extends Command {
	
	private boolean m_isBackwards;

    public DefaultStow() {
	requires(Robot.arm);
	requires(Robot.wrist);
	requires(Robot.intake);
    }

    @Override
    protected void initialize() {
    if (Robot.arm.getAngleAbsolute() > )
	SmartDashboard.putString("Current Command", "DefaultStow");
    }

    @Override
    protected void execute() {
	Robot.intake.setRollerPower(-0.254); // hold cube in place as we go up
	Robot.arm.setPosition(SuperstructureConstants.kArmOffsetPos);
	Robot.wrist.setAngleAbsolute(90);
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
