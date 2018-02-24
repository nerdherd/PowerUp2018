package com.team687.frc2018.utilities;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SuperstructureSynchronizationTest {

    private static final double kEpsilon = 1E-1;

    @Test
    public void testDesiredWristAngles() {
	assertEquals(getDesiredWristAngle(-52), 90, kEpsilon);
	assertEquals(getDesiredWristAngle(-5), 90, kEpsilon);
	assertEquals(getDesiredWristAngle(10), 87, kEpsilon);
	assertEquals(getDesiredWristAngle(35), 51.8, kEpsilon);
	assertEquals(getDesiredWristAngle(47), 47, kEpsilon);
	assertEquals(getDesiredWristAngle(65), 18.7, kEpsilon);
	assertEquals(getDesiredWristAngle(90), 0, kEpsilon);
    }

    public double getDesiredWristAngle(double armAngle) {
	if (armAngle <= 0) {
	    return 90;
	} else if (armAngle <= 40) {
	    return NerdyMath
		    .radiansToDegrees(Math.acos(41 * (1 - Math.cos(NerdyMath.degreesToRadians(armAngle))) / 12));
	} else if (armAngle <= 50) {
	    return armAngle;
	} else {
	    return NerdyMath
		    .radiansToDegrees(Math.asin(41 * (1 - Math.sin(NerdyMath.degreesToRadians(armAngle))) / 12));
	}
    }

}
