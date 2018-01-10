package com.team687.frc2018.utilities;

import java.util.ArrayList;

import com.team687.frc2018.constants.DriveConstants;

/**
 * Trapezoidal motion profile generator
 */

public class MotionProfile {

    private double m_cruiseVelocity;
    private double m_accel;
    private double m_decel;

    private double m_clkInMinutes = DriveConstants.kDtInMinutes;

    private ArrayList<Double> m_timeProfile = new ArrayList<Double>();
    private ArrayList<Double> m_velocityProfile = new ArrayList<Double>();
    private ArrayList<Double> m_positionProfile = new ArrayList<Double>();
    private ArrayList<Double> m_accelerationProfile = new ArrayList<Double>();

    private double m_time;
    private double m_expectedTotalTime;
    private double m_accelTime;
    private double m_cruiseTime;
    private double m_decelTime;

    /**
     * Motion Profile Generator
     * 
     * @param cruise
     *            velocity in RPM
     * @param acceleration
     *            in R/M^2
     * @param deceleration
     *            in R/M^2
     */
    public MotionProfile(double cruiseVelocity, double accel, double decel) {
	m_cruiseVelocity = cruiseVelocity;
	m_accel = accel;
	m_decel = decel;
    }

    /**
     * Generate a trapezoidal motion profile using basic kinematic equations
     * 
     * @param distance
     *            desired in ticks or rotations
     */
    public void generateProfile(double distance) {
	double x;
	double v = 0;

	m_accelTime = m_cruiseVelocity / m_accel;
	// SmartDashboard.putNumber("Acceleration Time", accelTime);
	m_cruiseTime = (distance - (m_accelTime * m_cruiseVelocity)) / m_cruiseVelocity;
	// SmartDashboard.putNumber("Cruise Time", cruiseTime);
	double accelAndCruiseTime = m_accelTime + m_cruiseTime;
	// SmartDashboard.putNumber("Acceleration + Cruise Time", accelAndCruiseTime);
	m_decelTime = -m_cruiseVelocity / m_decel;
	// SmartDashboard.putNumber("Deceleration Time", decelTime);
	m_expectedTotalTime = m_accelTime + m_cruiseTime + m_decelTime;
	// SmartDashboard.putNumber("Expected End Time", + totalTime);

	for (m_time = 0; m_time < m_accelTime; m_time += m_clkInMinutes) {
	    x = (0.5 * m_accel * Math.pow(m_time, (double) 2));
	    v = m_accel * m_time;
	    addData(m_time, v, x, m_accel);
	}
	for (m_time = m_accelTime; m_time < accelAndCruiseTime; m_time += m_clkInMinutes) {
	    x = (0.5 * (Math.pow(m_cruiseVelocity, 2) / m_accel))
		    + (m_cruiseVelocity * (m_time - (m_cruiseVelocity / m_accel)));
	    v = (m_cruiseVelocity);
	    addData(m_time, v, x, 0);
	}
	for (m_time = accelAndCruiseTime; m_time <= m_expectedTotalTime; m_time += m_clkInMinutes) {
	    x = (double) (distance + 0.5 * m_decel * Math.pow((m_time - m_expectedTotalTime), 2));
	    v = -m_accel * m_time + (m_cruiseVelocity + m_accel * accelAndCruiseTime);
	    addData(m_time, v, x, m_decel);
	}
	// SmartDashboard.putNumber("Calculated Acutal End Time", m_time);
    }

    public int getTotalPoints() {
	return m_timeProfile.size();
    }

    /**
     * @return acceleration time in minutes
     */
    public double getAccelTime() {
	return m_accelTime;
    }

    /**
     * @return cruise time in minutes
     */
    public double getCruiseTime() {
	return m_cruiseTime;
    }

    /**
     * @return deceleration time in minutes
     */
    public double getDecelTime() {
	return m_decelTime;
    }

    /**
     * @return total time in minutes
     */
    public double getTotalTime() {
	return m_time;
    }

    /**
     * Add data to array lists
     * 
     * @param time
     *            index
     * @param velocity
     * @param distance
     * @param acceleration
     */
    private void addData(double time, double v, double x, double acceleration) {
	m_timeProfile.add(time);
	m_velocityProfile.add(v);
	m_positionProfile.add(x);
	m_accelerationProfile.add(acceleration);
    }

    /**
     * @param point
     * @return goal velocity
     */
    public double readVelocity(int point) {
	return m_velocityProfile.get(point);
    }

    /**
     * @param point
     * @return goal distance
     */
    public double readPosition(int point) {
	return m_positionProfile.get(point);
    }

    /**
     * @param point
     * @return goal acceleration
     */
    public double readAcceleration(int point) {
	return m_accelerationProfile.get(point);
    }
}
