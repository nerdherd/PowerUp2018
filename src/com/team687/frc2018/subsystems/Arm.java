package com.team687.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
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

	m_arm.configPeakOutputForward(SuperstructureConstants.kArmMaxVoltageForward / 12, 0);
	m_arm.configPeakOutputReverse(SuperstructureConstants.kArmMaxVoltageReverse / 12, 0);
	m_arm.configClosedloopRamp(SuperstructureConstants.kArmRampRate, 0);

	m_arm.configPeakCurrentLimit(SuperstructureConstants.kArmPeakCurrent, 0);
	m_arm.configContinuousCurrentLimit(SuperstructureConstants.kArmContinuousCurrent, 0);
	m_arm.enableCurrentLimit(true);

	m_arm.configForwardSoftLimitThreshold(SuperstructureConstants.kArmForwardSoftLimit, 0);
	m_arm.configReverseSoftLimitThreshold(SuperstructureConstants.kArmReverseSoftLimit, 0);
	m_arm.configForwardSoftLimitEnable(true, 0);
	m_arm.configReverseSoftLimitEnable(true, 0);

	m_arm.setStatusFramePeriod(StatusFrame.Status_1_General, 10, 0);
	m_arm.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20, 0);
    }

    @Override
    protected void initDefaultCommand() {
	// setDefaultCommand(new MySpecialCommand());
    }

    public void setPosition(double position) {
	if (!Robot.wrist.isWristSafe()) {
	    position = Math.max(position, SuperstructureConstants.kArmWristSafePos);
	}
	m_arm.set(ControlMode.Position, position);
    }

    public void setPercentOutput(double power) {
	m_arm.set(ControlMode.PercentOutput, power);
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