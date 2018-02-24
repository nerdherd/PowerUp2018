package com.team687.frc2018.subsystems;

import com.team687.frc2018.commands.superstructure.SuperstructureTest;
import com.team687.frc2018.constants.SuperstructureConstants;

public class WristSimulation {

    private double m_position;

    public WristSimulation() {
	m_position = SuperstructureConstants.kWristIntakePos;
    }

    public void setPosition(double position) {
	if (position > SuperstructureConstants.kWristForwardSoftLimit) {
	    position = SuperstructureConstants.kWristForwardSoftLimit;
	}
	if (position < SuperstructureConstants.kWristReverseSoftLimit) {
	    position = SuperstructureConstants.kWristReverseSoftLimit;
	}
	if (SuperstructureTest.m_armSimulation.getPosition() < SuperstructureConstants.kArmWristSafePos) {
	    position = Math.max(position, SuperstructureConstants.kWristIntakePos);
	}
	m_position = position;
    }

    public double getPosition() {
	return m_position;
    }

    public boolean isWristSafe() {
	return getPosition() <= SuperstructureConstants.kWristStowPos
		&& getPosition() >= SuperstructureConstants.kWristIntakePos;
    }

}
