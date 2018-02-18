package com.team687.frc2018.commands.wrist;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ExerciseWristEncoder extends Command {


    public ExerciseWristEncoder() {
    	requires(Robot.wrist);
    }

    @Override
    protected void initialize() {
//	SmartDashboard.putString("Current Wrist Command", "SetWristPosition: " + m_position);
    }

    @Override
    protected void execute() {
//    	Robot.wrist.exerciseWristEncoder();
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
    }
}
