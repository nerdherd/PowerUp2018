package com.team687.frc2018.commands.drive;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Turn in place for a specified time
 */

public class TurnTime extends Command {

    private double m_rotPower;
    private double m_timeout;
    private boolean m_isHighGear;
    private double m_startTime;

    /**
     * @param straightPower
     * @param timeout
     * @param isHighGear
     */
    public TurnTime(double rotPower, double timeout, boolean isHighGear) {
	m_rotPower = rotPower;
	m_timeout = timeout;
	m_isHighGear = isHighGear;

	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "DriveTime");
	m_startTime = Timer.getFPGATimestamp();

	if (m_isHighGear) {
	    Robot.drive.shiftUp();
	} else if (!m_isHighGear) {
	    Robot.drive.shiftDown();
	}
    }

    @Override
    protected void execute() {
	Robot.drive.setPower(m_rotPower, m_rotPower);
    }

    @Override
    protected boolean isFinished() {
	return Timer.getFPGATimestamp() - m_startTime > m_timeout;
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
