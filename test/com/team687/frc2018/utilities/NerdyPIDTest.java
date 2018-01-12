package com.team687.frc2018.utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * NerdyPID unit testing
 */

public class NerdyPIDTest {

    private static final double kEpsilon = 1E-9;

    @Test
    public void testGyroMode() {
	NerdyPID testRotPID = new NerdyPID(1, 0, 0);
	testRotPID.setDesired(0);
	assertEquals(0, testRotPID.getDesired(), kEpsilon);
	assertFalse(testRotPID.isGyro());

	testRotPID.calculate(-270);
	assertEquals(270, testRotPID.getError(), kEpsilon);

	testRotPID.setGyro(true);
	assertTrue(testRotPID.isGyro());

	testRotPID.calculate(-270);
	assertEquals(-90, testRotPID.getError(), kEpsilon);
    }

    @Test
    public void testOutputRange() {
	NerdyPID testPID = new NerdyPID(0.0687, 0, 0);
	testPID.setDesired(0);
	assertEquals(1.0, testPID.calculate(-687), kEpsilon);
	assertEquals(-1.0, testPID.calculate(687), kEpsilon);
	assertEquals(0, testPID.calculate(0), kEpsilon);

	testPID.setOutputRange(0.254, 0.971);
	assertEquals(0.971, testPID.calculate(-687), kEpsilon);
	assertEquals(-0.971, testPID.calculate(687), kEpsilon);
	assertEquals(0.254, testPID.calculate(-0.001), kEpsilon);
	assertEquals(-0.254, testPID.calculate(0.001), kEpsilon);
    }

}
