package com.team687.frc2018.utilities;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Skid steer forward kinematics unit testing
 */

@RunWith(Parameterized.class)
public class KinematicsTest {

    private static final double kEpsilon = 1E-9;

    @SuppressWarnings("rawtypes")
    @Parameters
    public static Collection testCases() {
	// consists of {x, y, theta (in radians), left speed, right speed, and dT
	return Arrays.asList(new double[][] { { 10, 10, 0.687 * Math.PI, 120, 90, 0.02 },
		{ 10, 10, 0.687 * Math.PI, 120, 90, 0 }, { 10, 10, 0, 120, 119.99, 0.02 } });
    }

    private double m_x;
    private double m_y;
    private double m_theta;
    private double m_leftSpeed;
    private double m_rightSpeed;

    private double m_angularVelocity;
    private double m_radius;
    private double m_dt; // in seconds
    private double m_time;

    public KinematicsTest(double[] rawVal) {
	m_x = rawVal[0];
	m_y = rawVal[1];
	m_theta = rawVal[2];
	m_leftSpeed = rawVal[3];
	m_rightSpeed = rawVal[4];
	m_dt = rawVal[5];

	m_angularVelocity = Kinematics.getAngularVelocity(m_rightSpeed, m_leftSpeed);
	m_radius = Kinematics.getCurvatureRadius(m_rightSpeed, m_leftSpeed);
	System.out.println();
	System.out.println("Angular Velocity: " + m_angularVelocity);
	System.out.println("Radius: " + m_radius);
    }

    @Test
    public void poseTransformationTest() {
	// these transformations assume that the robot isn't going perfectly straight
	double[][] origPos = { { 1, 0, 0, m_x }, { 0, 1, 0, m_y }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
	double[][] currentPose = { { Math.cos(m_theta), -Math.sin(m_theta), 0, 0 },
		{ Math.sin(m_theta), Math.cos(m_theta), 0, 0 }, { 0, 0, 1, m_theta }, { 0, 0, 0, 1 } };
	// instantaneous center of curvature
	double[][] ICC = { { 1, 0, 0, 0 }, { 0, 1, 0, m_radius }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
	double[][] rotateICC = { { Math.cos(m_angularVelocity * m_dt), -Math.sin(m_angularVelocity * m_dt), 0, 0 },
		{ Math.sin(m_angularVelocity * m_dt), Math.cos(m_angularVelocity * m_dt), 0, 0 },
		{ 0, 0, 1, m_angularVelocity * m_dt }, { 0, 0, 0, 1 } };
	double[][] translateOut = { { 1, 0, 0, 0 }, { 0, 1, 0, -m_radius }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };

	Matrix RT_P = new Matrix(origPos);
	Matrix PT_P = new Matrix(currentPose);
	Matrix PT_ICC = new Matrix(ICC);
	Matrix ICCT_ICC = new Matrix(rotateICC);
	Matrix ICCT_N = new Matrix(translateOut);
	System.out.println("RT_P");
	RT_P.show();
	System.out.println("PT_P");
	PT_P.show();
	System.out.println("PT_ICC");
	PT_ICC.show();
	System.out.println("ICCT_ICC");
	ICCT_ICC.show();
	System.out.println("ICCT_N");
	ICCT_N.show();

	// find new pose
	Matrix B = RT_P.multiplyBy(PT_P);
	Matrix C = B.multiplyBy(PT_ICC);
	Matrix D = C.multiplyBy(ICCT_ICC);
	Matrix RT_N = D.multiplyBy(ICCT_N);
	System.out.println("RT_N");
	RT_N.show();

	Pose lastPose = new Pose(m_x, m_y, m_theta);
	Pose newPose = new Pose(Kinematics.getNewX(lastPose, m_rightSpeed, m_leftSpeed, m_dt),
		Kinematics.getNewY(lastPose, m_rightSpeed, m_leftSpeed, m_dt),
		Kinematics.getNewTheta(lastPose, m_rightSpeed, m_leftSpeed, m_dt));

	double newX = (m_radius * Math.cos(lastPose.getTheta()) * Math.sin(m_angularVelocity * m_dt))
		+ (m_radius * Math.sin(lastPose.getTheta()) * Math.cos(m_angularVelocity * m_dt)) + lastPose.getX()
		- (m_radius * Math.sin(lastPose.getTheta()));
	System.out.println("New X: " + newX);

	double newY = (m_radius * Math.sin(lastPose.getTheta()) * Math.sin(m_angularVelocity * m_dt))
		- (m_radius * Math.cos(lastPose.getTheta()) * Math.cos(m_angularVelocity * m_dt)) + lastPose.getY()
		+ (m_radius * Math.cos(lastPose.getTheta()));
	System.out.println("New Y: " + newY);

	double newTheta = newPose.getTheta();
	System.out.println("New Theta: " + newTheta);

	// these calculations assume that the instantaneous velocities of the two sides
	// of the drive are not equal
	if (Double.isFinite(m_radius)) {
	    assertEquals(RT_N.getData()[0][3], newX, kEpsilon);
	    assertEquals(RT_N.getData()[1][3], newY, kEpsilon);
	    assertEquals(RT_N.getData()[2][3], newTheta, kEpsilon);
	}
    }

    @Test
    public void findNewPoseTest() {
	Pose lastPose = new Pose(m_x, m_y, m_theta);
	Pose newPose = new Pose(Kinematics.getNewX(lastPose, m_rightSpeed, m_leftSpeed, m_dt),
		Kinematics.getNewY(lastPose, m_rightSpeed, m_leftSpeed, m_dt),
		Kinematics.getNewTheta(lastPose, m_rightSpeed, m_leftSpeed, m_dt));

	Pose deltaPose = new Pose(m_radius * Math.sin(m_angularVelocity * m_dt),
		-m_radius * Math.cos(m_angularVelocity * m_dt) + m_radius, newPose.getTheta());
	double[][] RTC_data = { { 1, 0, 0, lastPose.getX() }, { 0, 1, 0, lastPose.getY() }, { 0, 0, 1, 0 },
		{ 0, 0, 0, 1 } };
	double[][] transform_data = { { 1, 0, 0, deltaPose.getX() }, { 0, 1, 0, deltaPose.getY() },
		{ 0, 0, 1, deltaPose.getTheta() }, { 0, 0, 0, 1 } };
	Matrix RTC = new Matrix(RTC_data);
	System.out.println("RTC");
	RTC.show();
	Matrix transform = new Matrix(transform_data);
	System.out.println("Transform");
	transform.show();
	Matrix checkingNewPose = RTC.multiplyBy(transform);
	System.out.println("Checking New Pose");
	checkingNewPose.show();
	Pose checkNewPose = new Pose(checkingNewPose.getData()[0][3], checkingNewPose.getData()[1][3],
		checkingNewPose.getData()[2][3]);

	if (Double.isFinite(m_radius)) {
	    assertEquals(newPose.getX(), checkNewPose.getX(), kEpsilon);
	    assertEquals(newPose.getY(), checkNewPose.getY(), kEpsilon);
	    assertEquals(newPose.getTheta(), checkNewPose.getTheta(), kEpsilon);
	}
    }

}
