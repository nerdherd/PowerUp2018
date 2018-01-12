package com.team687.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team687.frc2018.Robot;
import com.team687.frc2018.RobotMap;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Arm subsystem
 */

public class Arm extends Subsystem {

    private final TalonSRX m_arm;

    public Arm() {
	m_arm = new TalonSRX(RobotMap.kArmID);

	m_arm.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
	m_arm.config_kP(0, SuperstructureConstants.kArmP, 0);
	m_arm.config_kI(0, SuperstructureConstants.kArmI, 0);
	m_arm.config_kD(0, SuperstructureConstants.kArmD, 0);
	m_arm.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    protected void initDefaultCommand() {
	// setDefaultCommand(new MySpecialCommand());
    }

    public void setPosition(double position) {
	position = Math.min(SuperstructureConstants.kArmScaleTopPos,
		Math.max(position, SuperstructureConstants.kArmDownPos));
	if (!Robot.wrist.isWristSafe()) {
	    position = Math.min(position, SuperstructureConstants.kArmWristSafePos);
	}
	m_arm.set(ControlMode.Position, position);
    }

    public void setZeroVoltage() {
	m_arm.set(ControlMode.PercentOutput, 0);
    }

    // // real world units
    // public double getPosition() {
    // return m_arm.getSelectedSensorPosition(0) / 4096;
    // }
    //
    // public double getSpeed() {
    // return m_arm.getSelectedSensorVelocity(0) * (600 / 4096);
    // }

    public double getPosition() {
	return m_arm.getSelectedSensorPosition(0);
    }

    public double getSpeed() {
	return m_arm.getSelectedSensorVelocity(0);
    }

    public void resetEncoder() {
	m_arm.setSelectedSensorPosition(0, 0, 0);
    }

    public double getVoltage() {
	return m_arm.getMotorOutputVoltage();
    }

    public double getCurrent() {
	return m_arm.getOutputCurrent();
    }

    public void reportToSmartDashboard() {
	SmartDashboard.putNumber("Arm Position", getPosition());
	SmartDashboard.putNumber("Arm Speed", getSpeed());
	SmartDashboard.putNumber("Arm Voltage", getVoltage());
	SmartDashboard.putNumber("Arm Current", getCurrent());
    }

}