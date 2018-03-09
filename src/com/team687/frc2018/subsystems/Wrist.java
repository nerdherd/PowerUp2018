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
import com.team687.frc2018.utilities.NerdyMath;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Wrist subsystem
 */

public class Wrist extends Subsystem {

    private final TalonSRX m_wrist;
    private double m_desiredPos = 0;

    private final PigeonIMU m_pigeon;
    private double[] m_ypr = new double[3];
    private double m_resetOffset = 0;

    public Wrist() {
	m_wrist = new TalonSRX(RobotMap.kWristID);

	m_pigeon = new PigeonIMU(RobotMap.kWristPigeonID);

	m_wrist.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
	m_wrist.setSensorPhase(true);
	m_wrist.setInverted(true);
	m_wrist.setNeutralMode(NeutralMode.Coast);

	m_wrist.config_kF(0, SuperstructureConstants.kWristF, 0);
	m_wrist.config_kP(0, SuperstructureConstants.kWristP, 0);
	m_wrist.config_kI(0, SuperstructureConstants.kWristI, 0);
	m_wrist.config_kD(0, SuperstructureConstants.kWristD, 0);

	m_wrist.configMotionCruiseVelocity(SuperstructureConstants.kWristCruiseVelocity, 0);
	m_wrist.configMotionAcceleration(SuperstructureConstants.kWristAcceleration, 0);

	m_wrist.configPeakOutputForward(SuperstructureConstants.kWristMaxVoltageForward / 12, 0);
	m_wrist.configPeakOutputReverse(SuperstructureConstants.kWristMaxVoltageReverse / 12, 0);
	m_wrist.configNominalOutputForward(0, 0);
	m_wrist.configNominalOutputReverse(0, 0);
	m_wrist.configClosedloopRamp(SuperstructureConstants.kWristRampRate, 0);
	m_wrist.configVoltageCompSaturation(12, 0);
	m_wrist.enableVoltageCompensation(false);

	m_wrist.configPeakCurrentLimit(0, 0);
	m_wrist.configContinuousCurrentLimit(SuperstructureConstants.kWristContinuousCurrent, 0);
	m_wrist.enableCurrentLimit(true);

	m_wrist.configForwardSoftLimitThreshold(SuperstructureConstants.kWristForwardSoftLimit, 0);
	m_wrist.configReverseSoftLimitThreshold(SuperstructureConstants.kWristReverseSoftLimit, 0);
	m_wrist.configForwardSoftLimitEnable(false, 0);
	m_wrist.configReverseSoftLimitEnable(false, 0);

	m_wrist.setStatusFramePeriod(StatusFrame.Status_1_General, 10, 0);
	m_wrist.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20, 0);
	m_wrist.setStatusFramePeriod(StatusFrame.Status_10_MotionMagic, 20, 0);
    }

    @Override
    protected void initDefaultCommand() {
    }

    public void setPosition(double position) {
	// m_desiredPos = position + getEncoderCorrection();
	m_desiredPos = position;
	m_wrist.set(ControlMode.MotionMagic, m_desiredPos);
    }

    public void setPercentOutput(double power) {
	m_wrist.set(ControlMode.PercentOutput, power);
    }

    public void setVoltage(double voltage) {
	m_wrist.set(ControlMode.PercentOutput, voltage / m_wrist.getBusVoltage());
    }

    public double getPosition() {
	return m_wrist.getSelectedSensorPosition(0);
    }

    public double ticksToDegrees(double ticks) {
	return ticks / 4096 * 360 / 2.5;
    }

    public double degreesToTicks(double degrees) {
	return degrees / 360 * 4096 * 2.5;
    }

    public double getAngleRelative() {
	// 650 is the offset that accounts for our zeroing because we don't zero our
	// encoder at exactly 0 degrees)
	// 2560 converts our 0 angle to the positive x-axis
	return ticksToDegrees(getPosition() + 650 + 2560) + 52;
    }

    /**
     * @return calculated angle from encoders
     */
    public double getAngleAbsolute() {
	return getAngleRelative() + Robot.arm.getAngleAbsolute();
    }

    public double angleAbsoluteToRelative(double angleAbsolute) {
	return angleAbsolute - Robot.arm.getAngleAbsolute();
    }

    public double angleRelativeToTicks(double angleRelative) {
	return degreesToTicks(angleRelative - 52) - 650 - 2560;
    }

    public double angleAbsoluteToTicks(double angle) {
	return angleRelativeToTicks(angleAbsoluteToRelative(angle));
    }

    public void setAngleAbsolute(double angle) {
	setPosition(angleAbsoluteToTicks(angle));
    }

    /**
     * @return desired angle when going to/from forwards scale scoring position
     */
    public double getDesiredAbsoluteAngle() {
	double _r3 = SuperstructureConstants.kWristPivotToTip;
	double theta2 = Robot.arm.getArmPigeonAngle();
	double x2 = Robot.arm.getX();
	double y2 = Robot.arm.getY();
	double _theta3_offset = -16;
	if (theta2 <= -33) {
	    return 90;
	} else if (theta2 <= 43) {
	    return NerdyMath.radiansToDegrees(Math.acos((45 - x2) / _r3)) - _theta3_offset; // DEGREES(ACOS((45-[@x2])/_r3))-theta3_offset
	} else if (theta2 <= 46) {
	    return -1.75 * theta2 + 135.3 - _theta3_offset; // -1.75*[@theta2]+135.3-theta3_offset
	} else {
	    return NerdyMath.radiansToDegrees(Math.asin((88 - y2) / _r3)) - _theta3_offset; // DEGREES(ASIN((88-[@y2])/_r3))-theta3_offset
	}
    }

    public double getVelocity() {
	return m_wrist.getSelectedSensorVelocity(0);
    }

    public void resetEncoder() {
	m_wrist.setSelectedSensorPosition(0, 0, 0);
    }

    public void updateYawPitchRoll() {
	m_pigeon.getYawPitchRoll(m_ypr);
    }

    public double getPigeonAngle() {
	return ((360 - m_ypr[0]) % 360) - m_resetOffset + 113; // TODO: convert to correct frame
    }

    public void resetAngle() {
	m_resetOffset += getPigeonAngle();
    }

    public void enterCalibrationMode() {
	m_pigeon.enterCalibrationMode(CalibrationMode.Temperature, 0);
    }

    // public double getEncoderCorrection() {
    // double diff = getPigeonAngle() - getAngleAbsolute();
    // return degreesToTicks(diff);
    // }

    public double getVoltage() {
	return m_wrist.getMotorOutputVoltage();
    }

    public double getCurrent() {
	return m_wrist.getOutputCurrent();
    }

    /**
     * @return if arm can safely move down without crushing wrist
     */
    public boolean isWristSafe() {
	return getPosition() <= SuperstructureConstants.kWristStowPos
		&& getPosition() >= SuperstructureConstants.kWristIntakePos;
    }

    public void reportToSmartDashboard() {
	SmartDashboard.putNumber("Wrist Position", getPosition());
	SmartDashboard.putNumber("Wrist Desired Absolute Angle", getDesiredAbsoluteAngle());
	SmartDashboard.putNumber("Wrist Absolute Angle", getAngleAbsolute());
	SmartDashboard.putNumber("Wrist Pigeon Angle", getPigeonAngle());
	SmartDashboard.putNumber("Wrist Velocity", getVelocity());
	SmartDashboard.putNumber("Wrist Voltage", getVoltage());
	SmartDashboard.putNumber("Wrist Current", getCurrent());
    }

    private CSVDatum m_wristPositionData, m_wristDesiredPosData, m_wristVelocityData, m_wristEncoderAngleData,
	    m_wristPigeonAngleData, m_wristVoltageData, m_wristCurrentData;

    public void addLoggedData() {
	m_wristPositionData = new CSVDatum("wrist_position");
	m_wristDesiredPosData = new CSVDatum("wrist_desiredPos");
	m_wristVelocityData = new CSVDatum("wrist_velocity");
	m_wristEncoderAngleData = new CSVDatum("wrist_encoderAngle");
	m_wristPigeonAngleData = new CSVDatum("wrist_pigeonAngle");
	m_wristVoltageData = new CSVDatum("wrist_voltage");
	m_wristCurrentData = new CSVDatum("wrist_current");

	Robot.logger.addCSVDatum(m_wristPositionData);
	Robot.logger.addCSVDatum(m_wristDesiredPosData);
	Robot.logger.addCSVDatum(m_wristVelocityData);
	Robot.logger.addCSVDatum(m_wristEncoderAngleData);
	Robot.logger.addCSVDatum(m_wristVoltageData);
	Robot.logger.addCSVDatum(m_wristCurrentData);
    }

    public void updateLog() {
	m_wristPositionData.updateValue(getPosition());
	m_wristDesiredPosData.updateValue(m_desiredPos);
	m_wristVelocityData.updateValue(getVelocity());
	m_wristEncoderAngleData.updateValue(getAngleAbsolute());
	m_wristPigeonAngleData.updateValue(getPigeonAngle());
	m_wristVoltageData.updateValue(getVoltage());
	m_wristCurrentData.updateValue(getCurrent());
    }

}