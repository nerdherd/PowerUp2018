package com.team687.frc2018.utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Linear interpolation / extrapolation unit testing
 */

@RunWith(Parameterized.class)
public class LinearInterpolatorTest {

    private final static double[] InputValues1 = { 0, 1, 2, 3, 4, 5, 6, 7 };
    private final static double[] OutputValues1 = { 0, 1, 2, 3, 4, 5, 6, 7 };

    private final static double[] InputValues2 = { 0, 1, 2, 3, 4, 5, 6, 7 };
    private final static double[] OutputValues2 = { 0, 2, 4, 6, 8, 10, 12, 14 };

    private final static double[] InputValues3 = { 5, 7, 9, 11, 13 };
    private final static double[] OutputValues3 = { 15, 21, 27, 33, 39 };

    private final static double[] InputValues4 = { 3, 6, 1, 4, 5, 2, 3 };
    private final static double[] OutputValues4 = { 1, 6, 2, 6, 2, 7, 3 };

    private final static double[] InputValues5 = { 1690, 971, 1114, 987, 973, 1241, 2056 };
    private final static double[] OutputValues5 = { 6, 2, 3, 2.54, 2.33, 3.3, 6.7 };

    @Parameters
    public static Collection<Object[]> testCases() {
	return Arrays.asList(new Object[][] { { InputValues1, OutputValues1 }, { InputValues2, OutputValues2 },
		{ InputValues3, OutputValues3 }, { InputValues4, OutputValues4 }, { InputValues5, OutputValues5 } });
    }

    private double[] m_sortedInputValues;
    private double[] m_sortedOutputValues;
    private LinearInterpolator m_estimator;

    public LinearInterpolatorTest(double[] inputValues, double[] outputValues) {
	m_estimator = new LinearInterpolator(inputValues, outputValues);
	m_sortedInputValues = m_estimator.getSortedKeys();
	m_sortedOutputValues = m_estimator.getSortedValues();
    }

    private static final double kEpsilon = 1E-9;

    @Test
    public void testSort() {
	for (int i = 0; i < m_estimator.getMaxLength() - 2; i++) {
	    assertTrue(m_sortedInputValues[i] <= m_sortedInputValues[i + 1]);
	    assertTrue(m_sortedOutputValues[i] <= m_sortedOutputValues[i + 1]);
	}
    }

    @Test
    public void testDifferentLengths() {
	double[] inputValues = { 33, 67, 118, 148 };
	double[] outputValues = { 254, 330, 469, 971, 973, 987 };

	LinearInterpolator estimator = new LinearInterpolator(inputValues, outputValues);
	double[] sortedInputValues = estimator.getSortedKeys();
	double[] sortedOutputValues = estimator.getSortedValues();

	Assert.assertNotNull(sortedInputValues[estimator.getMaxLength() - 1]);
	Assert.assertNotNull(sortedOutputValues[estimator.getMaxLength() - 1]);
    }

    @Test
    public void testLowerBound() {
	double output = m_estimator.estimate(-100);
	assertEquals(output, m_sortedOutputValues[0], kEpsilon);
    }

    @Test
    public void testInterpolatedSlope() {
	for (int i = 0; i < m_estimator.getMaxLength() - 2; i++) {
	    double input = m_sortedInputValues[i] + (m_sortedInputValues[i + 1] - m_sortedInputValues[i]) / 3;
	    double output = m_estimator.estimate(input);
	    double expectedSlope = (m_sortedOutputValues[i + 1] - m_sortedOutputValues[i])
		    / (m_sortedInputValues[i + 1] - m_sortedInputValues[i]);
	    double estimatedSlope = (m_sortedOutputValues[i + 1] - output) / (m_sortedInputValues[i + 1] - input);
	    assertEquals(expectedSlope, estimatedSlope, kEpsilon);
	}
    }

    @Test
    public void testExtrapolatedSlope() {
	double input = 3 * m_sortedInputValues[m_estimator.getMaxLength() - 1] / 2;
	double output = m_estimator.estimate(input);
	double expectedSlope = (m_sortedOutputValues[m_estimator.getMaxLength() - 1] - m_sortedOutputValues[0])
		/ (m_sortedInputValues[m_estimator.getMaxLength() - 1] - m_sortedInputValues[0]);
	double estimatedSlope = (m_sortedOutputValues[m_estimator.getMaxLength() - 1] - output)
		/ (m_sortedInputValues[m_estimator.getMaxLength() - 1] - input);
	assertEquals(expectedSlope, estimatedSlope, kEpsilon);
    }
}
