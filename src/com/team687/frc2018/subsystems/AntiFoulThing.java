package com.team687.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team687.frc2018.RobotMap;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AntiFoulThing extends Subsystem {
	
	private TalonSRX m_deployerThing;

	public AntiFoulThing() {
		m_deployerThing = new TalonSRX(RobotMap.kAntiFoulThingID);
		
		m_deployerThing.config_kP(0, SuperstructureConstants.kAntiFoulThingkP, 0);
		m_deployerThing.config_kI(0, 0, 0);
		m_deployerThing.config_kD(0, 0, 0);
		m_deployerThing.config_kF(0, 0, 0);
		
		m_deployerThing.setSensorPhase(false);
		m_deployerThing.setInverted(false);
		
		m_deployerThing.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
		m_deployerThing.setNeutralMode(NeutralMode.Brake);
	}

    public void initDefaultCommand() {
    }
    
    public void setPosition(double position) {
    	m_deployerThing.set(ControlMode.Position, position);
    }
    
    public void setPower(double percent) {
    	m_deployerThing.set(ControlMode.PercentOutput, percent);
    }
    
    public double getPosition() {
    	return m_deployerThing.getSelectedSensorPosition(0);
    }
    
    public void resetEncoder() {
    	m_deployerThing.setSelectedSensorPosition(0, 0, 0);
    }
    
    public void reportToSmartDashboard() {
    	SmartDashboard.putNumber("Deployer thing position", getPosition());
    }
}

