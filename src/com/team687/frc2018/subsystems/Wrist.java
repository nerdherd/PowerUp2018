package com.team687.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team687.frc2018.Robot;
import com.team687.frc2018.RobotMap;
import com.team687.frc2018.constants.SuperstructureConstants;

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
	m_wrist.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    protected void initDefaultCommand() {
	// setDefaultCommand(new MySpecialCommand());
    }

    public void setPosition(double position) {
	if (Robot.arm.getPosition() < SuperstructureConstants.kArmWristSafePos) {
	    position = Math.min(SuperstructureConstants.kWristStowPos,
		    Math.max(position, SuperstructureConstants.kWristIntakePos));
	}
	m_wrist.set(ControlMode.Position, position);
    }

    public void setZeroVoltage() {
	m_wrist.set(ControlMode.PercentOutput, 0);
    }

    // // real world units
    // public double getPosition() {
    // return m_wrist.getSelectedSensorPosition(0) / 4096;
    // }
    //
    // public double getSpeed() {
    // return m_wrist.getSelectedSensorVelocity(0) * (600 / 4096);
    // }

    public double getPosition() {
	return m_wrist.getSelectedSensorPosition(0);
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