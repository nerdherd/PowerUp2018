package com.team687.frc2017.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team687.frc2017.RobotMap;
import com.team687.frc2017.constants.ArmConstants;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Arm extends Subsystem {

    private final TalonSRX m_artic;

    public Arm() {
	m_artic = new TalonSRX(RobotMap.kArticID);

	m_artic.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
	m_artic.config_kP(0, ArmConstants.kArticP, 0);
	m_artic.config_kI(0, ArmConstants.kArticI, 0);
	m_artic.config_kD(0, ArmConstants.kArticD, 0);
    }

    public void setPos(double pos) {
	double position = Math.min(ArmConstants.kArmMax, Math.max(pos, ArmConstants.kArmMin));
	m_artic.set(ControlMode.Position, position);
    }

    public void resetEncoder() {
	m_artic.setSelectedSensorPosition(0, 0, 0);
    }

    public void setZeroVoltage() {
	m_artic.set(ControlMode.PercentOutput, 0);
    }

    public double getArticPosition() {
	return m_artic.getSelectedSensorPosition(0) / 4096;
    }

    public double getArticSpeed() {
	return m_artic.getSelectedSensorVelocity(0) * (600 / 4096);
    }

    public double getArticPositionTicks() {
	return m_artic.getSelectedSensorPosition(0);
    }

    public double getArticSpeedTicks() {
	return m_artic.getSelectedSensorVelocity(0);
    }

    public void initDefaultCommand() {
	// setDefaultCommand(new MySpecialCommand());
    }
}