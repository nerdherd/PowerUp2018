package com.team687.frc2018.commands.drive;

import com.team687.frc2018.Robot;
import com.team687.frc2018.constants.DriveConstants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive straight without setting power to 0 when it reaches goal. No heading
 * adjustment, all open loop.
 */

public class DriveStraightContinuous extends Command {

    private double m_distance;
    private double m_straightPower;

    /**
     * @param distance
     * @param straightPower
     */
    public DriveStraightContinuous(double distance, double straightPower) {
	m_distance = distance;
	m_straightPower = straightPower;

	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Drive Command", "DriveStraightContinuous");
    }

    @Override
    protected void execute() {
	Robot.drive.setPower((DriveConstants.kLeftAdjustment * m_straightPower), m_straightPower);
    }

    @Override
    protected boolean isFinished() {
	return Robot.drive.getDrivetrainPosition() > m_distance;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
	end();
    }

}
