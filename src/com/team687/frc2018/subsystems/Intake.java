package com.team687.frc2018.subsystems;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team687.frc2018.RobotMap;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Intake subsystem
 */

public class Intake extends Subsystem {

	private final TalonSRX m_rollers;
	private final DigitalInput m_switch;

	private String m_filePath1 = "/media/sda1/logs/";
	private String m_filePath2 = "/home/lvuser/logs/";
	private File m_file;
	private FileWriter m_writer;
	private boolean writeException = false;

	// Status Frame Docs in Section 20 of Software Reference Manual
	public static final int loggingFrameRate = 20;

	private double m_logStartTime;

	public Intake() {
		m_rollers = new TalonSRX(RobotMap.kIntakeRollersID);
		m_rollers.setNeutralMode(NeutralMode.Coast);
		m_rollers.setStatusFramePeriod(StatusFrame.Status_1_General, 20, 0);

		m_rollers.configPeakOutputForward(1, 0);
		m_rollers.configPeakOutputReverse(-1, 0);
		m_rollers.enableCurrentLimit(false);

		m_switch = new DigitalInput(RobotMap.kLimitSwitchID);
	}

	@Override
	protected void initDefaultCommand() {
	}

	public boolean hasCube() {
		return isMaxCurrent();
	}

	public boolean isMaxCurrent() {
		return getCurrent() > SuperstructureConstants.kRollerMaxCurrent;
	}

	public void setRollerPower(double power) {
		m_rollers.set(ControlMode.PercentOutput, power);
	}

	public double getVoltage() {
		return m_rollers.getMotorOutputVoltage();
	}

	public double getCurrent() {
		return m_rollers.getOutputCurrent();
	}

	public void reportToSmartDashboard() {
		SmartDashboard.putNumber("Roller Voltage", getVoltage());
		SmartDashboard.putNumber("Roller Current", getCurrent());
		SmartDashboard.putBoolean("Has Cube", hasCube());
		SmartDashboard.putBoolean("Reached Max Current", isMaxCurrent());
	}

	public void startLog() {
		// Check to see if flash drive is mounted.
		File logFolder1 = new File(m_filePath1);
		File logFolder2 = new File(m_filePath2);
		Path filePrefix = Paths.get("");
		if (logFolder1.exists() && logFolder1.isDirectory())
			filePrefix = Paths.get(logFolder1.toString(),
					SmartDashboard.getString("log_file_name", "2018_02_19_Intake"));
		else if (logFolder2.exists() && logFolder2.isDirectory())
			filePrefix = Paths.get(logFolder2.toString(),
					SmartDashboard.getString("log_file_name", "2018_02_19_Intake"));
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
				m_writer.append("Time,Voltage,Current\n");
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
				m_writer.append(String.valueOf(timestamp) + "," + String.valueOf(getVoltage()) + ","
						+ String.valueOf(getCurrent()) + "\n");
				m_writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
				writeException = true;
			}
		}
	}

}
