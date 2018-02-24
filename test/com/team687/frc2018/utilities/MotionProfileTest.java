package com.team687.frc2018.utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.team687.frc2018.constants.DriveConstants;

/**
 * Tests for motion profile generation and integration
 */

public class MotionProfileTest {

    private static final double kEpsilon = 1E-9;

    private static double cruiseVelocity = 1241;
    private static double maxAccel = 51.56 * 3600;
    private static double maxDecel = -maxAccel;
    private static double targetDistance = 30.93;

    private double velocityIntegral = 0;
    private double accelerationIntegral = 0;
    private double cumulativePositionTolerance = 0.0971;
    private double cumulativeVelocityTolerance = 33;

    @Test
    public void generateProfileTest() {
	MotionProfile motionProfile = new MotionProfile(cruiseVelocity, maxAccel, maxDecel);
	motionProfile.generateProfile(targetDistance);
	double accelTime = cruiseVelocity / maxAccel;
	assertEquals(accelTime, motionProfile.getAccelTime(), kEpsilon);
	double cruiseTime = (targetDistance - (accelTime * cruiseVelocity)) / cruiseVelocity;
	assertEquals(cruiseTime, motionProfile.getCruiseTime(), kEpsilon);
	double decelTime = -cruiseVelocity / maxDecel;
	assertEquals(decelTime, motionProfile.getDecelTime(), kEpsilon);
	double totalTime = accelTime + cruiseTime + decelTime;
	assertEquals(totalTime, motionProfile.getTotalTime(), 0.001); // small tolerance of 0.001 minutes

	int totalIndex = (int) (totalTime / DriveConstants.kDtInMinutes) + 3; // accounts for overlapped times in for
									      // loops
	assertEquals(totalIndex, motionProfile.getTotalPoints(), kEpsilon);

	for (double time = 0; time < accelTime; time += DriveConstants.kDtInMinutes) {
	    int index = (int) (time / DriveConstants.kDtInMinutes);
	    double position = motionProfile.readPosition(index);
	    double velocity = motionProfile.readVelocity(index);
	    double acceleration = motionProfile.readAcceleration(index);

	    assertTrue(position < targetDistance);
	    assertTrue(velocity < cruiseVelocity);
	    assertEquals(acceleration, maxAccel, kEpsilon);
	    if (index > 0) {
		assertTrue(position > motionProfile.readPosition(index - 1));
		assertTrue(velocity > motionProfile.readVelocity(index - 1));

		accelerationIntegral += (acceleration + motionProfile.readAcceleration(index - 1)) / 2
			* DriveConstants.kDtInMinutes;
		assertEquals(accelerationIntegral, velocity, cumulativeVelocityTolerance);

		velocityIntegral += (velocity + motionProfile.readVelocity(index - 1)) / 2
			* DriveConstants.kDtInMinutes;
		assertEquals(velocityIntegral, position, cumulativePositionTolerance);
	    }
	}
	for (double time = accelTime; time < (accelTime + cruiseTime); time += DriveConstants.kDtInMinutes) {
	    int index = (int) (time / DriveConstants.kDtInMinutes) + 1;
	    double position = motionProfile.readPosition(index);
	    double velocity = motionProfile.readVelocity(index);
	    double acceleration = motionProfile.readAcceleration(index);

	    assertTrue(position < targetDistance);
	    assertEquals(cruiseVelocity, velocity, kEpsilon);
	    assertEquals(acceleration, 0, kEpsilon);
	    if (index > (int) (accelTime / DriveConstants.kDtInMinutes) + 1) {
		assertTrue(position > motionProfile.readPosition(index - 1));
		assertEquals(velocity, motionProfile.readVelocity(index - 1), kEpsilon);

		accelerationIntegral += (acceleration + motionProfile.readAcceleration(index - 1)) / 2
			* DriveConstants.kDtInMinutes;
		assertEquals(accelerationIntegral, velocity, 2 * cumulativeVelocityTolerance);

		velocityIntegral += (velocity + motionProfile.readVelocity(index - 1)) / 2
			* DriveConstants.kDtInMinutes;
		assertEquals(velocityIntegral, position, 2 * cumulativePositionTolerance);
	    }
	}
	for (double time = (accelTime + cruiseTime); time < totalTime; time += DriveConstants.kDtInMinutes) {
	    int index = (int) (time / DriveConstants.kDtInMinutes) + 2;
	    double position = motionProfile.readPosition(index);
	    double velocity = motionProfile.readVelocity(index);
	    double acceleration = motionProfile.readAcceleration(index);

	    assertTrue(position < targetDistance);
	    assertTrue(velocity <= cruiseVelocity);
	    assertEquals(acceleration, maxDecel, kEpsilon);
	    if (index > ((int) ((accelTime + cruiseTime) / DriveConstants.kDtInMinutes)) + 2) {
		assertTrue(position > motionProfile.readPosition(index - 1));
		assertTrue(velocity < motionProfile.readVelocity(index - 1));

		accelerationIntegral += (acceleration + motionProfile.readAcceleration(index - 1)) / 2
			* DriveConstants.kDtInMinutes;
		assertEquals(accelerationIntegral, velocity, 3 * cumulativeVelocityTolerance);

		velocityIntegral += (velocity + motionProfile.readVelocity(index - 1)) / 2
			* DriveConstants.kDtInMinutes;
		assertEquals(velocityIntegral, position, 3 * cumulativePositionTolerance);
	    }
	}
    }

    @Test
    public void timeConversionTest() {
	double timestampInSeconds = 6.87;
	double timestampInMinutes = timestampInSeconds / 60;
	int indexForSeconds = (int) (timestampInSeconds / DriveConstants.kDt);
	int indexForMinutes = (int) (timestampInMinutes / DriveConstants.kDtInMinutes);
	assertEquals(indexForSeconds, indexForMinutes, kEpsilon);
    }

}
