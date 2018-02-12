package com.team687.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team687.frc2018.Robot;
import com.team687.frc2018.RobotMap;
import com.team687.frc2018.constants.SuperstructureConstants;
import com.team687.frc2018.utilities.NerdyMath;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Wrist subsystem
 */

public class Wrist extends Subsystem {

    private final TalonSRX m_wrist;

    public Wrist() {
	m_wrist = new TalonSRX(RobotMap.kWristID);

	m_wrist.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
	m_wrist.config_kP(0, SuperstructureConstants.kWristP, 0);
	m_wrist.config_kI(0, SuperstructureConstants.kWristI, 0);
	m_wrist.config_kD(0, SuperstructureConstants.kWristD, 0);
	m_wrist.setNeutralMode(NeutralMode.Coast);

	m_wrist.configPeakOutputForward(SuperstructureConstants.kWristMaxVoltageForward / 12, 0);
	m_wrist.configPeakOutputReverse(SuperstructureConstants.kWristMaxVoltageReverse / 12, 0);
	m_wrist.configClosedloopRamp(SuperstructureConstants.kWristRampRate, 0);

	m_wrist.configPeakCurrentLimit(0, 0);
	m_wrist.configContinuousCurrentLimit(SuperstructureConstants.kWristContinuousCurrent, 0);
	m_wrist.enableCurrentLimit(true);

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
    }

    public void setPosition(double position) {
	m_wrist.set(ControlMode.Position, position);
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
	return ticksToDegrees(getPosition() + 650) + 52; // 650 is the offset that accounts for our zeroing because we
							 // don't zero our encoder at exactly 0 degrees)
    }

    public double getAngleAbsolute() {
	return getAngleRelative() + Robot.arm.getAbsoluteAngle();
    }

    public double angleAbsoluteToRelative(double angleAbsolute) {
	return angleAbsolute - Robot.arm.getAbsoluteAngle();
    }

    public double angleRelativeToTicks(double angleRelative) {
	return degreesToTicks(angleRelative - 52) - 650;
    }

    public double angleAbsoluteToTicks(double angle) {
	return angleRelativeToTicks(angleAbsoluteToRelative(angle));
    }

    public void setAngleAbsolute(double angle) {
	setPosition(angleAbsoluteToTicks(angle));
    }

    public double getDesiredAbsoluteAngle() {
	if (Robot.arm.getAbsoluteAngle() <= 0) {
	    return 90;
	} else if (Robot.arm.getAbsoluteAngle() <= 40) {
	    return NerdyMath.radiansToDegrees(
		    Math.acos(41 * (1 - Math.cos(NerdyMath.degreesToRadians(Robot.arm.getAbsoluteAngle()))) / 12));
	} else if (Robot.arm.getAbsoluteAngle() <= 50) {
	    return Robot.arm.getAbsoluteAngle();
	} else {
	    return NerdyMath.radiansToDegrees(
		    Math.asin(41 * (1 - Math.sin(NerdyMath.degreesToRadians(Robot.arm.getAbsoluteAngle()))) / 12));
	}
    }

    public double getSpeed() {
	return m_wrist.getSelectedSensorVelocity(0);
    }

    // see if we can avoid using this for the wrist
    public void resetEncoder() {
	m_wrist.setSelectedSensorPosition(0, 0, 0);
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
	return getPosition() <= SuperstructureConstants.kWristStowPos
		&& getPosition() >= SuperstructureConstants.kWristIntakePos;
    }

    public void reportToSmartDashboard() {
	SmartDashboard.putNumber("Wrist Position", getPosition());
	SmartDashboard.putNumber("Wrist Velocity", getSpeed());
	SmartDashboard.putNumber("Wrist Voltage", getVoltage());
	SmartDashboard.putNumber("Wrist Current", getCurrent());
	SmartDashboard.putBoolean("Wrist Safe", isWristSafe());
    }

}