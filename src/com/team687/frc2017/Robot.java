package com.team687.frc2017;

import com.team687.frc2017.subsystems.Drive;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends TimedRobot {

    public static Drive drive;
    public static PowerDistributionPanel pdp;
    public static Compressor compressor;
    public static OI m_oi;

    @Override
    public void robotInit() {
	pdp = new PowerDistributionPanel();
	compressor = new Compressor();
	compressor.start();

	drive = new Drive();
	drive.stopDrive();
	drive.resetEncoders();
	drive.resetGyro();

	m_oi = new OI();
    }

    @Override
    public void disabledInit() {
	Scheduler.getInstance().removeAll();
	drive.reportToSmartDashboard();
    }

    @Override
    public void disabledPeriodic() {
	Scheduler.getInstance().run();
	drive.reportToSmartDashboard();
    }

    @Override
    public void autonomousInit() {
	Scheduler.getInstance().removeAll();
	drive.reportToSmartDashboard();
    }

    @Override
    public void autonomousPeriodic() {
	Scheduler.getInstance().run();
	drive.reportToSmartDashboard();
    }

    @Override
    public void teleopInit() {
	Scheduler.getInstance().run();
	drive.reportToSmartDashboard();
    }

    @Override
    public void teleopPeriodic() {
	Scheduler.getInstance().removeAll();
	drive.reportToSmartDashboard();
    }

    @Override
    public void testPeriodic() {
	Scheduler.getInstance().run();
	drive.reportToSmartDashboard();
    }
}
