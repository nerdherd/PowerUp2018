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

	private String m_filePath1 = "/media/sda1/logs/";
	private String m_filePath2 = "/home/lvuser/logs/";
	private File m_file;
	private FileWriter m_writer;
	private boolean writeException = false;

	// Status Frame Docs in Section 20 of Software Reference Manual
	public static final int loggingFrameRate = 20;

	private double m_logStartTime;

	private final PigeonIMU m_pigeon;
	private double[] m_ypr = new double[3];

	private double m_yawOffset, m_pitchOffset, m_rollOffset = 0;

	public Wrist() {
		m_wrist = new TalonSRX(RobotMap.kWristID);
		m_pigeon = new PigeonIMU(RobotMap.kPigeonWristID);

		m_wrist.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
		m_wrist.config_kF(0, 0.681318681, 0);
		// m_wrist.config_kF(0, 0, 0);
		m_wrist.config_kP(0, 3, 0);
		m_wrist.config_kI(0, 0, 0);
		m_wrist.config_kD(0, 0, 0);
		m_wrist.configMotionCruiseVelocity(1241, 0);
		m_wrist.configMotionAcceleration(1254, 0);
		m_wrist.setNeutralMode(NeutralMode.Coast);

		m_wrist.configPeakOutputForward(SuperstructureConstants.kWristMaxVoltageForward / 12, 0);
		m_wrist.configPeakOutputReverse(SuperstructureConstants.kWristMaxVoltageReverse / 12, 0);
		m_wrist.configClosedloopRamp(SuperstructureConstants.kWristRampRate, 0);
		m_wrist.configOpenloopRamp(SuperstructureConstants.kWristRampRate, 0);

		m_wrist.configPeakCurrentLimit(0, 0);
		m_wrist.configContinuousCurrentLimit(SuperstructureConstants.kWristContinuousCurrent, 0);
		m_wrist.enableCurrentLimit(false);

		m_wrist.configForwardSoftLimitThreshold(SuperstructureConstants.kWristForwardSoftLimit, 0);
		m_wrist.configReverseSoftLimitThreshold(SuperstructureConstants.kWristReverseSoftLimit, 0);
		m_wrist.configForwardSoftLimitEnable(false, 0);
		m_wrist.configReverseSoftLimitEnable(false, 0);

		m_wrist.setInverted(true);
		m_wrist.setSensorPhase(true);
		m_wrist.setStatusFramePeriod(StatusFrame.Status_1_General, 10, 0);
		m_wrist.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20, 0);
	}

	@Override
	protected void initDefaultCommand() {
		// setDefaultCommand(new MySpecialCommand());
	}

	private double m_desiredPos = 0;

	public void setPosition(double position) {
		m_desiredPos = position;
		m_wrist.set(ControlMode.MotionMagic, position);
	}

	public void setPercentOutput(double power) {
		m_wrist.set(ControlMode.PercentOutput, power);
	}

	public void setVoltage(double voltage) {
		m_wrist.set(ControlMode.PercentOutput, voltage / m_wrist.getBusVoltage());
	}

	public void updateYawPitchRoll() {
		m_pigeon.getYawPitchRoll(m_ypr);
	}

	private double m_restingWristYaw = 0;

	public double getYaw() {
		return ((360 - m_ypr[0]) % 360) - m_yawOffset + m_restingWristYaw;
	}

	public double getPitch() {
		return ((360 - m_ypr[1]) % 360) - m_pitchOffset;
	}

	public double getRoll() {
		return ((360 - m_ypr[2]) % 360) - m_rollOffset;
	}

	public void resetYaw() {
		m_yawOffset += getYaw();
	}

	public void resetPitch() {
		m_pitchOffset += getPitch();
	}

	public void resetRoll() {
		m_rollOffset += getRoll();
	}

	public void enterCalibrationMode() {
		m_pigeon.enterCalibrationMode(CalibrationMode.Temperature, 0);
	}

	public double getPositionRelative() {
		return m_wrist.getSelectedSensorPosition(0);
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
		return ticksToDegrees(getPosition() + 700 + 2560) + 55;
	}

	public double getDesiredAngle() {
		return ticksToDegrees(m_desiredPos + 700 + 2560) + 55;
	}

	public double getAngleAbsolute() {
		return getAngleRelative() + Robot.arm.getAbsoluteAngle();
	}

	public double angleAbsoluteToRelative(double angleAbsolute) {
		return angleAbsolute - Robot.arm.getAbsoluteAngle();
	}

	public double angleRelativeToTicks(double angleRelative) {
		return degreesToTicks(angleRelative - 55) - 700 - 2560;
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
	public double getDesiredAbsoluteAngleGoingUp() {
		double _r3 = SuperstructureConstants.kWristPivotToTip;
		double theta2 = Robot.arm.getAbsoluteAngle();
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

	public double getDesiredAbsoluteAngleGoingDown() {
		double _r3 = SuperstructureConstants.kWristPivotToTip;
		double theta2 = Robot.arm.getAbsoluteAngle();
		double x2 = Robot.arm.getX();
		double y2 = Robot.arm.getY();
		double _theta3_offset = -16;
		if (theta2 <= 55) {
			return 90 - _theta3_offset; // DEGREES(ACOS((45-[@x2])/_r3))-theta3_offset
		} else if (theta2 <= 100) {
			double alpha = (90 - theta2) / (90 - 55);
			return alpha * 45 + (1 - alpha) * 90 - _theta3_offset; // -1.75*[@theta2]+135.3-theta3_offset THIS HAS BEEN
																	// CHANGED
		} else {
			return NerdyMath.radiansToDegrees(Math.asin((92 - y2) / _r3)) - _theta3_offset; // DEGREES(ASIN((88-[@y2])/_r3))-theta3_offset
		}
	}

	public double getSpeed() {
		return m_wrist.getSelectedSensorVelocity(0);
	}

	// see if we can avoid using this for the wrist
	public void resetEncoder() {
		m_wrist.setSelectedSensorPosition(0 + SuperstructureConstants.kWristResetPosition, 0, 0);
	}

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
		return getPositionRelative() <= SuperstructureConstants.kWristStowPos
				&& getPositionRelative() >= SuperstructureConstants.kWristIntakePos;
	}

	public void reportToSmartDashboard() {
		SmartDashboard.putNumber("Wrist Position", getPosition());
		// SmartDashboard.putNumber("Wrist Desired Position",
		// angleAbsoluteToTicks(getDesiredAbsoluteAngle()));
		SmartDashboard.putNumber("Wrist Absolute Angle", getAngleAbsolute());
		// SmartDashboard.putNumber("Wrist Desired Absolute Angle",
		// getDesiredAbsoluteAngle());
		// SmartDashboard.putNumber("Wrist Voltage", getVoltage());
		// SmartDashboard.putNumber("Wrist Current", getCurrent());
		// SmartDashboard.putNumber("Wrist Yaw", getYaw());
		// SmartDashboard.putNumber("Wrist Pitch", getPitch());
		// SmartDashboard.putNumber("Wrist Roll", getRoll());
	}

	public void startLog() {
		writeException = false;
		// Check to see if flash drive is mounted.
		File logFolder1 = new File(m_filePath1);
		File logFolder2 = new File(m_filePath2);
		Path filePrefix = Paths.get("");
		if (logFolder1.exists() && logFolder1.isDirectory())
			filePrefix = Paths.get(logFolder1.toString(), "2018_02_24_Wrist");
		else if (logFolder2.exists() && logFolder2.isDirectory())
			filePrefix = Paths.get(logFolder2.toString(),
					SmartDashboard.getString("log_file_name", "2018_02_24_Wrist"));
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
				m_writer.append(
						"Time,Position,DesiredPos,DesiredAngle,Velocity,PigeonAngle,EncoderAngle,Voltage,Current\n");
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
				m_writer.append(String.valueOf(timestamp) + "," + String.valueOf(getPositionRelative()) + ","
						+ String.valueOf(m_desiredPos) + "," + String.valueOf(getDesiredAngle()) + ","
						+ String.valueOf(getSpeed()) + "," + String.valueOf(getYaw()) + ","
						+ String.valueOf(getAngleAbsolute()) + "," + String.valueOf(getVoltage()) + ","
						+ String.valueOf(getCurrent()) + "\n");
				m_writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
				writeException = true;
			}
		}
	}

}