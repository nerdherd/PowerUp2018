package com.team687.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team687.frc2018.Robot;
import com.team687.frc2018.RobotMap;
import com.team687.frc2018.constants.SuperstructureConstants;
import com.team687.frc2018.utilities.CSVDatum;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Arm subsystem
 */

public class Arm extends Subsystem {

    private final TalonSRX m_arm;

    private double m_desiredPos = 0;

    public Arm() {
	m_arm = new TalonSRX(RobotMap.kArmID);

	m_arm.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
	m_arm.setSensorPhase(true);
	m_arm.setNeutralMode(NeutralMode.Coast);

	m_arm.config_kF(0, SuperstructureConstants.kArmF, 0);
	m_arm.config_kP(0, SuperstructureConstants.kArmP, 0);
	m_arm.config_kI(0, SuperstructureConstants.kArmI, 0);
	m_arm.config_kD(0, SuperstructureConstants.kArmD, 0);

	m_arm.configMotionAcceleration(SuperstructureConstants.kArmAcceleration, 0);
	m_arm.configMotionCruiseVelocity(SuperstructureConstants.kArmCruiseVelocity, 0);

	m_arm.configPeakOutputForward(SuperstructureConstants.kArmMaxVoltageForward / 12, 0);
	m_arm.configPeakOutputReverse(SuperstructureConstants.kArmMaxVoltageReverse / 12, 0);
	m_arm.configNominalOutputForward(0, 0);
	m_arm.configNominalOutputReverse(0, 0);
	m_arm.configClosedloopRamp(SuperstructureConstants.kArmRampRate, 0);
	m_arm.configVoltageCompSaturation(12, 0);
	m_arm.enableVoltageCompensation(false);

	m_arm.configPeakCurrentLimit(SuperstructureConstants.kArmPeakCurrent, 0);
	m_arm.configContinuousCurrentLimit(SuperstructureConstants.kArmContinuousCurrent, 0);
	m_arm.enableCurrentLimit(false);

	m_arm.configForwardSoftLimitThreshold(SuperstructureConstants.kArmForwardSoftLimit, 0);
	m_arm.configReverseSoftLimitThreshold(SuperstructureConstants.kArmReverseSoftLimit, 0);
	m_arm.configForwardSoftLimitEnable(false, 0);
	m_arm.configReverseSoftLimitEnable(false, 0);

	m_arm.setStatusFramePeriod(StatusFrame.Status_1_General, 20, 0);
	m_arm.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20, 0);
	m_arm.setStatusFramePeriod(StatusFrame.Status_10_MotionMagic, 20, 0);
    }

    @Override
    protected void initDefaultCommand() {
    }

    public void setPosition(double position) {
	m_desiredPos = position;
	m_arm.set(ControlMode.MotionMagic, position);
    }

    public void setVoltage(double voltage) {
	m_arm.set(ControlMode.PercentOutput, voltage / m_arm.getBusVoltage());
    }

    public double getPosition() {
	return m_arm.getSelectedSensorPosition(0);
    }

    public double getVelocity() {
	return m_arm.getSelectedSensorVelocity(0);
    }

    public double ticksToDegrees(double ticks) {
	return (ticks / 4096) * (360 / 12) - 52;
    }

    public double degreesToTicks(double degrees) {
	return (degrees + 52) * 12 / 360 * 4096;
    }

    public double getAbsoluteAngle() {
	return ticksToDegrees(getPosition());
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
	SmartDashboard.putNumber("Arm Velocity", getVelocity());
	SmartDashboard.putNumber("Arm Voltage", getVoltage());
	SmartDashboard.putNumber("Arm Current", getCurrent());
	SmartDashboard.putNumber("Arm Desired Position", m_desiredPos);
    }

    private CSVDatum m_positionData, m_armDesiredPosData, m_velocityData, m_voltageData, m_currentData;

    public void addLoggedData() {
	m_positionData = new CSVDatum("arm_position");
	m_armDesiredPosData = new CSVDatum("arm_desiredPos");
	m_velocityData = new CSVDatum("arm_velocity");
	m_voltageData = new CSVDatum("arm_voltage");
	m_currentData = new CSVDatum("arm_current");

	Robot.logger.addCSVDatum(m_positionData);
	Robot.logger.addCSVDatum(m_armDesiredPosData);
	Robot.logger.addCSVDatum(m_velocityData);
	Robot.logger.addCSVDatum(m_voltageData);
	Robot.logger.addCSVDatum(m_currentData);
    }

    public void updateLog() {
	m_positionData.updateValue(getPosition());
	m_armDesiredPosData.updateValue(m_desiredPos);
	m_velocityData.updateValue(getVelocity());
	m_voltageData.updateValue(getVoltage());
	m_currentData.updateValue(getCurrent());
    }

}