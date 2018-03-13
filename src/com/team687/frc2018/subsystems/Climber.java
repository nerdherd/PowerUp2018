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

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Climber extends Subsystem {

	public final TalonSRX m_climber1;
	public final TalonSRX m_climber2;

	private String m_filePath1 = "/media/sda1/logs/";
	private String m_filePath2 = "/home/lvuser/logs/";
	private File m_file;
	private FileWriter m_writer;
	private boolean writeException = false;
	private double m_logStartTime;

	public Climber() {
		m_climber1 = new TalonSRX(RobotMap.kClimber1ID);
		m_climber2 = new TalonSRX(RobotMap.kClimber2ID);

		m_climber1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
		m_climber2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);

		m_climber1.config_kP(0, SuperstructureConstants.kClimber1P, 0);
		m_climber2.config_kP(0, SuperstructureConstants.kClimber2P, 0);

		m_climber1.setNeutralMode(NeutralMode.Brake);
		m_climber2.setNeutralMode(NeutralMode.Brake);

		m_climber1.setStatusFramePeriod(StatusFrame.Status_1_General, 20, 0);
		m_climber1.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20, 0);

		m_climber2.setStatusFramePeriod(StatusFrame.Status_1_General, 20, 0);
		m_climber2.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20, 0);

	}

	public void setPosition(double climber1Pos, double climber2Pos) {
		m_climber1.set(ControlMode.Position, climber1Pos);
		m_climber2.set(ControlMode.Position, climber2Pos);

	}

	public void resetEncoders() {
		m_climber1.setSelectedSensorPosition(0, 0, 0);
		m_climber2.setSelectedSensorPosition(0, 0, 0);
	}
	
	
	public double getClimber1Voltage() {
		return m_climber1.getMotorOutputVoltage();
	}

	public double getClimber2Voltage() {
		return m_climber2.getMotorOutputVoltage();
	}

	public double getClimber1Current() {
		return m_climber1.getOutputCurrent();
	}

	public double getClimber2Current() {
		return m_climber2.getOutputCurrent();
	}

	public double getClimber1Position() {
		return m_climber1.getSelectedSensorPosition(0);
	}

	public double getClimber2Position() {
		return m_climber2.getSelectedSensorPosition(0);
	}

	public double getClimber1Speed() {
		return m_climber1.getSelectedSensorVelocity(0);
	}
	
	public double getClimber2Sped() {
		return m_climber2.getSelectedSensorVelocity(0);
	}

	public void reportToSmartDashboard() {
		SmartDashboard.putNumber("Climber 1 Position", getClimber1Position());
		SmartDashboard.putNumber("Climber 2 Position", getClimber2Position());
	}
	
	public void startLog() {
		// Check to see if flash drive is mounted.
		File logFolder1 = new File(m_filePath1);
		File logFolder2 = new File(m_filePath2);
		Path filePrefix = Paths.get("");
		if(logFolder1.exists() && logFolder1.isDirectory())
			filePrefix = Paths.get(logFolder1.toString(), SmartDashboard.getString("log_file_name", "2018_03_12_Climber"));
		else if(logFolder2.exists() && logFolder2.isDirectory())
			filePrefix = Paths.get(logFolder2.toString(), SmartDashboard.getString("log_file_name", "2018_03_12_Climber"));
		else writeException = true;

		if(!writeException) {
			int counter = 0;
			while(counter <= 99) {
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
				m_writer.append("\n");
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
				m_writer.append(String.valueOf(timestamp) + "," + "\n");
				m_writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
				writeException = true;
			}
		}
	}
	
	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
