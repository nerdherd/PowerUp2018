package com.team687.frc2018.commands.superstructure;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AdjustForwardsScale extends Command {

    private double m_armPosition;

    public AdjustForwardsScale(double armPosition) {
	m_armPosition = armPosition;

	requires(Robot.arm);
	requires(Robot.wrist);
	requires(Robot.intake);
    }

    protected void initialize() {
    }

    protected void execute() {
	Robot.intake.setRollerPower(-0.08);
	Robot.arm.setPosition(m_armPosition);
	if (m_armPosition == SuperstructureConstants.kArmMiddleScalePosition) {
	    Robot.wrist.setAngleAbsolute(10);
	} else {
	    Robot.wrist.setAngleAbsolute(85);
	}
    }

    protected boolean isFinished() {
	return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}