package com.team687.frc2018.utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test for normalizer
 */

@RunWith(Parameterized.class)
public class NerdyMathTest {

    private static final double kEpsilon = 1E-9;

    enum Type {
	NORMALIZE, THRESHOLD
    };

    @Parameters
    public static Collection<Object[]> testCases() {
	return Arrays.asList(new Object[][] { { Type.NORMALIZE, 0.971, 0.987, 1 }, { Type.NORMALIZE, 0.971, 1.678, 1 },
		{ Type.NORMALIZE, 1.678, 0.971, 1 }, { Type.NORMALIZE, -0.971, 1.678, 1 },
		{ Type.NORMALIZE, 0.971, -1.678, 1 }, { Type.NORMALIZE, 1.678, 2.54, 1 },
		{ Type.NORMALIZE, 2.54, 1.678, 1 }, { Type.NORMALIZE, 687, 254, 1 },
		{ Type.THRESHOLD, -0.971, 0.254, 0.987 }, { Type.THRESHOLD, -1.114, 0.254, 0.987 },
		{ Type.THRESHOLD, -0.118, 0.254, 0.987 }, { Type.THRESHOLD, 0.971, 0.254, 0.987 },
		{ Type.THRESHOLD, 0.118, 0.254, 0.987 }, { Type.THRESHOLD, 1.114, 0.254, 0.987 } });
    }

    private Type m_type;
    private double m_firstVal;
    private double m_secondVal;
    private double m_thirdVal;
    private double[] m_rawVal;

    public NerdyMathTest(Type type, double firstVal, double secondVal, double thirdVal) {
	m_type = type;
	m_firstVal = firstVal;
	m_secondVal = secondVal;
	m_thirdVal = thirdVal;
    }

    @Test
    public void normalizeTest() {
	Assume.assumeTrue(m_type == Type.NORMALIZE);
	m_rawVal = new double[] { m_firstVal, m_secondVal }; // represents left and right power
	double[] normalizedVal = NerdyMath.normalize(m_rawVal, false);
	for (int i = 0; i < normalizedVal.length - 1; i++) {
	    assertTrue(Math.abs(normalizedVal[i]) <= 1.0);
	    assertTrue(isNegative(m_rawVal[i]) == isNegative(normalizedVal[i]));
	}
	assertEquals((m_rawVal[0] / m_rawVal[1]), (normalizedVal[0] / normalizedVal[1]), kEpsilon);
    }

    @Test
    public void thresholdTest() {
	Assume.assumeTrue(m_type == Type.THRESHOLD);
	double value = m_firstVal;
	double minimum = m_secondVal;
	double maximum = m_thirdVal;
	double thresholdedVal = NerdyMath.threshold(value, minimum, maximum);
	assertTrue(minimum <= Math.abs(thresholdedVal) && Math.abs(thresholdedVal) <= maximum);
    }

    public static boolean isNegative(double d) {
	return Double.doubleToRawLongBits(d) < 0;
    }

}
