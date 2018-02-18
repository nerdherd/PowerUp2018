package com.team687.frc2018.utilities;

import java.util.Arrays;

/**
 * Linear interpolation (and extrapolation if needed). Intended to use for
 * determining distance from the area of contour(s) from vision.
 * 
 * Negative numbers not supported.
 */

public class LinearInterpolator {

    private double[] m_keys;
    private double[] m_values;

    private int m_maxLength;

    public LinearInterpolator(double[] key, double[] value) {
	Arrays.sort(key);
	Arrays.sort(value);
	m_keys = key;
	m_values = value;
	m_maxLength = Math.min(m_keys.length, m_values.length);

	if (m_keys.length != m_values.length) {
	    System.out.println("(Linear Interpolation) Not the same number of keys and values!");
	}
    }

    public double estimate(double input) {
	double output = 0;

	// put a hard limit on the minimum value
	if (input < m_keys[0]) {
	    output = m_values[0];
	}
	// extrapolation (try to avoid this)
	if (input > m_keys[m_maxLength - 1]) {
	    double slope = (m_values[m_maxLength - 1] - m_values[0]) / (m_keys[m_maxLength - 1] - m_keys[0]);
	    double intercept = m_values[0] - slope * m_keys[0];
	    output = slope * input + intercept;
	}
	// interpolation
	for (int i = 0; i < m_maxLength - 2; i++) {
	    if (input > m_keys[i] && input < m_keys[i + 1]) {
		output = (m_values[i] * (m_keys[i + 1] - input) + m_values[i + 1] * (input - m_keys[i]))
			/ (m_keys[i + 1] - m_keys[i]);
	    }
	}
	return output;
    }

    public double[] getSortedKeys() {
	return m_keys;
    }

    public double[] getSortedValues() {
	return m_values;
    }

    public int getMaxLength() {
	return m_maxLength;
    }

}
