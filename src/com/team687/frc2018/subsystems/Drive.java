package com.team687.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team687.frc2018.Robot;
import com.team687.frc2018.RobotMap;
import com.team687.frc2018.commands.drive.teleop.TankDrive;
import com.team687.frc2018.constants.DriveConstants;
import com.team687.frc2018.utilities.CSVDatum;
import com.team687.frc2018.utilities.NerdyMath;
import com.team687.lib.kauailabs.navx.frc.AHRS;
import com.team687.lib.kauailabs.sf2.frc.navXSensor;
import com.team687.lib.kauailabs.sf2.orientation.OrientationHistory;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive subsystem
 */

public class Drive extends Subsystem {

    private final TalonSRX m_leftMaster, m_leftSlave1;
    private final TalonSRX m_rightMaster, m_rightSlave1;

    private final AHRS m_nav;
    private final navXSensor m_navxsensor;
    private final OrientationHistory m_orientationHistory;

    private double m_initTime;
    private double m_currentTime;

    private boolean m_brakeModeOn;

    public Drive() {
	m_leftMaster = new TalonSRX(RobotMap.kLeftMasterTalonID);
	m_leftSlave1 = new TalonSRX(RobotMap.kLeftSlaveTalon1ID);
	m_rightMaster = new TalonSRX(RobotMap.kRightMasterTalonID);
	m_rightSlave1 = new TalonSRX(RobotMap.kRightSlaveTalon1ID);

	m_leftSlave1.follow(m_leftMaster);
	m_rightSlave1.follow(m_rightMaster);

	m_leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
	m_leftMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, 0);
	m_leftMaster.setInverted(false);
	m_leftSlave1.setInverted(false);
	m_leftMaster.setSensorPhase(true);
	m_leftMaster.config_kF(0, DriveConstants.kLeftVelocityF, 0);
	m_leftMaster.config_kP(0, DriveConstants.kLeftVelocityP, 0);
	m_leftMaster.config_kI(0, DriveConstants.kLeftVelocityI, 0);
	m_leftMaster.config_kD(0, DriveConstants.kLeftVelocityD, 0);

	m_rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
	m_rightMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, 0);
	m_rightMaster.setInverted(true);
	m_rightSlave1.setInverted(true);
	m_rightMaster.setSensorPhase(true);
	m_rightMaster.config_kF(0, DriveConstants.kRightVelocityF, 0);
	m_rightMaster.config_kP(0, DriveConstants.kRightVelocityP, 0);
	m_rightMaster.config_kI(0, DriveConstants.kRightVelocityI, 0);
	m_rightMaster.config_kD(0, DriveConstants.kRightVelocityD, 0);

	m_leftMaster.setNeutralMode(NeutralMode.Brake);
	m_leftSlave1.setNeutralMode(NeutralMode.Brake);
	m_rightMaster.setNeutralMode(NeutralMode.Brake);
	m_rightSlave1.setNeutralMode(NeutralMode.Brake);
	m_brakeModeOn = true;

	m_nav = new AHRS(SerialPort.Port.kMXP);
	m_navxsensor = new navXSensor(m_nav, "Drivetrain Orientation");
	m_orientationHistory = new OrientationHistory(m_navxsensor, m_nav.getRequestedUpdateRate() * 10);
    }

    @Override
    protected void initDefaultCommand() {
	setDefaultCommand(new TankDrive());
    }

    /**
     * @param leftPercentOutput
     *            (from -1.0 to +1.0)
     * @param rightPercentOutput
     *            (from -1.0 to +1.0)
     */
    public void setPower(double leftPercentOutput, double rightPercentOutput) {
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
	return (getLeftPosition() + getRightPosition() / 2);
    }

    public double getLeftVelocity() {
	return m_leftMaster.getSelectedSensorVelocity(1);
    }

    public double getRightVelocity() {
	return m_rightMaster.getSelectedSensorVelocity(2);
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
	SmartDashboard.putBoolean("Brake Mode On", m_brakeModeOn);

	SmartDashboard.putNumber("Left Master Voltage", getLeftMasterVoltage());
	SmartDashboard.putNumber("Left Slave 1 Voltage", getLeftSlaveVoltage());
	SmartDashboard.putNumber("Right Master Voltage", getRightMasterVoltage());
	SmartDashboard.putNumber("Right Slave 1 Voltage", getRightSlaveVoltage());

	SmartDashboard.putNumber("Left Master Current", getLeftMasterCurrent());
	SmartDashboard.putNumber("Left Slave 1 Current", getLeftSlaveCurrent());
	SmartDashboard.putNumber("Right Master Current", getRightMasterCurrent());
	SmartDashboard.putNumber("Right Slave 1 Current", getRightSlaveCurrent());
    }

    private CSVDatum m_leftMasterVoltage, m_leftSlaveVoltage, m_rightMasterVoltage, m_rightSlaveVoltage;
    private CSVDatum m_leftMasterCurrent, m_leftSlaveCurrent, m_rightMasterCurrent, m_rightSlaveCurrent;

    public void addLoggedData() {
	m_leftMasterVoltage = new CSVDatum("drive_leftMasterVoltage");
	m_leftSlaveVoltage = new CSVDatum("drive_leftSlaveVoltage");
	m_rightMasterVoltage = new CSVDatum("drive_rightMasterVoltage");
	m_rightSlaveVoltage = new CSVDatum("drive_rightSlaveVoltage");

	m_leftMasterCurrent = new CSVDatum("drive_leftMasterCurrent");
	m_leftSlaveCurrent = new CSVDatum("drive_leftSlaveCurrent");
	m_rightMasterCurrent = new CSVDatum("drive_rightMasterCurrent");
	m_rightSlaveCurrent = new CSVDatum("drive_rightSlaveCurrent");

	Robot.logger.addCSVDatum(m_leftMasterVoltage);
	Robot.logger.addCSVDatum(m_leftSlaveVoltage);
	Robot.logger.addCSVDatum(m_rightMasterVoltage);
	Robot.logger.addCSVDatum(m_rightSlaveVoltage);

	Robot.logger.addCSVDatum(m_leftMasterCurrent);
	Robot.logger.addCSVDatum(m_leftSlaveCurrent);
	Robot.logger.addCSVDatum(m_rightMasterCurrent);
	Robot.logger.addCSVDatum(m_rightSlaveCurrent);
    }

    public void updateLog() {
	m_leftMasterVoltage.updateValue(getLeftMasterVoltage());
	m_leftSlaveVoltage.updateValue(getLeftSlaveVoltage());
	m_rightMasterVoltage.updateValue(getRightMasterVoltage());
	m_rightSlaveVoltage.updateValue(getRightSlaveVoltage());

	m_leftMasterCurrent.updateValue(getLeftMasterCurrent());
	m_leftSlaveCurrent.updateValue(getLeftSlaveCurrent());
	m_rightMasterCurrent.updateValue(getRightMasterCurrent());
	m_rightSlaveCurrent.updateValue(getRightSlaveCurrent());
    }

}