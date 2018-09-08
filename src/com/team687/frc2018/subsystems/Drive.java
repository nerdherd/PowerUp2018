
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
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.team687.frc2018.Robot;
import com.team687.frc2018.RobotMap;
import com.team687.frc2018.commands.drive.teleop.ArcadeDrive;
import com.team687.frc2018.commands.drive.teleop.TankDrive;
import com.team687.frc2018.constants.DriveConstants;
import com.team687.frc2018.utilities.NerdyMath;
import com.team687.lib.kauailabs.navx.frc.AHRS;
import com.team687.lib.kauailabs.sf2.frc.navXSensor;
import com.team687.lib.kauailabs.sf2.orientation.OrientationHistory;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive subsystem
 */

public class Drive extends Subsystem {

    private final TalonSRX m_leftMaster;
    private final TalonSRX m_rightMaster;

    private final VictorSPX m_leftSlave1;
    private final VictorSPX m_rightSlave1;

    private final AHRS m_nav;
    private final navXSensor m_navxsensor;
    private final OrientationHistory m_orientationHistory;

    private double m_initTime;
    private double m_currentTime;

    private boolean m_brakeModeOn;

    private String m_filePath1 = "/media/sda1/logs/";
    private String m_filePath2 = "/home/lvuser/logs/";
    private File m_file;
    public FileWriter m_writer;
    private boolean writeException = false;
    private double m_logStartTime = 0;
    
    private double m_bezierDesiredYaw;
    private double m_bezierRotError;
    private double m_bezierRotPower;

    public Drive() {
	m_leftMaster = new TalonSRX(RobotMap.kLeftMasterTalonID);
	m_leftSlave1 = new VictorSPX(RobotMap.kLeftSlaveVictorID);
	m_rightMaster = new TalonSRX(RobotMap.kRightMasterTalonID);
	m_rightSlave1 = new VictorSPX(RobotMap.kRightSlaveVictorID);

	m_leftSlave1.follow(m_leftMaster);
	m_rightSlave1.follow(m_rightMaster);

	m_leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
	m_leftMaster.setStatusFramePeriod(StatusFrame.Status_1_General, 20, 0);
	m_leftMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20, 0);
	m_leftMaster.setInverted(false);
	m_leftSlave1.setInverted(false);
	m_leftMaster.setSensorPhase(true);
	m_leftMaster.configForwardSoftLimitEnable(false, 0);
	m_leftMaster.configReverseSoftLimitEnable(false, 0);
	m_leftSlave1.configForwardSoftLimitEnable(false, 0);
	m_leftSlave1.configReverseSoftLimitEnable(false, 0);

	m_leftMaster.config_kF(0, DriveConstants.kLeftVelocityF, 0);
	m_leftMaster.config_kP(0, DriveConstants.kLeftVelocityP, 0);
	m_leftMaster.config_kI(0, DriveConstants.kLeftVelocityI, 0);
	m_leftMaster.config_kD(0, DriveConstants.kLeftVelocityD, 0);

	m_rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
	m_rightMaster.setStatusFramePeriod(StatusFrame.Status_1_General, 20, 0);
	m_rightMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 20, 0);
	m_rightMaster.setInverted(true);
	m_rightSlave1.setInverted(true);
	m_rightMaster.setSensorPhase(true);
	m_rightMaster.configForwardSoftLimitEnable(false, 0);
	m_rightMaster.configReverseSoftLimitEnable(false, 0);
	m_rightSlave1.configForwardSoftLimitEnable(false, 0);
	m_rightSlave1.configReverseSoftLimitEnable(false, 0);

	m_rightMaster.config_kF(0, DriveConstants.kRightVelocityF, 0);
	m_rightMaster.config_kP(0, DriveConstants.kRightVelocityP, 0);
	m_rightMaster.config_kI(0, DriveConstants.kRightVelocityI, 0);
	m_rightMaster.config_kD(0, DriveConstants.kRightVelocityD, 0);

	m_leftMaster.setNeutralMode(NeutralMode.Brake);
	m_leftSlave1.setNeutralMode(NeutralMode.Brake);
	m_rightMaster.setNeutralMode(NeutralMode.Brake);
	m_rightSlave1.setNeutralMode(NeutralMode.Brake);
	m_brakeModeOn = true;

	m_leftMaster.configPeakCurrentLimit(0, 0);
	m_leftMaster.configPeakCurrentDuration(0, 0);
	m_leftMaster.configContinuousCurrentLimit(DriveConstants.kContinuousCurrentLimit, 0);
	m_leftMaster.enableCurrentLimit(false);
//	 m_leftSlave1.enableCurrentLimit(false);
	m_leftMaster.configOpenloopRamp(DriveConstants.kVoltageRampRate, 0);
	m_leftMaster.configClosedloopRamp(DriveConstants.kVoltageRampRate, 0);
	m_leftMaster.configPeakOutputForward(1, 0);
	m_leftMaster.configPeakOutputReverse(-1, 0);
	m_leftSlave1.configPeakOutputForward(1, 0);
	m_leftSlave1.configPeakOutputReverse(-1, 0);

	m_rightMaster.configPeakCurrentLimit(0, 0);
	m_rightMaster.configPeakCurrentDuration(0, 0);
	m_rightMaster.configContinuousCurrentLimit(DriveConstants.kContinuousCurrentLimit, 0);
	m_rightMaster.enableCurrentLimit(false);
	// m_rightSlave1.enableCurrentLimit(false);
	m_rightMaster.configOpenloopRamp(DriveConstants.kVoltageRampRate, 0);
	m_rightMaster.configClosedloopRamp(DriveConstants.kVoltageRampRate, 0);
	m_rightMaster.configPeakOutputForward(1, 0);
	m_rightMaster.configPeakOutputReverse(-1, 0);
	m_rightSlave1.configPeakOutputForward(1, 0);
	m_rightSlave1.configPeakOutputReverse(-1, 0);

	m_nav = new AHRS(SPI.Port.kMXP);
	m_navxsensor = new navXSensor(m_nav, "Drivetrain Orientation");
	m_orientationHistory = new OrientationHistory(m_navxsensor, m_nav.getRequestedUpdateRate() * 10);
    }

    @Override
    protected void initDefaultCommand() {
	setDefaultCommand(new ArcadeDrive());
    }

    /**
     * @param leftPercentOutput
     *            (from -1.0 to +1.0)
     * @param rightPercentOutput
     *            (from -1.0 to +1.0)
     */
    public void setPower(double leftPercentOutput, double rightPercentOutput) {
    	SmartDashboard.putNumber("Left Out", leftPercentOutput);
    	SmartDashboard.putNumber("Right Out", rightPercentOutput);
    	
	m_leftMaster.set(ControlMode.PercentOutput, leftPercentOutput);
	m_rightMaster.set(ControlMode.PercentOutput, rightPercentOutput);
    }

    /**
     * @param leftVoltage
     *            (from -12V to +12V)
     * @param rightVoltage
     *            (from -12V to +12V)
     */
    public void setVoltage(double leftVoltage, double rightVoltage) {
	double leftBusVoltage = m_leftMaster.getBusVoltage();
	double rightBusVoltage = m_rightMaster.getBusVoltage();
	m_leftMaster.set(ControlMode.PercentOutput, leftVoltage / leftBusVoltage);
	m_rightMaster.set(ControlMode.PercentOutput, rightVoltage / rightBusVoltage);
    }

    /**
     * @param leftVelocity
     *            (ticks / 100ms)
     * @param rightVelocity
     *            (ticks / 100ms)
     */
    public void setVelocity(double leftVelocity, double rightVelocity) {
	m_leftMaster.set(ControlMode.Velocity, leftVelocity);
	m_rightMaster.set(ControlMode.Velocity, rightVelocity);
    }

    /**
     * @param leftPercentVelocity
     *            (from -1.0 to +1.0)
     * @param rightPercentVelocity
     *            (from -1.0 to +1.0)
     */
    public void setPercentVelocity(double leftPercentVelocity, double rightPercentVelocity) {
	m_leftMaster.set(ControlMode.Velocity, leftPercentVelocity * DriveConstants.kMaxVelocity);
	m_rightMaster.set(ControlMode.Velocity, rightPercentVelocity * DriveConstants.kMaxVelocity);
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

    public double getCurrentYaw() {
	return m_nav.getYaw();
    }

    public double getCurrentPitch() {
	return m_nav.getPitch();
    }

    public double getCurrentRoll() {
	return m_nav.getRoll();
    }

    public double getCurrentYawRadians() {
	return NerdyMath.degreesToRadians(m_nav.getYaw());
    }

    public double getCurrentPitchRadians() {
	return NerdyMath.degreesToRadians(m_nav.getPitch());
    }

    public double getCurrentRollRadians() {
	return NerdyMath.degreesToRadians(m_nav.getRoll());
    }

    public double getNavTimestamp() {
	return m_nav.getLastSensorTimestamp();
    }

    public double getHistoricalYaw(long timestamp) {
	return m_orientationHistory.getYawDegreesAtTime(timestamp);
    }

    public double timeMachineYaw(double processingTime) {
	long navxTimestamp = m_nav.getLastSensorTimestamp();
	navxTimestamp -= (1000 * processingTime); /* look backwards in time */
	return getHistoricalYaw(navxTimestamp);
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

    public double getLeftVelocity() {
	return m_leftMaster.getSelectedSensorVelocity(0);
    }

    public double getRightVelocity() {
	return m_rightMaster.getSelectedSensorVelocity(0);
    }

    public double getRightMasterCurrent() {
	return m_rightMaster.getOutputCurrent();
    }

    public double getLeftMasterCurrent() {
	return m_leftMaster.getOutputCurrent();
    }

    public double getRightSlaveCurrent() {
	return m_rightSlave1.getOutputCurrent();
    }

    public double getLeftSlaveCurrent() {
	return m_leftSlave1.getOutputCurrent();
    }

    public double getRightMasterVoltage() {
	return m_rightMaster.getMotorOutputVoltage();
    }

    public double getLeftMasterVoltage() {
	return m_leftMaster.getMotorOutputVoltage();
    }

    public double getRightSlaveVoltage() {
	return m_rightSlave1.getMotorOutputVoltage();
    }

    public double getLeftSlaveVoltage() {
	return m_leftSlave1.getMotorOutputVoltage();
    }

    public void resetEncoders() {
	m_leftMaster.setSelectedSensorPosition(0, 0, 0);
	m_rightMaster.setSelectedSensorPosition(0, 0, 0);
    }

    public void stopDrive() {
	setPower(0, 0);
    }

    public void updateBezierData(double yaw, double error, double rotPower) {
    	 m_bezierDesiredYaw = yaw;
    	 m_bezierRotError = error;
    	 m_bezierRotPower = rotPower;
    }
    
    public boolean testDriveSubsystem() {
	boolean failed = false;

	double expectedSpeed = getRightVelocity();
	if (Math.abs(getLeftVelocity() - expectedSpeed) > DriveConstants.kVelocityEpsilon) {
	    failed = true;
	    DriverStation.reportError("Drive Velocities Unequal (Drive subsystem test)", false);
	    System.out.println("Drive Velocities Unequal (Drive subsystem test)");
	}

	double expectedCurrent = getRightMasterCurrent();
	if (Math.abs(getLeftMasterCurrent() - expectedCurrent) > DriveConstants.kCurrentEpsilon
		|| Math.abs(getRightSlaveCurrent() - expectedCurrent) > DriveConstants.kCurrentEpsilon
		|| Math.abs(getLeftSlaveCurrent() - expectedCurrent) > DriveConstants.kCurrentEpsilon) {
	    failed = true;
	    DriverStation.reportError("Drive Currents Unequal (Drive subsystem test)", false);
	    System.out.println("Drive Currents Unequal (Drive subsystem test)");
	}

	double expectedVoltage = getRightMasterVoltage();
	if (Math.abs(getLeftMasterVoltage() - expectedVoltage) > DriveConstants.kVoltageEpsilon
		|| Math.abs(getRightSlaveVoltage() - expectedVoltage) > DriveConstants.kVoltageEpsilon
		|| Math.abs(getLeftSlaveVoltage() - expectedVoltage) > DriveConstants.kVoltageEpsilon) {
	    failed = true;
	    DriverStation.reportError("Drive Voltages Unequal (Drive subsystem test)", false);
	    System.out.println("Drive Voltages Unequal (Drive subsystem test)");
	}

	if (getLeftMasterCurrent() == 0 || getRightMasterCurrent() == 0 || getLeftSlaveCurrent() == 0
		|| getRightMasterCurrent() == 0) {
	    failed = true;
	    DriverStation.reportError("Drive Current at 0 (Drive subsystem test)", false);
	    System.out.println("Drive Current at 0 (Drive subsystem test)");
	}

	if (getLeftMasterVoltage() == 0 || getRightMasterVoltage() == 0 || getLeftSlaveVoltage() == 0
		|| getRightMasterVoltage() == 0) {
	    failed = true;
	    DriverStation.reportError("Drive Voltage at 0 (Drive subsystem test)", false);
	    System.out.println("Drive Voltage at 0 (Drive subsystem test)");
	}

	return failed;
    }

    public void reportToSmartDashboard() {
	// ----- COMMENT THESE OUT WHEN GOING TO FIELD ----- //
//	SmartDashboard.putBoolean("Brake Mode On", m_brakeModeOn);
//	SmartDashboard.putNumber("Left Master Voltage", getLeftMasterVoltage());
//	SmartDashboard.putNumber("Left Slave 1 Voltage", getLeftSlaveVoltage());
//	SmartDashboard.putNumber("Right Master Voltage", getRightMasterVoltage());
//	SmartDashboard.putNumber("Right Slave 1 Voltage", getRightSlaveVoltage());
//	SmartDashboard.putNumber("Left Master Current", getLeftMasterCurrent());
//	SmartDashboard.putNumber("Left Slave 1 Current", getLeftSlaveCurrent());
//	SmartDashboard.putNumber("Right Master Current", getRightMasterCurrent());
//	SmartDashboard.putNumber("Right Slave 1 Current", getRightSlaveCurrent());
	SmartDashboard.putNumber("Right Drive Velocity", getRightVelocity());
	SmartDashboard.putNumber("Left Drive Velocity", getLeftVelocity());
	// ----- COMMENT THESE OUT WHEN GOING TO FIELD ----- //

	SmartDashboard.putNumber("Right Drive Postion", getRightPosition());
	SmartDashboard.putNumber("Left Drive Position", getLeftPosition());
	SmartDashboard.putNumber("Yaw", getCurrentYaw());
    }

    public void startLog() {
	// Check to see if flash drive is mounted.
	File logFolder1 = new File(m_filePath1);
	File logFolder2 = new File(m_filePath2);
	Path filePrefix = Paths.get("");
	if (logFolder1.exists() && logFolder1.isDirectory()) {
	    filePrefix = Paths.get(logFolder1.toString(),
		    Robot.kDate + Robot.ds.getMatchType().toString() + Robot.ds.getMatchNumber() + "Drive");
	} else if (logFolder2.exists() && logFolder2.isDirectory()) {
	    filePrefix = Paths.get(logFolder2.toString(),
		    Robot.kDate + Robot.ds.getMatchType().toString() + Robot.ds.getMatchNumber() + "Drive");
	} else {
	    writeException = true;
	}

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
		m_writer.append("Time,MatchTime,RightPosition,LeftPosition,RightVelocity,LeftVelocity,Yaw"
			+ "RightMasterVoltage,RightSlaveVoltage,LeftMasterVoltage,LeftSlaveVoltage,"
			+ "RightMasterCurrent,RightSlaveCurrent,LeftMasterCurrent,LeftSlaveCurrent,ScaleOnLeft,SwitchOnLeft,BezierDesiredYaw,BezierError,BezierRotPower\n");
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
    System.out.println("test write");
	if (!writeException) {
	    try {
		double timestamp = Timer.getFPGATimestamp() - m_logStartTime;
		m_writer.append(String.valueOf(timestamp) + "," + String.valueOf(Robot.ds.getMatchTime()) + ","
			+ String.valueOf(getRightPosition()) + "," + String.valueOf(getLeftPosition()) + ","
			+ String.valueOf(getRightVelocity()) + "," + String.valueOf(getLeftVelocity()) + ","
			+ String.valueOf(getCurrentYaw()) + "," + String.valueOf(getRightMasterVoltage()) + ","
			+ String.valueOf(getRightSlaveVoltage()) + "," + String.valueOf(getLeftMasterVoltage()) + ","
			+ String.valueOf(getLeftSlaveVoltage()) + "," + String.valueOf(getRightMasterCurrent()) + ","
			+ String.valueOf(getRightSlaveCurrent()) + "," + String.valueOf(getLeftMasterCurrent()) + ","
			+ String.valueOf(getLeftSlaveCurrent()) + "," + String.valueOf(Robot.scaleOnLeft) + "," + String.valueOf(Robot.switchOnLeft) + 
			"," + String.valueOf(m_bezierDesiredYaw) + ","
					+ String.valueOf(m_bezierRotError) + "," + String.valueOf(m_bezierRotPower) + "\n");
//		m_writer.flush();
	    } catch (IOException e) {
		e.printStackTrace();
		writeException = true;
	    }
	}
    }

}