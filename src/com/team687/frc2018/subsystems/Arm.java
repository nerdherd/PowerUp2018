package com.team687.frc2018.subsystems;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU.CalibrationMode;
import com.team687.frc2018.Robot;
import com.team687.frc2018.RobotMap;
import com.team687.frc2018.constants.SuperstructureConstants;
import com.team687.frc2018.utilities.CSVDatum;
import com.team687.frc2018.utilities.NerdyMath;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Arm subsystem
 */

public class Arm extends Subsystem {

    private final TalonSRX m_arm;
    private double m_desiredPos = 0;

    private final PigeonIMU m_towerPigeon, m_armPigeon;
    private double[] m_towerYpr = new double[3];
    private double m_towerResetOffset = 0;
    private double[] m_armYpr = new double[3];
    private double m_armResetOffset = 0;

    public Arm() {
	m_arm = new TalonSRX(RobotMap.kArmID);

	m_towerPigeon = new PigeonIMU(RobotMap.kTowerPigeonID);
	m_armPigeon = new PigeonIMU(RobotMap.kArmPigeonID);

	m_arm.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
	m_arm.setSensorPhase(true);
	m_arm.setInverted(false);
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
	m_arm.set(ControlMode.MotionMagic, m_desiredPos);
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
	return (ticks / 4096) * (360 / 12) - 55;
    }

    public double degreesToTicks(double degrees) {
	return (degrees + 55) * 12 / 360 * 4096;
    }

    /**
     * @return calculated angle from encoders
     */
    public double getAngleAbsolute() {
	return ticksToDegrees(getPosition());
    }

    public void resetEncoder() {
	m_arm.setSelectedSensorPosition(0, 0, 0);
    }

    public void updateYawPitchRoll() {
	m_towerPigeon.getYawPitchRoll(m_towerYpr);
	m_armPigeon.getYawPitchRoll(m_armYpr);
    }

    public double getTowerPigeonAngle() {
	return ((360 - m_towerYpr[0]) % 360) - m_towerResetOffset + 90; // converted to correct frame
    }

    public void resetTowerAngle() {
	m_towerResetOffset += getTowerPigeonAngle();
    }

    public double getArmPigeonAngle() {
	return ((360 - m_armYpr[0]) % 360) - m_armResetOffset - 55; // TODO: convert to correct frame
    }

    public void resetArmAngle() {
	m_armResetOffset += getArmPigeonAngle();
    }

    public void enterCalibrationMode() {
	m_towerPigeon.enterCalibrationMode(CalibrationMode.Temperature, 0);
	m_armPigeon.enterCalibrationMode(CalibrationMode.Temperature, 0);
    }

    // aliasing
    public double _x1 = SuperstructureConstants.kShoulderPivotX;
    public double _y1 = SuperstructureConstants.kShoulderPivotY;
    public double _r2 = SuperstructureConstants.kShoulderToWristPivot;

    public double getX() {
	return _x1 + _r2 * Math.cos(NerdyMath.degreesToRadians(getArmPigeonAngle()));
    }

    public double getY() {
	return _y1 + _r2 * Math.sin(NerdyMath.degreesToRadians(getArmPigeonAngle()));
    }

    public double getVoltage() {
	return m_arm.getMotorOutputVoltage();
    }

    public double getCurrent() {
	return m_arm.getOutputCurrent();
    }

    public void reportToSmartDashboard() {
	SmartDashboard.putNumber("Arm Position", getPosition());
	SmartDashboard.putNumber("Arm Angle from Encoder", getAngleAbsolute());
	SmartDashboard.putNumber("Arm Angle from Pigeon", getArmPigeonAngle());
	SmartDashboard.putNumber("Tower Angle from Pigeon", getTowerPigeonAngle());
	SmartDashboard.putNumber("Arm Velocity", getVelocity());
	SmartDashboard.putNumber("Arm Voltage", getVoltage());
	SmartDashboard.putNumber("Arm Current", getCurrent());
	SmartDashboard.putNumber("Arm Desired Position", m_desiredPos);
    }

    private CSVDatum m_armPositionData, m_armDesiredPosData, m_armVelocityData, m_armEncoderAngleData, m_armPigeonAngle,
	    m_towerPigeonAngle, m_armVoltageData, m_armCurrentData;

    public void addLoggedData() {
	m_armPositionData = new CSVDatum("arm_position");
	m_armDesiredPosData = new CSVDatum("arm_desiredPos");
	m_armVelocityData = new CSVDatum("arm_velocity");
	m_armEncoderAngleData = new CSVDatum("arm_encoderAngle");
	m_armPigeonAngle = new CSVDatum("arm_pigeonAngle");
	m_towerPigeonAngle = new CSVDatum("tower_pigeonAngle");
	m_armVoltageData = new CSVDatum("arm_voltage");
	m_armCurrentData = new CSVDatum("arm_current");

	Robot.logger.addCSVDatum(m_armPositionData);
	Robot.logger.addCSVDatum(m_armDesiredPosData);
	Robot.logger.addCSVDatum(m_armVelocityData);
	Robot.logger.addCSVDatum(m_armEncoderAngleData);
	Robot.logger.addCSVDatum(m_armPigeonAngle);
	Robot.logger.addCSVDatum(m_towerPigeonAngle);
	Robot.logger.addCSVDatum(m_armVoltageData);
	Robot.logger.addCSVDatum(m_armCurrentData);
    }

    public void updateLog() {
	m_armPositionData.updateValue(getPosition());
	m_armDesiredPosData.updateValue(m_desiredPos);
	m_armVelocityData.updateValue(getVelocity());
	m_armEncoderAngleData.updateValue(getAngleAbsolute());
	m_armPigeonAngle.updateValue(getArmPigeonAngle());
	m_towerPigeonAngle.updateValue(getTowerPigeonAngle());
	m_armVoltageData.updateValue(getVoltage());
	m_armCurrentData.updateValue(getCurrent());
    }
  
}
