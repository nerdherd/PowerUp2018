package com.team687.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team687.frc2018.RobotMap;
import com.team687.frc2018.commands.drive.teleop.TankDrive;
import com.team687.frc2018.constants.DriveConstants;
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

	m_leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
	m_leftMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, 0);
	m_leftMaster.setInverted(false);
	m_leftSlave1.setInverted(false);
	m_leftMaster.setSensorPhase(true); // check this on actual robot
	m_leftMaster.setNeutralMode(NeutralMode.Brake);
	m_leftSlave1.setNeutralMode(NeutralMode.Brake);

	m_rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
	m_rightMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, 0);
	m_rightMaster.setInverted(true);
	m_rightSlave1.setInverted(true);
	m_rightMaster.setSensorPhase(true); // check this on actual robot
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
     * Set drivetrain motor power to value between -1.0 and +1.0
     * 
     * @param leftPower
     * @param rightPower
     */
    public void setPower(double leftPower, double rightPower) {
	m_leftMaster.set(ControlMode.PercentOutput, leftPower);
	m_leftSlave1.set(ControlMode.PercentOutput, leftPower);

	m_rightMaster.set(ControlMode.PercentOutput, rightPower);
	m_rightSlave1.set(ControlMode.PercentOutput, rightPower);
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

    public double getLeftSpeed() {
	return m_leftMaster.getSelectedSensorVelocity(1);
    }

    public double getRightSpeed() {
	return m_rightMaster.getSelectedSensorVelocity(2);
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

    public boolean testDriveSubsystem() {
	boolean failed = false;

	double expectedSpeed = getRightSpeed();
	if (Math.abs(getLeftSpeed() - expectedSpeed) > DriveConstants.rpmEpsilon) {
	    failed = true;
	    DriverStation.reportError("Left Master Speed != Right Master Speed (Drive subsystem test)", false);
	    System.out.println("Left Master Speed != Right Master Speed (Drive subsystem test)");
	}

	return failed;
    }

    public void reportToSmartDashboard() {
	SmartDashboard.putBoolean("Brake Mode On", m_brakeModeOn);

	SmartDashboard.putNumber("Left Master Voltage", m_leftMaster.getMotorOutputVoltage());
	SmartDashboard.putNumber("Left Slave 1 Voltage", m_leftSlave1.getMotorOutputVoltage());
	SmartDashboard.putNumber("Right Master Voltage", m_rightMaster.getMotorOutputVoltage());
	SmartDashboard.putNumber("Right Slave 1 Voltage", m_rightSlave1.getMotorOutputVoltage());

	SmartDashboard.putNumber("Left Master Current", m_leftMaster.getOutputCurrent());
	SmartDashboard.putNumber("Left Slave 1 Current", m_leftSlave1.getOutputCurrent());
	SmartDashboard.putNumber("Right Master Current", m_rightMaster.getOutputCurrent());
	SmartDashboard.putNumber("Right Slave 1 Current", m_rightSlave1.getOutputCurrent());
    }

}