package com.team687.frc2018.commands.drive;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.DriveConstants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive at a specified heading (turns to an angle near the beginning of a
 * specified distance). Loop is closed on heading but not on straight power.
 */

public class DriveAtHeading extends Command {

    private double m_straightPower;
    private double m_heading, m_distance;
    private double m_kRotP;

    /**
     * @param straightPower
     *            (determines direction and magnitude)
     * @param heading
     * @param distance
     *            (absolute value)
     * @param kRotP
     */
    public DriveAtHeading(double straightPower, double heading, double distance, double kRotP) {
	m_straightPower = straightPower;
	m_heading = heading;
	m_distance = distance;
	m_kRotP = kRotP;

	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "DriveAtHeading");
    }

    @Override
    protected void execute() {
	double yaw = Robot.drive.getCurrentYaw();
	if (m_straightPower < 0) {
	    yaw += 180;
	}
	double robotAngle = (360 - yaw) % 360;
	double rotError = -m_heading - robotAngle;
	rotError = (rotError > 180) ? rotError - 360 : rotError;
	rotError = (rotError < -180) ? rotError + 360 : rotError;
	double rotPower = m_kRotP * rotError;

	Robot.drive.setPower((DriveConstants.kLeftAdjustment * m_straightPower) - rotPower, m_straightPower + rotPower);
    }

    @Override
    protected boolean isFinished() {
	return Math.abs(Robot.drive.getDrivetrainPosition()) >= m_distance;
    }

    @Override
    protected void end() {
	Robot.drive.stopDrive();
    }

    @Override
    protected void interrupted() {
	end();
    }

}