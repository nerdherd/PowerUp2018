package com.team687.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team687.frc2018.RobotMap;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Intake subsystem
 */

public class Intake extends Subsystem {

    private final TalonSRX m_rollers;
    private final DigitalInput m_switch;

    public Intake() {
	m_rollers = new TalonSRX(RobotMap.kIntakeRollersID);
	m_rollers.setNeutralMode(NeutralMode.Coast);
	m_rollers.setStatusFramePeriod(StatusFrame.Status_1_General, 15, 0);
	
	m_rollers.configPeakOutputForward(1, 0);
	m_rollers.configPeakOutputReverse(-1, 0);
	m_rollers.enableCurrentLimit(false);

	m_switch = new DigitalInput(RobotMap.kLimitSwitchID);
    }

    @Override
    protected void initDefaultCommand() {
    }

    public boolean hasCube() {
	return !m_switch.get();
    }

    public boolean isMaxCurrent() {
	return getCurrent() > SuperstructureConstants.kRollerMaxCurrent;
    }

    public void setRollerPower(double power) {
	m_rollers.set(ControlMode.PercentOutput, power);
    }

    public double getVoltage() {
	return m_rollers.getMotorOutputVoltage();
    }

    public double getCurrent() {
	return m_rollers.getOutputCurrent();
    }

    public void reportToSmartDashboard() {
	SmartDashboard.putNumber("Roller Voltage", getVoltage());
	SmartDashboard.putNumber("Roller Current", getCurrent());
	SmartDashboard.putBoolean("Has Cube", hasCube());
	SmartDashboard.putBoolean("Reached Max Current", isMaxCurrent());
    }

}
