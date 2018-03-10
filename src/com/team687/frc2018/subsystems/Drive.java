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
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.team687.frc2018.Robot;
import com.team687.frc2018.RobotMap;
import com.team687.frc2018.commands.drive.teleop.ArcadeDrive;
import com.team687.frc2018.commands.drive.teleop.SetDriveVoltage;
import com.team687.frc2018.constants.DriveConstants;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive subsystem
 */

public class Drive extends Subsystem {

	private final TalonSRX m_leftMaster, m_leftSlave1;
	private final TalonSRX m_rightMaster, m_rightSlave1;

	private final AHRS m_nav;

	private double m_initTime;
	private double m_currentTime;

	private boolean m_brakeModeOn;

	public static final int kCruiseVelocity = 2500;
	public static final int kAcceleration = 2500;

	public static final int kTestDistance = 67000;
	
	private double m_rightDesiredVel, m_leftDesiredVel;

	private String m_filePath1 = "/media/sda1/logs/";
	private String m_filePath2 = "/home/lvuser/logs/";
	private File m_file;
	private FileWriter m_writer;
	private boolean writeException = false;

	// Status Frame Docs in Section 20 of Software Reference Manual
	public static final int loggingFrameRate = 20;

	private double m_logStartTime;

	public Drive() {
		m_leftMaster = new TalonSRX(RobotMap.kLeftMasterTalonID);
		m_leftSlave1 = new TalonSRX(RobotMap.kLeftSlaveTalon1ID);
		m_rightMaster = new TalonSRX(RobotMap.kRightMasterTalonID);
		m_rightSlave1 = new TalonSRX(RobotMap.kRightSlaveTalon1ID);

		m_leftSlave1.follow(m_leftMaster);
		m_rightSlave1.follow(m_rightMaster);

		m_leftMaster.setStatusFramePeriod(StatusFrame.Status_1_General, 20, 0);
		m_leftMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20, 0);
		m_leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		m_leftMaster.setSensorPhase(true);
		m_leftMaster.setInverted(false);
		m_leftSlave1.setInverted(false);
		m_leftMaster.setNeutralMode(NeutralMode.Brake);
		m_leftSlave1.setNeutralMode(NeutralMode.Brake);

		m_leftMaster.configForwardSoftLimitEnable(false, 0);
		m_leftMaster.configForwardSoftLimitEnable(false, 0);

		m_rightMaster.setStatusFramePeriod(StatusFrame.Status_1_General, 20, 0);
		m_rightMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 20, 0);
		m_rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		m_rightMaster.setSensorPhase(true);
		m_rightMaster.setInverted(true);
		m_rightSlave1.setInverted(true);
		m_rightMaster.setNeutralMode(NeutralMode.Brake);
		m_rightSlave1.setNeutralMode(NeutralMode.Brake);

		m_leftMaster.config_kF(0, DriveConstants.kLeftVelocityF, 0);
		m_leftMaster.config_kP(0, DriveConstants.kLeftVelocityP, 0);
		m_leftMaster.config_kI(0, DriveConstants.kLeftVelocityI, 0);
		m_leftMaster.config_kD(0, DriveConstants.kLeftVelocityD, 0);

		m_rightMaster.config_kF(0, DriveConstants.kRightVelocityF, 0);
		m_rightMaster.config_kP(0, DriveConstants.kRightVelocityP, 0);
		m_rightMaster.config_kI(0, DriveConstants.kRightVelocityI, 0);
		m_rightMaster.config_kD(0, DriveConstants.kRightVelocityD, 0);

		m_leftMaster.configPeakCurrentLimit(0, 0);
		m_leftMaster.configPeakCurrentDuration(0, 0);
		m_leftMaster.configContinuousCurrentLimit(40, 0);
		m_leftMaster.enableCurrentLimit(true);
		m_leftSlave1.enableCurrentLimit(false);

		m_rightMaster.configPeakCurrentLimit(0, 0);
		m_rightMaster.configContinuousCurrentLimit(40, 0);
		m_rightMaster.configPeakCurrentDuration(0, 0);
		m_rightMaster.enableCurrentLimit(true);
		m_rightSlave1.enableCurrentLimit(false);

		m_leftMaster.configOpenloopRamp(0.5, 0);
		m_rightMaster.configOpenloopRamp(0.5, 0);
		m_leftMaster.configClosedloopRamp(0.5, 0);
		m_rightMaster.configClosedloopRamp(0.5, 0);

		m_brakeModeOn = true;

		m_nav = new AHRS(SPI.Port.kMXP);
		
		m_leftDesiredVel = 0;
		m_rightDesiredVel = 0;
		
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ArcadeDrive());
	}

	/**
	 * Set drivetrain motor power to value between -1.0 and +1.0
	 * 
	 * @param leftPercent
	 * @param rightPercent
	 */
	public void setPower(double leftPercent, double rightPercent) {
		m_leftDesiredVel = 0;
		m_rightDesiredVel = 0;
		// setPercentVelocity(leftPercent, rightPercent);
		m_leftMaster.set(ControlMode.PercentOutput, leftPercent);
		m_rightMaster.set(ControlMode.PercentOutput, rightPercent);
	}	
	

	public void setVoltage(double leftVoltage, double rightVoltage) {
		double leftBusVoltage = m_leftMaster.getBusVoltage();
		double rightBusVoltage = m_rightMaster.getBusVoltage();
		m_leftMaster.set(ControlMode.PercentOutput, leftVoltage / leftBusVoltage);
		m_rightMaster.set(ControlMode.PercentOutput, rightVoltage / rightBusVoltage);
	}

	public void setPercentOutput(double leftPercent, double rightPercent) {
		double leftBus = m_leftMaster.getBusVoltage();
		double rightBus = m_rightMaster.getBusVoltage();
		m_leftMaster.set(ControlMode.PercentOutput, leftPercent * 12 / leftBus);
		m_rightMaster.set(ControlMode.PercentOutput, rightPercent * 12 / rightBus);
		// m_leftMaster.set(ControlMode.PercentOutput, leftPercent);
		// m_rightMaster.set(ControlMode.PercentOutput, rightPercent);
	}
	
	public void setLeftPosition(double position) {
		m_leftMaster.set(ControlMode.Position, position);
	}
	
	public void setRightPosition(double position) {
		m_rightMaster.set(ControlMode.Position, position);
	}

	public void setPercentVelocity(double leftPercent, double rightPercent) {
		m_rightDesiredVel = rightPercent * DriveConstants.kMaxVelocity;
		m_leftDesiredVel = leftPercent * DriveConstants.kMaxVelocity;
		m_leftMaster.set(ControlMode.Velocity, m_leftDesiredVel);
		m_rightMaster.set(ControlMode.Velocity, m_rightDesiredVel);
	}

	public void setVelocity(double leftVelocity, double rightVelocity) {
		m_leftMaster.set(ControlMode.Velocity, leftVelocity);
		m_rightMaster.set(ControlMode.Velocity, rightVelocity);
	}

	public void setBrakeMode(boolean enabled) {
		if (enabled) {
			m_leftMaster.setNeutralMode(NeutralMode.Brake);
			m_leftSlave1.setNeutralMode(NeutralMode.Brake);
			m_rightMaster.setNeutralMode(NeutralMode.Brake);
			m_rightSlave1.setNeutralMode(NeutralMode.Brake);
		} else {
			m_leftMaster.setNeutralMode(NeutralMode.Coast);
			m_leftSlave1.setNeutralMode(NeutralMode.Coast);
			m_rightMaster.setNeutralMode(NeutralMode.Coast);
			m_rightSlave1.setNeutralMode(NeutralMode.Coast);
		}

		m_brakeModeOn = enabled;
	}

	public boolean getBrakeMode() {
		return m_brakeModeOn;
	}

	public double squareInput(double input) {
		return Math.pow(input, 2) * (input / Math.abs(input));
	}

	/**
	 * Handles when the joystick moves slightly when you actually don't want it to
	 * move at all
	 * 
	 * @param value
	 * @param deadband
	 * @return value or 0 if within deadband
	 */
	public double handleDeadband(double val, double deadband) {
		return (Math.abs(val) > Math.abs(deadband)) ? val : 0.0;
	}

	public double getEncoderYaw() {
		return ((getRightPosition()-getLeftPosition())/2600) * (180/Math.PI);
	}
	
	public double getCurrentYaw() {
		return m_nav.getYaw();
	}

	public double getCurrentPitch() {
		return m_nav.getPitch();
	}

	public double getCurrentRoll() {
		return m_nav.getRoll();
	}

	public void resetGyro() {
		m_nav.reset();
	}

	public double getCurrentAccelX() {
		return m_nav.getWorldLinearAccelX();
	}

	public double getCurrentAccelY() {
		return m_nav.getWorldLinearAccelY();
	}

	public double getCurrentAccelZ() {
		return m_nav.getWorldLinearAccelZ();
	}

	public double getInitTime() {
		return m_initTime;
	}

	public double getCurrentTime() {
		return m_currentTime;
	}

	public double getLeftPosition() {
		return m_leftMaster.getSelectedSensorPosition(0);
	}

	public double getRightPosition() {
		return m_rightMaster.getSelectedSensorPosition(0);
	}

	public double getDrivetrainPosition() {
		return (getLeftPosition() + getRightPosition()) / 2;
	}

	public double getLeftSpeed() {
		return m_leftMaster.getSelectedSensorVelocity(0);
	}

	public double getRightSpeed() {
		return m_rightMaster.getSelectedSensorVelocity(0);
	}

	public void resetEncoders() {
		// m_leftSensorOffset = getLeftPosition();
		// m_rightSensorOffset = getRightPosition();
		//
		// m_leftSensorCumulativeOffset += m_leftSensorOffset;
		// m_rightSensorOffset += m_rightSensorOffset;

		m_leftMaster.setSelectedSensorPosition(0, 0, 0);
		m_rightMaster.setSelectedSensorPosition(0, 0, 0);
	}

	public void stopDrive() {
		setPower(0, 0);
	}

	public void startLog() {
		writeException = false;
		// Check to see if flash drive is mounted.
		File logFolder1 = new File(m_filePath1);
		File logFolder2 = new File(m_filePath2);
		Path filePrefix = Paths.get("");
		if (logFolder1.exists() && logFolder1.isDirectory())
			filePrefix = Paths.get(logFolder1.toString(), "2018_03_03_Drive");
		else if (logFolder2.exists() && logFolder2.isDirectory())
			filePrefix = Paths.get(logFolder2.toString(),
					SmartDashboard.getString("log_file_name", "2018_03_03_Drive"));
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
				m_writer.append("Time,RightPosition,LeftPosition,RightVelocity,LeftVelocity,RightDesiredVel,LeftDesiredVel,RightVoltage,LeftVoltage,"
						+ "RightMasterCurrent,LeftMasterCurrent,RightSlaveCurrent,LeftSlaveCurrent,BusVoltage,Yaw\n");
				m_writer.flush();
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
				m_writer.append(String.valueOf(timestamp) + "," + String.valueOf(getRightPosition()) + ","
						+ String.valueOf(getLeftPosition()) + "," + String.valueOf(getRightSpeed()) + ","
						+ String.valueOf(getLeftSpeed()) + "," + String.valueOf(m_rightDesiredVel) + "," + String.valueOf(m_leftDesiredVel)
						+ "," + String.valueOf(m_rightMaster.getMotorOutputVoltage())
						+ "," + String.valueOf(m_leftMaster.getMotorOutputVoltage()) + ","
						+ String.valueOf(m_rightMaster.getOutputCurrent()) + ","
						+ String.valueOf(m_leftMaster.getOutputCurrent()) + ","
						+ String.valueOf(m_rightSlave1.getOutputCurrent()) + ","
						+ String.valueOf(m_leftSlave1.getOutputCurrent()) + "," + String.valueOf(Robot.pdp.getVoltage()) + ","
						+ String.valueOf(getCurrentYaw()) + "\n");
				m_writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
				writeException = true;
			}
		}
	}

	// private CSVDatum m_leftMasterVoltage, m_leftSlaveVoltage,
	// m_rightMasterVoltage, m_rightSlaveVoltage;
	// private CSVDatum m_leftMasterCurrent, m_leftSlaveCurrent,
	// m_rightMasterCurrent, m_rightSlaveCurrent;
	//
	// public void addLoggedData() {
	// m_leftMasterVoltage = new CSVDatum("drive_leftMasterVoltage");
	// m_leftSlaveVoltage = new CSVDatum("drive_leftSlaveVoltage");
	// m_rightMasterVoltage = new CSVDatum("drive_rightMasterVoltage");
	// m_rightSlaveVoltage = new CSVDatum("drive_rightSlaveVoltage");
	//
	// m_leftMasterCurrent = new CSVDatum("drive_leftMasterCurrent");
	// m_leftSlaveCurrent = new CSVDatum("drive_leftSlaveCurrent");
	// m_rightMasterCurrent = new CSVDatum("drive_rightMasterCurrent");
	// m_rightSlaveCurrent = new CSVDatum("drive_rightSlaveCurrent");
	//
	// Robot.logger.addCSVDatum(m_leftMasterVoltage);
	// Robot.logger.addCSVDatum(m_leftSlaveVoltage);
	// Robot.logger.addCSVDatum(m_rightMasterVoltage);
	// Robot.logger.addCSVDatum(m_rightSlaveVoltage);
	//
	// Robot.logger.addCSVDatum(m_leftMasterCurrent);
	// Robot.logger.addCSVDatum(m_leftSlaveCurrent);
	// Robot.logger.addCSVDatum(m_rightMasterCurrent);
	// Robot.logger.addCSVDatum(m_rightSlaveCurrent);
	// }
	//
	// public void updateLog() {
	// m_leftMasterVoltage.updateValue(getLeftMasterVoltage());
	// m_leftSlaveVoltage.updateValue(getLeftSlaveVoltage());
	// m_rightMasterVoltage.updateValue(getRightMasterVoltage());
	// m_rightSlaveVoltage.updateValue(getRightSlaveVoltage());
	//
	// m_leftMasterCurrent.updateValue(getLeftMasterCurrent());
	// m_leftSlaveCurrent.updateValue(getLeftSlaveCurrent());
	// m_rightMasterCurrent.updateValue(getRightMasterCurrent());
	// m_rightSlaveCurrent.updateValue(getRightSlaveCurrent());
	// }

	public void reportToSmartDashboard() {
		// SmartDashboard.putBoolean("Brake Mode On", m_brakeModeOn);
		//
		// SmartDashboard.putNumber("Left Master Voltage",
		// m_leftMaster.getMotorOutputVoltage());
		// SmartDashboard.putNumber("Left Slave 1 Voltage",
		// m_leftSlave1.getMotorOutputVoltage());
		// SmartDashboard.putNumber("Right Master Voltage",
		// m_rightMaster.getMotorOutputVoltage());
		// SmartDashboard.putNumber("Right Slave 1 Voltage",
		// m_rightSlave1.getMotorOutputVoltage());
		//
		// SmartDashboard.putNumber("Left Master Current",
		// m_leftMaster.getOutputCurrent());
		// SmartDashboard.putNumber("Left Slave 1 Current",
		// m_leftSlave1.getOutputCurrent());
		// SmartDashboard.putNumber("Right Master Current",
		// m_rightMaster.getOutputCurrent());
		// SmartDashboard.putNumber("Right Slave 1 Current",
		// m_rightSlave1.getOutputCurrent());
		//
		 SmartDashboard.putNumber("Left Drive Position", getLeftPosition());
		 SmartDashboard.putNumber("Right Drive Position", getRightPosition());
		 SmartDashboard.putNumber("Drive Position", getDrivetrainPosition());

		// SmartDashboard.putNumber("Left Drive Speed", getLeftSpeed());
		// SmartDashboard.putNumber("Right Drive Speed", getRightSpeed());
		 SmartDashboard.putNumber("Yaw", getCurrentYaw());
//		 SmartDashboard.putNumber("Encoder Yaw", getEncoderYaw());
//		 SmartDashboard.putNumber("NavX update rate", m_nav.getActualUpdateRate());
	}

}