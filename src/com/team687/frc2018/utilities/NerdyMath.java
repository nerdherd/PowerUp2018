package com.team687.frc2018.utilities;

import com.team687.frc2018.constants.DriveConstants;

/**
 * Useful math functions
 */

public class NerdyMath {

    public static double radiansToDegrees(double rads) {
	return rads * 180 / Math.PI;
    }

    public static double degreesToRadians(double deg) {
	return deg * Math.PI / 180;
    }

    public static double inchesToRotations(double inches) {
	return (int) (inches / (Math.PI * DriveConstants.kWheelDiameter));
    }

    public static int rotationsToTicks(double rotations) {
	return (int) (rotations * 4096);
    }

    public static int inchesToTicks(double inches) {
	return (int) (inches / Math.PI / DriveConstants.kWheelDiameter * 4096);
    }

    public static double ticksToRotations(int ticks) {
	return ticks / rotationsToTicks(1);
    }

    public static double rotationsToInches(double rotations) {
	return rotations / inchesToRotations(1);
    }

    public static double ticksToInches(int ticks) {
	return ticks / 4096 * 6 * Math.PI;
    }

    public static double inchesToFeet(double inches) {
	return inches / 12;
    }

    public static double feetToInches(double feet) {
	return feet * 12;
    }

    /**
     * Limits the given input to the given magnitude.
     * 
     * @param value
     * @param absolute
     *            value limit
     * @return thresholded value
     */
    public static double limit(double value, double limit) {
	return (Math.abs(value) < limit) ? value : limit * (value < 0 ? -1 : 1);
    }

    /**
     * Thresholds given input to given range
     * 
     * @param value
     * @param minimum
     *            (absolute value)
     * @param maximum
     *            (absolute value)
     * @return thresholded value
     */
    public static double threshold(double value, double minimum, double maximum) {
	double sign = Math.signum(value);
	if (Math.abs(value) < Math.abs(minimum)) {
	    value = minimum * sign;
	} else if (Math.abs(value) > Math.abs(maximum)) {
	    value = maximum * sign;
	}
	return value;
    }

    /**
     * Add joystick sensitivity (Ether method) If throttle is 0, joystick output is
     * linear. If throttle is 1, joystick output is cubic. You can adjust in
     * between.
     * 
     * @param input
     * @param throttle
     */
    public static double addSensitivity(double input, double throttle) {
	double b = DriveConstants.kJoystickDeadband;
	double a = throttle;
	double output = 0;
	if (input >= 0) {
	    output = b + (1 - b) * (a * Math.pow(input, 3) + (1 - a) * input);
	} else if (input < 0) {
	    output = -b + (1 - b) * (a * Math.pow(input, 3) + (1 - a) * input);
	}
	return output;
    }

    /**
     * Square input for sensitivity adjustment
     * 
     * @param input
     * @return scaled output
     */
    public static double squareInput(double input) {
    	if (input != 0) {
    		return Math.pow(input, 2) * (input / Math.abs(input));
    	} else {
    		return 0;
    	}
    }

    /**
     * Cube input for sensitivity adjustment
     * 
     * @param input
     * @return scaled output
     */
    public static double cubeInput(double input) {
	return Math.pow(input, 3);
    }

    /**
     * Handles when the joystick moves slightly when you actually don't want it to
     * move at all
     * 
     * @param value
     * @param deadband
     * @return value or 0 if within deadband
     */
    public static double handleDeadband(double val, double deadband) {
	return (Math.abs(val) > Math.abs(deadband)) ? val : 0.0;
    }

    /**
     * Normalizes the array to -1 or 1
     * 
     * @param values
     *            The values
     * @param scaleUp
     *            True makes the values scale no matter what
     * @return The normalized values
     */
    public static double[] normalize(double[] values, boolean scaleUp) {
	double[] normalizedValues = new double[values.length];
	double max = Math.max(Math.abs(values[0]), Math.abs(values[1]));
	for (int i = 2; i < values.length; i++) {
	    max = Math.max(Math.abs(values[i]), max);
	}
	if (max < 1 && scaleUp == false) {
	    for (int i = 0; i < values.length; i++) {
		normalizedValues[i] = values[i];
	    }
	} else {
	    for (int i = 0; i < values.length; i++) {
		normalizedValues[i] = values[i] / max;
	    }
	}

	return normalizedValues;
    }

    /**
     * Bound an angle (in degrees) to 360 degrees.
     */
    public static double boundAngle(double angleDegrees) {
	angleDegrees = (360 - angleDegrees) % 360;
	return angleDegrees;
    }

    /**
     * Bound angle error
     */
    public static double boundAngleError(double error) {
	error = (error > 180) ? error - 360 : error;
	error = (error < -180) ? error + 360 : error;
	return error;
    }

}