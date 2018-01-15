package com.team687.frc2018.commands.superstructure;

import org.junit.Test;

import com.team687.frc2018.constants.SuperstructureConstants;
import com.team687.frc2018.subsystems.ArmSimulation;
import com.team687.frc2018.subsystems.WristSimulation;

public class SuperstructureTest {

    public static ArmSimulation m_armSimulation = new ArmSimulation();
    public static WristSimulation m_wristSimulation = new WristSimulation();

    @Test
    public void stowSuperstructure() {
	System.out.println("StowSuperstructure");
	m_wristSimulation.setPosition(SuperstructureConstants.kWristStowPos);
	m_armSimulation.setPosition(SuperstructureConstants.kArmDownPos);
	System.out.println("Wrist Position: " + m_wristSimulation.getPosition());
	System.out.println("Arm Position: " + m_armSimulation.getPosition());
	System.out.println();
    }

    @Test
    public void setIntakePosition() {
	stowSuperstructure();
	System.out.println("SetIntakePosition");
	m_wristSimulation.setPosition(SuperstructureConstants.kWristIntakePos);
	System.out.println("Wrist Position: " + m_wristSimulation.getPosition());
	System.out.println("Arm Position: " + m_armSimulation.getPosition());
	System.out.println();
    }

    @Test
    public void setScalePositionForward() {
	stowSuperstructure();
	System.out.println("SetScalePositionForward");
	m_armSimulation.setPosition(SuperstructureConstants.kArmScaleMidPos);
	m_wristSimulation.setPosition(SuperstructureConstants.kWristScaleMidPos);
	System.out.println("Wrist Position: " + m_wristSimulation.getPosition());
	System.out.println("Arm Position: " + m_armSimulation.getPosition());
	System.out.println();
    }

    @Test
    public void setScalePositionBackward() {
	stowSuperstructure();
	System.out.println("SetScalePositionBackward");
	m_armSimulation.setPosition(SuperstructureConstants.kArmScaleBackwardPos);
	m_wristSimulation.setPosition(SuperstructureConstants.kWristScaleBackwardPos);
	System.out.println("Wrist Position: " + m_wristSimulation.getPosition());
	System.out.println("Arm Posiiton: " + m_armSimulation.getPosition());
    }

}
