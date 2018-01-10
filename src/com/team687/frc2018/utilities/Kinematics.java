package com.team687.frc2018.utilities;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.DriveConstants;

/**
 * Skid steer drive kinematic calculations
 */

public class Kinematics {

    /**
     * Calculates new X by finding delta X and adding it to the original X
     * 
     * @param origPose
     * @param rightVelocity
     * @param leftVelocity
     * @param deltaTime
     */
    public static double getNewX(Pose origPose, double rightVelocity, double leftVelocity, double deltaTime) {
	double angularVelocity = getAngularVelocity(rightVelocity, leftVelocity);
	double arcRadius = getCurvatureRadius(rightVelocity, leftVelocity);
	double newX;
	if (rightVelocity != leftVelocity) {
	    newX = (arcRadius * Math.sin(angularVelocity * deltaTime)) + origPose.getX();
	} else {
	    newX = (Math.cos(origPose.getTheta()) * rightVelocity * deltaTime) + origPose.getX();
	}
	return newX;
    }

    /**
     * Calculates new Y by finding delta Y and adding it to the original Y
     * 
     * @param origPose
     * @param rightVelocity
     * @param leftVelocity
     * @param deltaTime
     */
    public static double getNewY(Pose origPose, double rightVelocity, double leftVelocity, double deltaTime) {
	double angularVelocity = getAngularVelocity(rightVelocity, leftVelocity);
	double arcRadius = getCurvatureRadius(rightVelocity, leftVelocity);
	double newY;
	if (rightVelocity != leftVelocity) {
	    newY = (-arcRadius * Math.cos(angularVelocity * deltaTime) + arcRadius) + origPose.getY();
	} else {
	    newY = (Math.sin(origPose.getTheta()) * rightVelocity * deltaTime) + origPose.getY();
	}
	return newY;
    }

    /**
     * @param origPose
     * @param rightVelocity
     * @param leftVelocity
     * @param deltaTime
     */
    public static double getNewTheta(Pose origPose, double rightVelocity, double leftVelocity, double deltaTime) {
	double angularVelocity = getAngularVelocity(rightVelocity, leftVelocity);
	return origPose.getTheta() + (angularVelocity * deltaTime);
    }

    /**
     * Returns new pose of robot. Theta is based on gyro yaw (keep in mind this
     * might throw null pointer exception when doing unit tests).
     * 
     * @param origPose
     * @param rightVelocity
     * @param leftVelocity
     * @param deltaTime
     */
    public static Pose getNewPose(Pose origPose, double rightVelocity, double leftVelocity, double deltaTime) {
	Pose deltaPose = new Pose(getNewX(origPose, rightVelocity, leftVelocity, deltaTime),
		getNewY(origPose, rightVelocity, leftVelocity, deltaTime),
		getNewTheta(origPose, rightVelocity, leftVelocity, deltaTime));
	Pose newPose = new Pose(deltaPose.getX() + origPose.getX(), deltaPose.getY() + origPose.getX(),
		Robot.drive.getCurrentYawRadians());
	return newPose;
    }

    /**
     * @param rightVelocity
     * @param leftVelocity
     */
    public static double getAngularVelocity(double rightVelocity, double leftVelocity) {
	double diffVelocity = rightVelocity - leftVelocity;
	return diffVelocity / DriveConstants.kDrivetrainWidth;
    }

    /**
     * @param rightVelocity
     * @param leftVelocity
     */
    public static double getCurvatureRadius(double rightVelocity, double leftVelocity) {
	double diffVelocity = rightVelocity - leftVelocity;
	double sigmaVelocity = rightVelocity + leftVelocity;
	if (diffVelocity == 0) {
	    return Double.POSITIVE_INFINITY;
	} else {
	    return (DriveConstants.kDrivetrainWidth * sigmaVelocity) / (2 * diffVelocity);
	}
    }

    /**
     * @param rightVelocity
     * @param leftVelocity
     * @param deltaTime
     */
    public static double getDerivedDeltaYaw(double rightVelocity, double leftVelocity, double deltaTime) {
	double diffVelocity = rightVelocity - leftVelocity;
	return diffVelocity * deltaTime / DriveConstants.kDrivetrainWidth;
    }

}
