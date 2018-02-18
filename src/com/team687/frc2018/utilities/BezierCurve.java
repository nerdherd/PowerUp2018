package com.team687.frc2018.utilities;

import java.util.ArrayList;

import com.team687.frc2018.constants.DriveConstants;

/**
 * Bezier curve generation inspired by 1241
 */

public class BezierCurve {

    private double[] m_xVal = new double[4];
    private double[] m_yVal = new double[4];

    private ArrayList<Double> m_xPoints = new ArrayList<Double>();
    private ArrayList<Double> m_yPoints = new ArrayList<Double>();
    private ArrayList<Double> m_headingList = new ArrayList<Double>();
    private ArrayList<Double> m_arcLengthList = new ArrayList<Double>();
    private double m_distance;

    private double m_ax;
    private double m_bx;
    private double m_cx;
    private double m_ay;
    private double m_by;
    private double m_cy;

    public BezierCurve(double x0, double y0, double x1, double y1, double x2, double y2, double x3, double y3) {
	m_xVal[0] = x0;
	m_xVal[1] = x1;
	m_xVal[2] = x2;
	m_xVal[3] = x3;
	m_yVal[0] = y0;
	m_yVal[1] = y1;
	m_yVal[2] = y2;
	m_yVal[3] = y3;
    }

    public BezierCurve(double[] path) {
	m_xVal[0] = path[0];
	m_xVal[1] = path[2];
	m_xVal[2] = path[4];
	m_xVal[3] = path[6];
	m_yVal[0] = path[1];
	m_yVal[1] = path[3];
	m_yVal[2] = path[5];
	m_yVal[3] = path[7];
    }

    public void calculateParams() {
	m_cx = 3 * (m_xVal[1] - m_xVal[0]);
	m_bx = 3 * (m_xVal[2] - m_xVal[1]) - m_cx;
	m_ax = m_xVal[3] - m_xVal[0] - m_cx - m_bx;

	m_cy = 3 * (m_yVal[1] - m_yVal[0]);
	m_by = 3 * (m_yVal[2] - m_yVal[1]) - m_cy;
	m_ay = m_yVal[3] - m_yVal[0] - m_cy - m_by;
    }

    public double calculateX(double counter) {
	return m_ax * Math.pow(counter, 3) + m_bx * Math.pow(counter, 2) + m_cx * counter + m_xVal[0];
    }

    public double calculateY(double counter) {
	return m_ay * Math.pow(counter, 3) + m_by * Math.pow(counter, 2) + m_cy * counter + m_yVal[0];
    }

    public void calculatePoints() {
	m_xPoints.clear();
	m_yPoints.clear();
	for (double i = 0; i <= 1; i += (1 / DriveConstants.kBezierStep)) {
	    m_xPoints.add(calculateX(i));
	    m_yPoints.add(calculateY(i));
	}
	m_xPoints.add(m_xVal[3]);
	m_yPoints.add(m_yVal[3]);
    }

    public void calculateBezier() {
	calculateParams();
	calculatePoints();

	m_headingList.clear();
	m_arcLengthList.clear();
	for (int i = 0; i < m_xPoints.size() - 1; i++) {
	    double xDelta = m_xPoints.get(i + 1) - m_xPoints.get(i);
	    double yDelta = m_yPoints.get(i + 1) - m_yPoints.get(i);
	    double heading = 0;

	    if (xDelta == 0) {
		heading = (yDelta > 0) ? heading : 0.0;
		heading = (yDelta < 0) ? heading : 180.0;
	    } else if (yDelta == 0) {
		heading = (xDelta > 0) ? heading : 90.0;
		heading = (xDelta < 0) ? heading : -90.0;
	    } else {
		heading = Math.toDegrees(Math.atan2(xDelta, yDelta));
	    }

	    double hypotenuse = Math.sqrt(Math.pow(xDelta, 2) + Math.pow(yDelta, 2));
	    m_distance += hypotenuse;

	    m_headingList.add(i, heading);
	    m_arcLengthList.add(i, m_distance);
	}
    }

    public ArrayList<Double> getXPointList() {
	return m_xPoints;
    }

    public ArrayList<Double> getYPointList() {
	return m_yPoints;
    }

    public ArrayList<Double> getArcLengthList() {
	return m_arcLengthList;
    }

    public ArrayList<Double> getHeadingList() {
	return m_headingList;
    }

    public double[] getXParam() {
	return m_xVal;
    }

    public double[] getYParam() {
	return m_yVal;
    }

    public void clearAll() {
	m_xPoints.clear();
	m_yPoints.clear();
	m_arcLengthList.clear();
	m_headingList.clear();
    }
}
