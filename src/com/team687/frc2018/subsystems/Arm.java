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
import com.team687.frc2018.RobotMap;
import com.team687.frc2018.constants.SuperstructureConstants;
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

	private String m_filePath1 = "/media/sda1/logs/";
	private String m_filePath2 = "/home/lvuser/logs/";
	private File m_file;
	private FileWriter m_writer;
	private boolean writeException = false;

	// Status Frame Docs in Section 20 of Software Reference Manual
	public static final int loggingFrameRate = 20;

	private double m_logStartTime;

	public Arm() {
		m_arm = new TalonSRX(RobotMap.kArmID);

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
		return (ticks / 4096) * (360 / 8.18) - 54;
	}

	public double degreesToTicks(double degrees) {
		return (degrees + 54) * 8.18 / 360 * 4096;
	}

	public double getAngleAbsolute() {
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

	public double getAbsoluteAngle() {
		return ticksToDegrees(getPosition());
	}

	public double _x1 = SuperstructureConstants.kShoulderPivotX;
	public double _y1 = SuperstructureConstants.kShoulderPivotY;
	public double _r2 = SuperstructureConstants.kShoulderToWristPivot;

	public double getX() {
		return _x1 + _r2 * Math.cos(NerdyMath.degreesToRadians(getAbsoluteAngle()));
	}

	public double getY() {
		return _y1 + _r2 * Math.sin(NerdyMath.degreesToRadians(getAbsoluteAngle()));
	}

	public void reportToSmartDashboard() {
		SmartDashboard.putNumber("Arm Position", getPosition());
		SmartDashboard.putNumber("Arm Angle", getAngleAbsolute());
		SmartDashboard.putNumber("Arm Velocity", getVelocity());
		SmartDashboard.putNumber("Arm Voltage", getVoltage());
		SmartDashboard.putNumber("Arm Current", getCurrent());
		SmartDashboard.putNumber("Arm Desired Position", m_desiredPos);
	}

	// private CSVDatum m_armPositionData, m_armDesiredPosData,
	// m_armVelocityData, m_armAngleData, m_armVoltageData,
	// m_armCurrentData;
	//
	// public void addLoggedData() {
	// m_armPositionData = new CSVDatum("arm_position");
	// m_armDesiredPosData = new CSVDatum("arm_desiredPos");
	// m_armVelocityData = new CSVDatum("arm_velocity");
	// m_armAngleData = new CSVDatum("arm_angle");
	// m_armVoltageData = new CSVDatum("arm_voltage");
	// m_armCurrentData = new CSVDatum("arm_current");
	//
	// Robot.logger.addCSVDatum(m_armPositionData);
	// Robot.logger.addCSVDatum(m_armDesiredPosData);
	// Robot.logger.addCSVDatum(m_armVelocityData);
	// Robot.logger.addCSVDatum(m_armAngleData);
	// Robot.logger.addCSVDatum(m_armVoltageData);
	// Robot.logger.addCSVDatum(m_armCurrentData);
	// }
	//
	// public void updateLog() {
	// m_armPositionData.updateValue(getPosition());
	// m_armDesiredPosData.updateValue(m_desiredPos);
	// m_armVelocityData.updateValue(getVelocity());
	// m_armAngleData.updateValue(getAngleAbsolute());
	// m_armVoltageData.updateValue(getVoltage());
	// m_armCurrentData.updateValue(getCurrent());
	// }

	public void startLog() {
		// Check to see if flash drive is mounted.
		File logFolder1 = new File(m_filePath1);
		File logFolder2 = new File(m_filePath2);
		Path filePrefix = Paths.get("");
		if (logFolder1.exists() && logFolder1.isDirectory())
			filePrefix = Paths.get(logFolder1.toString(), SmartDashboard.getString("log_file_name", "2018_02_19_Arm"));
		else if (logFolder2.exists() && logFolder2.isDirectory())
			filePrefix = Paths.get(logFolder2.toString(), SmartDashboard.getString("log_file_name", "2018_02_19_Arm"));
		else
			writeException = true;

		if (!writeException) {
			int counter = 0;
			while (counter <= 99) {
				m_file = new File(filePrefix.toString() + String.format("%02d", counter) + ".csv");
				if (m_file.exists()) {
					counter++;
				} else {
					break;
				}
				if (counter == 99) {
					System.out.println("file creation counter at 99!");
				}
			}
			try {
				m_writer = new FileWriter(m_file);
				m_writer.append("Time,Position,Velocity,Voltage,Current,DesiredPos,AbsAngle\n");
				m_logStartTime = Timer.getFPGATimestamp();
			} catch (IOException e) {
				e.printStackTrace();
				writeException = true;
			}
		}
	}

	public void stopLog() {
		try {
			if (null != m_writer)
				m_writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			writeException = true;
		}
	}

	public void logToCSV() {
		if (!writeException) {
			try {
				double timestamp = Timer.getFPGATimestamp() - m_logStartTime;
				m_writer.append(String.valueOf(timestamp) + "," + String.valueOf(getPosition()) + ","
						+ String.valueOf(getVelocity()) + "," + String.valueOf(getVoltage()) + ","
						+ String.valueOf(getCurrent()) + "," + String.valueOf(m_desiredPos) + ","
						+ String.valueOf(getAngleAbsolute()) + "\n");
				m_writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
				writeException = true;
			}
		}
	}

}