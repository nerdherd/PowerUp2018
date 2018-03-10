package com.team687.frc2018.commands.intake;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class IntakeSequence extends Command {

	private boolean m_isFirstPartDone;
	
    public IntakeSequence() {
    	requires(Robot.arm);
    	requires(Robot.intake);
    	requires(Robot.wrist);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putString("Current Command", "IntakePosition");
    	m_isFirstPartDone = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.arm.setPosition(SuperstructureConstants.kArmOffsetPos);
    	if (!m_isFirstPartDone) {
    		Robot.wrist.setPosition(SuperstructureConstants.kWristIntakePos);
    		if (Robot.wrist.getPosition() < SuperstructureConstants.kWristIntakePos) {
    			Robot.intake.setRollerPower(-0.5);
    			Timer.delay(.330);
    		}
    	} else if (m_isFirstPartDone) {
    		Robot.intake.setRollerPower(-0.1);
    		Robot.wrist.setPosition(SuperstructureConstants.kWristStowArmOffsetPos);
    	}
    	if (Robot.intake.hasCube()) {
    		Timer.delay(.254);
    		m_isFirstPartDone = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
