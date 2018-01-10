package com.team687.frc2018;

import com.team687.frc2018.subsystems.Arm;
import com.team687.frc2018.subsystems.Drive;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends TimedRobot {

    public static Drive drive;
    public static Arm arm;
    public static PowerDistributionPanel pdp;
    public static Compressor compressor;
    public static OI oi;
    public static NetworkTableInstance networkTable;

    public static VisionAdapter visionAdapter;
    public static Odometry odometry;

    @Override
    public void robotInit() {
	pdp = new PowerDistributionPanel();
	compressor = new Compressor();
	compressor.start();

	arm = new Arm();
	arm.setZeroVoltage();
	arm.resetEncoder();

	drive = new Drive();
	drive.stopDrive();
	drive.resetEncoders();
	drive.resetGyro();

	oi = new OI();

	visionAdapter = VisionAdapter.getInstance();
	odometry = Odometry.getInstance();
    }

    @Override
    public void disabledInit() {
	Scheduler.getInstance().removeAll();
	drive.reportToSmartDashboard();
	visionAdapter.reportToSmartDashboard();
	odometry.update();
    }

    @Override
    public void disabledPeriodic() {
	Scheduler.getInstance().run();
	drive.reportToSmartDashboard();
	visionAdapter.reportToSmartDashboard();
	odometry.update();
    }

    @Override
    public void autonomousInit() {
	Scheduler.getInstance().removeAll();
	drive.reportToSmartDashboard();
	visionAdapter.reportToSmartDashboard();
	odometry.update();
    }

    @Override
    public void autonomousPeriodic() {
	Scheduler.getInstance().run();
	drive.reportToSmartDashboard();
	visionAdapter.reportToSmartDashboard();
	odometry.update();
    }

    @Override
    public void teleopInit() {
	Scheduler.getInstance().run();
	drive.reportToSmartDashboard();
	visionAdapter.reportToSmartDashboard();
	odometry.update();
    }

    @Override
    public void teleopPeriodic() {
	Scheduler.getInstance().removeAll();
	drive.reportToSmartDashboard();
	visionAdapter.reportToSmartDashboard();
	odometry.update();
    }

    @Override
    public void testPeriodic() {
	Scheduler.getInstance().run();
	drive.reportToSmartDashboard();
	visionAdapter.reportToSmartDashboard();
	odometry.update();
    }
}
