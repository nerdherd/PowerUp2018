package com.team687.frc2018.commands.drive;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.DriveConstants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Turn in place for a specified time
 */

public class TurnTime extends Command {

    private double m_rotPower;
    private double m_timeout;
    private double m_startTime;

    /**
     * @param straightPower
     * @param timeout
     */
    public TurnTime(double rotPower, double timeout) {
	m_rotPower = rotPower;
	m_timeout = timeout;

	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "DriveTime");
	m_startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
	Robot.drive.setPower(-(DriveConstants.kLeftAdjustment * m_rotPower), m_rotPower);
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
