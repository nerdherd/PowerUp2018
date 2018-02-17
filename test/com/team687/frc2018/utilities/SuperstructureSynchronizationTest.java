package com.team687.frc2018.utilities;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.team687.frc2018.constants.SuperstructureConstants;

public class SuperstructureSynchronizationTest {

    private static final double kEpsilon = 1E-1;

    @Test
    public void testArmCoordinates() {
	assertEquals(getX(-52), 30.24, kEpsilon);
	assertEquals(getY(-52), 9.44, kEpsilon);
	assertEquals(getX(-34), 38.99, kEpsilon);
	assertEquals(getY(-34), 18.82, kEpsilon);
	assertEquals(getX(-22), 43.01, kEpsilon);
	assertEquals(getY(-22), 26.39, kEpsilon);
	assertEquals(getX(45), 33.99, kEpsilon);
	assertEquals(getY(45), 70.74, kEpsilon);
	assertEquals(getX(89), 5.72, kEpsilon);
	assertEquals(getY(89), 82.74, kEpsilon);
    }

    @Test
    public void testDesiredWristAngles() {
	assertEquals(getDesiredWristAngle(-52), 74.0, kEpsilon);
	assertEquals(getDesiredWristAngle(-31), 75.9, kEpsilon);
	assertEquals(getDesiredWristAngle(-11), 90.7, kEpsilon);
	assertEquals(getDesiredWristAngle(10), 91.1, kEpsilon);
	assertEquals(getDesiredWristAngle(44), 58.3, kEpsilon);
	assertEquals(getDesiredWristAngle(62), 30.2, kEpsilon);
	assertEquals(getDesiredWristAngle(87), 15.4, kEpsilon);
    }

    public double getDesiredWristAngle(double armAngle) {
	double _r3 = SuperstructureConstants.kWristPivotToTip;
	double theta2 = armAngle;
	double x2 = getX(armAngle);
	double y2 = getY(armAngle);
	double _theta3_offset = -16;
	if (theta2 <= -33) {
	    return 74;
	} else if (theta2 <= 43) {
	    return NerdyMath.radiansToDegrees(Math.acos((45 - x2) / _r3)); // DEGREES(ACOS((45-[@x2])/_r3))-theta3_offset
	} else if (theta2 <= 46) {
	    return -1.75 * theta2 + 135.3; // -1.75*[@theta2]+135.3-theta3_offset
	} else {
	    return NerdyMath.radiansToDegrees(Math.asin((88 - y2) / _r3)); // DEGREES(ASIN((88-[@y2])/_r3))-theta3_offset
	}
    }

    // aliasing
    public double _x1 = SuperstructureConstants.kShoulderPivotX;
    public double _y1 = SuperstructureConstants.kShoulderPivotY;
    public double _r2 = SuperstructureConstants.kShoulderToWristPivot;

    public double getX(double armAngle) {
	return _x1 + _r2 * Math.cos(NerdyMath.degreesToRadians(armAngle));
    }

    public double getY(double armAngle) {
	return _y1 + _r2 * Math.sin(NerdyMath.degreesToRadians(armAngle));
    }

}
