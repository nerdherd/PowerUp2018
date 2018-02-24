package com.team687.frc2018.commands.superstructure;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.SuperstructureConstants;
import com.team687.frc2018.utilities.NerdyMath;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ForwardsScaleToStow extends Command {

    private double m_startTime;
    private boolean m_isInitialActionFinished = false;

    public ForwardsScaleToStow() {
	requires(Robot.arm);
	requires(Robot.wrist);
	requires(Robot.intake);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "ForwardsScaleToStow");

	m_startTime = Timer.getFPGATimestamp();
	m_isInitialActionFinished = false;
    }

    @Override
    protected void execute() {
    	Robot.intake.setRollerPower(0);
    	
    	
    	if (Robot.wrist.getAngleAbsolute() < 45 && !m_isInitialActionFinished) {
    		Robot.arm.setPosition(SuperstructureConstants.kArmVerticalPos);
    		Robot.wrist.setAngleAbsolute(47);
    	} else {
        	Robot.arm.setPosition(SuperstructureConstants.kArmOffsetPos);
        	double _r3 = SuperstructureConstants.kWristPivotToTip;
        	double theta2 = Robot.arm.getAbsoluteAngle();
        	double x2 = Robot.arm.getX();
        	double y2 = Robot.arm.getY();
        	double _theta3_offset = -16;
        	if (theta2 <= 55) {
        	    Robot.wrist.setPosition(SuperstructureConstants.kWristStowArmOffsetPos);; // DEGREES(ACOS((45-[@x2])/_r3))-theta3_offset
        	} else if (theta2 <= 100) {
        		double alpha = (90 - theta2)/(90-55);
        	    Robot.wrist.setAngleAbsolute(alpha*90 + (1-alpha)*45 - _theta3_offset); // -1.75*[@theta2]+135.3-theta3_offset THIS HAS BEEN CHANGED
        	} else {
        	    Robot.wrist.setAngleAbsolute(NerdyMath.radiansToDegrees(Math.asin((92 - y2) / _r3)) - _theta3_offset); // DEGREES(ASIN((88-[@y2])/_r3))-theta3_offset
        	}
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