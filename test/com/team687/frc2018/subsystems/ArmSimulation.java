package com.team687.frc2018.subsystems;

import com.team687.frc2018.commands.superstructure.SuperstructureTest;
import com.team687.frc2018.constants.SuperstructureConstants;

public class ArmSimulation {

    private double m_position;

    public ArmSimulation() {
	m_position = SuperstructureConstants.kArmDownPos;
    }

    public void setPosition(double position) {
	position = Math.min(SuperstructureConstants.kArmScaleTopPos,
		Math.max(position, SuperstructureConstants.kArmDownPos));
	if (!SuperstructureTest.m_wristSimulation.isWristSafe()) {
	    position = Math.min(position, SuperstructureConstants.kArmWristSafePos);
	}
	m_position = position;
    }

    public double getPosition() {
	return m_position;
    }

}
