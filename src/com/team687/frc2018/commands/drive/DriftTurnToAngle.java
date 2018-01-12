package com.team687.frc2018.commands.drive;

import com.team687.frc2018.Robot;
import com.team687.frc2018.utilities.PGains;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * An arc turn that drift turns to an angle near the end of a specified
 * distance. Similar to the logic behind 973's hopper auto controller. Loop is
 * closed on heading but not on straight power.
 */

public class DriftTurnToAngle extends Command {

    private double m_straightPower;
    private double m_desiredAngle;
    private double m_distance;
    private boolean m_isHighGear;
    private PGains m_rotPGains;

    /**
     * @param straightPower
     * @param angle
     * @param distance
     *            (absolute value)
     * @param isHighGear
     * @param kRotP
     */
    public DriftTurnToAngle(double straightPower, double angle, double distance, boolean isHighGear, double kRotP) {
	m_straightPower = straightPower;
	m_desiredAngle = angle;
	m_distance = distance;
	m_isHighGear = isHighGear;
	m_rotPGains.setP(kRotP);

	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "DriftTurnToAngle");

	if (m_isHighGear) {
	    Robot.drive.shiftUp();
	} else if (!m_isHighGear) {
	    Robot.drive.shiftDown();
	}
    }

    @Override
    protected void execute() {
	double doneness = Math.abs(Robot.drive.getDrivetrainPosition() / m_distance);
	// doneness = Math.sqrt(doneness); // this makes the arc turn more gradual

	double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
	double rotError = -m_desiredAngle - robotAngle;
	rotError = (rotError > 180) ? rotError - 360 : rotError;
	rotError = (rotError < -180) ? rotError + 360 : rotError;
	double rotPower = m_rotPGains.getP() * rotError * doneness;

	Robot.drive.setPower(rotPower + m_straightPower, rotPower - m_straightPower);
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
