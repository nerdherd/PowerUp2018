package com.team687.frc2018.utilities;

/**
 * A loggable data parameter
 */

public class CSVDatum {

    private String m_name;

    private double m_value;

    public CSVDatum(String name) {
	m_name = name;

	m_value = 0;
    }

    public String getName() {
	return m_name;
    }

    public void updateValue(double value) {
	m_value = value;
    }

    public double getValue() {
	return m_value;
    }

}
