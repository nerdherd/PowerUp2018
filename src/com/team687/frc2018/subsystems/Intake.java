package com.team687.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team687.frc2018.RobotMap;

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

	m_switch = new DigitalInput(RobotMap.kLimitSwitchID);
    }

    @Override
    protected void initDefaultCommand() {
    }

    public boolean hasCube() {
	return !m_switch.get();
    }

    public void setRollerPower(double power) {
	if (!hasCube() || power <= 0) {
	    m_rollers.set(ControlMode.PercentOutput, power);
	} else {
	    m_rollers.set(ControlMode.PercentOutput, 0);
	}
    }

    public double getRollerVoltage() {
	return m_rollers.getMotorOutputVoltage();
    }

    public double getRollerCurrent() {
	return m_rollers.getOutputCurrent();
    }

    public void reportToSmartDashboard() {
	SmartDashboard.putNumber("Roller Voltage", getRollerVoltage());
	SmartDashboard.putNumber("Roller Current", getRollerCurrent());
	SmartDashboard.putBoolean("Has Cube", hasCube());
    }

}
