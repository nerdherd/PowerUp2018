package com.team687.frc2017.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.team687.frc2017.RobotMap;
import com.team687.frc2017.commands.teleop.TankDrive;
import com.team687.frc2017.constants.DriveConstants;
import com.team687.frc2017.utilities.NerdyMath;
import com.team687.lib.kauailabs.navx.frc.AHRS;
import com.team687.lib.kauailabs.sf2.frc.navXSensor;
import com.team687.lib.kauailabs.sf2.orientation.OrientationHistory;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive subsystem
 */

public class Drive extends Subsystem {

    private final WPI_TalonSRX m_leftMaster, m_leftSlave1, m_leftSlave2;
    private final WPI_TalonSRX m_rightMaster, m_rightSlave1, m_rightSlave2;

    private final int m_leftSensorIndex = 1;
    private final int m_rightSensorIndex = 2;

    private final DoubleSolenoid m_shifter;

    private final AHRS m_nav;
    private final navXSensor m_navxsensor;
    private final OrientationHistory m_orientationHistory;

    private double m_initTime;
    private double m_currentTime;

    private boolean m_brakeModeOn;

    private double m_rightSensorOffset, m_leftSensorOffset;
    private double m_rightSensorCumulativeOffset, m_leftSensorCumulativeOffset;

    public Drive() {
	m_leftMaster = new WPI_TalonSRX(RobotMap.kLeftMasterTalonID);
	m_leftSlave1 = new WPI_TalonSRX(RobotMap.kLeftSlaveTalon1ID);
	m_leftSlave2 = new WPI_TalonSRX(RobotMap.kLeftSlaveTalon2ID);
	m_rightMaster = new WPI_TalonSRX(RobotMap.kRightMasterTalonID);
	m_rightSlave1 = new WPI_TalonSRX(RobotMap.kRightSlaveTalon1ID);
	m_rightSlave2 = new WPI_TalonSRX(RobotMap.kRightSlaveTalon2ID);

	m_leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, m_leftSensorIndex, 0);
	m_leftMaster.setInverted(false);
	m_leftSlave1.setInverted(false);
	m_leftSlave2.setInverted(false);
	m_leftMaster.setSensorPhase(true); // check this on actual robot
	m_leftMaster.setNeutralMode(NeutralMode.Brake);
	m_leftSlave1.setNeutralMode(NeutralMode.Brake);
	m_leftSlave2.setNeutralMode(NeutralMode.Brake);

	m_rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, m_rightSensorIndex, 0);
	m_rightMaster.setInverted(true);
	m_rightSlave1.setInverted(true);
	m_rightSlave2.setInverted(true);
	m_rightMaster.setSensorPhase(true); // check this on actual robot
	m_rightMaster.setNeutralMode(NeutralMode.Brake);
	m_rightSlave1.setNeutralMode(NeutralMode.Brake);
	m_rightSlave2.setNeutralMode(NeutralMode.Brake);

	m_brakeModeOn = true;
	m_rightSensorOffset = 0;
	m_leftSensorOffset = 0;
	m_rightSensorCumulativeOffset = 0;
	m_leftSensorCumulativeOffset = 0;

	m_shifter = new DoubleSolenoid(RobotMap.kShifterID1, RobotMap.kShifterID2);

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
	m_leftMaster.set(ControlMode.PercentOutput, RobotMap.kLeftMasterTalonID);
	m_leftSlave1.set(ControlMode.PercentOutput, RobotMap.kLeftSlaveTalon1ID);
	m_leftSlave2.set(ControlMode.PercentOutput, RobotMap.kLeftSlaveTalon2ID);
	m_rightMaster.set(ControlMode.PercentOutput, RobotMap.kRightMasterTalonID);
	m_rightSlave1.set(ControlMode.PercentOutput, RobotMap.kRightSlaveTalon1ID);
	m_rightSlave2.set(ControlMode.PercentOutput, RobotMap.kRightSlaveTalon2ID);

	m_leftMaster.set(leftPower);
	m_leftSlave1.set(leftPower);
	m_leftSlave2.set(leftPower);

	m_rightMaster.set(rightPower);
	m_rightSlave1.set(rightPower);
	m_rightSlave2.set(rightPower);
    }

    public void setBrakeMode(boolean enabled) {
	if (enabled) {
	    m_leftMaster.setNeutralMode(NeutralMode.Brake);
	    m_leftSlave1.setNeutralMode(NeutralMode.Brake);
	    m_leftSlave2.setNeutralMode(NeutralMode.Brake);

	    m_rightMaster.setNeutralMode(NeutralMode.Brake);
	    m_rightSlave1.setNeutralMode(NeutralMode.Brake);
	    m_rightSlave2.setNeutralMode(NeutralMode.Brake);
	} else {
	    m_leftMaster.setNeutralMode(NeutralMode.Coast);
	    m_leftSlave1.setNeutralMode(NeutralMode.Coast);
	    m_leftSlave2.setNeutralMode(NeutralMode.Coast);

	    m_rightMaster.setNeutralMode(NeutralMode.Coast);
	    m_rightSlave1.setNeutralMode(NeutralMode.Coast);
	    m_rightSlave2.setNeutralMode(NeutralMode.Coast);
	}

	m_brakeModeOn = enabled;
    }

    public boolean getBrakeMode() {
	return m_brakeModeOn;
    }

    public double squareInput(double input) {
	return Math.pow(input, 2) * (input / Math.abs(input));
    }

    // public double addLeftSensitivity(double input) {
    // return NerdyMath.addSensitivity(input, Robot.oi.getThrottleL());
    // }
    //
    // public double addRightSensitivity(double input) {
    // return NerdyMath.addSensitivity(input, Robot.oi.getThrottleR());
    // }

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

    public void shiftUp() {
	m_shifter.set(DoubleSolenoid.Value.kForward);
    }

    public void shiftDown() {
	m_shifter.set(DoubleSolenoid.Value.kReverse);
    }

    public boolean isHighGear() {
	return m_shifter.get() == DoubleSolenoid.Value.kForward;
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
	return m_leftMaster.getSelectedSensorPosition(m_leftSensorIndex) + m_leftSensorCumulativeOffset;
    }

    public double getRightPosition() {
	return m_rightMaster.getSelectedSensorPosition(m_rightSensorIndex) + m_rightSensorCumulativeOffset;
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
	// m_leftMaster.reset();
	// m_rightMaster.reset();

	m_leftSensorOffset = getLeftPosition();
	m_rightSensorOffset = getRightPosition();

	m_leftSensorCumulativeOffset += m_leftSensorOffset;
	m_rightSensorOffset += m_rightSensorOffset;

	// m_leftMaster.setEncPosition(0);
	// m_rightMaster.setEncPosition(0);
    }

    public void stopDrive() {
	setPower(0.0, 0.0);
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
	if (isHighGear()) {
	    SmartDashboard.putString("Gear Shift", "High");
	} else if (!isHighGear()) {
	    SmartDashboard.putString("Gear Shift", "Low");
	}

	SmartDashboard.putBoolean("Brake Mode On", m_brakeModeOn);

	SmartDashboard.putNumber("Left Master PercentVbus", m_leftMaster.getMotorOutputVoltage());
	SmartDashboard.putNumber("Left Slave 1 PercentVbus", m_leftSlave1.getMotorOutputVoltage());
	SmartDashboard.putNumber("Left Slave 2 PercentVbus", m_leftSlave2.getMotorOutputVoltage());
	SmartDashboard.putNumber("Right Master PercentVbus", m_rightMaster.getMotorOutputVoltage());
	SmartDashboard.putNumber("Right Slave 1 PercentVbus", m_rightSlave1.getMotorOutputVoltage());
	SmartDashboard.putNumber("Right Slave 2 PercentVbus", m_rightSlave2.getMotorOutputVoltage());

	SmartDashboard.putNumber("Left Master Current", m_leftMaster.getOutputCurrent());
	SmartDashboard.putNumber("Left Slave 1 Current", m_leftSlave1.getOutputCurrent());
	SmartDashboard.putNumber("Left Slave 2 Current", m_leftSlave2.getOutputCurrent());
	SmartDashboard.putNumber("Right Master Current", m_rightMaster.getOutputCurrent());
	SmartDashboard.putNumber("Right Slave 1 Current", m_rightSlave1.getOutputCurrent());
	SmartDashboard.putNumber("Right Slave 2 Current", m_rightSlave2.getOutputCurrent());
    }

}