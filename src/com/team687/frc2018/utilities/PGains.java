package com.team687.frc2018.utilities;

/**
 * A set of values needed to create a simple but robust proportional control
 * loop.
 */

public class PGains {

    private double m_kP, m_minPower, m_maxPower;

    /**
     * @param kP
     * @param minPower
     * @param maxPower
     */
    public PGains(double kP, double minPower, double maxPower) {
	m_kP = kP;
	m_minPower = minPower;
	m_maxPower = maxPower;
    }

    public void setP(double kP) {
	m_kP = kP;
    }

    public void setMinPower(double minPower) {
	m_minPower = minPower;
    }

    public void setMaxPower(double maxPower) {
	m_maxPower = maxPower;
    }

    public double getP() {
	return m_kP;
    }

    public double getMinPower() {
	return m_minPower;
    }

    public double getMaxPower() {
	return m_maxPower;
    }

}
