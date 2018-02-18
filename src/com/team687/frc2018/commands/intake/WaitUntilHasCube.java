package com.team687.frc2018.commands.intake;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WaitUntilHasCube extends Command {
	
    public WaitUntilHasCube() {
	requires(Robot.intake);
    }
    
    public void initialize() {
	SmartDashboard.putString("Current Command", "WaitUntilHasCube");
    }
    
    @Override
    public void execute() {
	Timer.delay(0.015);
    }
    @Override
    protected boolean isFinished() {
	// TODO Auto-generated method stub
	return Robot.intake.hasCube();
    }

}
