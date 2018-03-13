package com.team687.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team687.frc2018.RobotMap;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {

	public final TalonSRX m_climber1;
	public final TalonSRX m_climber2;

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

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
