package com.team687.frc2018.utilities;

/**
 * PID Controller
 */

public class NerdyPID {

    // Constants
    private double m_kP;
    private double m_kI;
    private double m_kD;

    private double m_minimumOutput = -1;
    private double m_maximumOutput = 1;

    private double m_error = 0;
    private double m_lastError = 0;
    private double m_totalError = 0;
    private double m_desired = 0;
    private double m_result = 0;
    private double m_lastInput = Double.NaN;

    private boolean m_isGyro = false;

    /**
     * Default Constructor. Need to call the setPID method after this. Default PID
     * values are all 0.
     */
    public NerdyPID() {
	setPID(0, 0, 0);
    }

    /**
     * Constructs a PID controller with the set PID Values
     * 
     * @param p
     * @param i
     * @param d
     */
    public NerdyPID(double p, double i, double d) {
	setPID(p, i, d);
    }

    /**
     * The magical calculation
     * 
     * @param input
     *            The sensor input
     * @return The magical output
     */
    public double calculate(double input) {
	m_lastInput = input;
	m_lastError = m_error;
	m_error = m_desired - input;
	if (m_isGyro) {
	    m_error = (m_error > 180) ? m_error - 360 : m_error;
	    m_error = (m_error < -180) ? m_error + 360 : m_error;
	}
	m_totalError += m_error;

	m_result = (m_kP * m_error) + (m_kI * m_totalError) - (m_kD * (m_error - m_lastError));

	boolean isNegative = m_result < 0;
	if (Math.abs(m_result) > m_maximumOutput && !isNegative) {
	    m_result = m_maximumOutput;
	} else if (Math.abs(m_result) > m_maximumOutput && isNegative) {
	    m_result = -m_maximumOutput;
	}
	if (Math.abs(m_result) < m_minimumOutput && !isNegative) {
	    m_result = m_minimumOutput;
	} else if (Math.abs(m_result) < m_minimumOutput && isNegative) {
	    m_result = -m_minimumOutput;
	}

	return m_result;
    }

    /**
     * Sets the PID Values
     * 
     * @param p
     * @param i
     * @param d
     */
    public void setPID(double p, double i, double d) {
	m_kP = p;
	m_kI = i;
	m_kD = d;
    }

    /**
     * Sets the target value
     * 
     * @param desired
     */
    public void setDesired(double desired) {
	m_desired = desired;
    }

    /**
     * @return target value
     */
    public double getDesired() {
	return m_desired;
    }

    /**
     * Sets absolute value output range
     * 
     * @param minimum
     * @param maximum
     */
    public void setOutputRange(double minimum, double maximum) {
	m_minimumOutput = minimum;
	m_maximumOutput = maximum;
    }

    /**
     * Gets the output of the controller
     * 
     * @return The last calculated output.
     */
    public double getResult() {
	return m_result;
    }

    /**
     * @return last error
     */
    public double getError() {
	return m_error;
    }

    /**
     * Return true if the error is within the tolerance
     *
     * @return true if the error is less than the tolerance
     */
    public boolean onTarget(double tolerance) {
	return m_lastInput != Double.NaN && Math.abs(m_lastInput - m_desired) < tolerance;
    }

    /**
     * Sets if the PID is for gyro
     * 
     * @param isGyro
     */
    public void setGyro(boolean isGyro) {
	m_isGyro = isGyro;
    }

    /**
     * Returns if the PID is for gyro
     */
    public boolean isGyro() {
	return m_isGyro;
    }
}
