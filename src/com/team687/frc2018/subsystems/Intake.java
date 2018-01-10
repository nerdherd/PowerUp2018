package com.team687.frc2018.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team687.frc2018.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Intake subsystem
 */

public class Intake extends Subsystem {

    private final TalonSRX m_rollers;
    private final DoubleSolenoid m_claw;

    public Intake() {
	m_rollers = new TalonSRX(RobotMap.kIntakeRollersID);
	m_rollers.setNeutralMode(NeutralMode.Coast);

	m_claw = new DoubleSolenoid(RobotMap.kClawID1, RobotMap.kClawID2);
    }

    @Override
    protected void initDefaultCommand() {
    }

    public void setRollerPower(double power) {
	m_rollers.set(ControlMode.PercentOutput, power);
    }

    public void closeClaw(boolean clawClose) {
	if (clawClose) {
	    m_claw.set(DoubleSolenoid.Value.kForward);
	} else {
	    m_claw.set(DoubleSolenoid.Value.kReverse);
	}
    }

    public boolean isClawClosed() {
	boolean clawClosed = false;
	if (m_claw.get() == DoubleSolenoid.Value.kForward) {
	    clawClosed = true;
	}
	return clawClosed;
    }

    public double getRollerVoltage() {
	return m_rollers.getMotorOutputVoltage();
    }

    public double getRollerCurrent() {
	return m_rollers.getOutputCurrent();
    }

    public void reportToSmartDashboard() {
	SmartDashboard.putBoolean("Claw Closed", isClawClosed());
	SmartDashboard.putNumber("Roller Voltage", getRollerVoltage());
	SmartDashboard.putNumber("Roller Current", getRollerCurrent());
    }

}
