package com.team687.frc2018;

import com.team687.frc2018.commands.arm.ResetArmEncoder;
import com.team687.frc2018.commands.wrist.ResetWristEncoder;
import com.team687.frc2018.subsystems.Arm;
import com.team687.frc2018.subsystems.Drive;
import com.team687.frc2018.subsystems.Intake;
import com.team687.frc2018.subsystems.Wrist;
import com.team687.frc2018.utilities.CSVLogger;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends TimedRobot {

	public static Drive drive;
	public static Arm arm;
	public static Wrist wrist;
	public static Intake intake;

	public static PowerDistributionPanel pdp;
	public static OI oi;

	public static VisionAdapter visionAdapter;

	public static CSVLogger logger;

	@Override
	public void robotInit() {
		// logger = CSVLogger.getInstance();

		pdp = new PowerDistributionPanel();

		arm = new Arm();
		arm.setVoltage(0);
		arm.resetEncoder();

		wrist = new Wrist();
		wrist.setPercentOutput(0);
		wrist.resetEncoder();

		intake = new Intake();
		intake.setRollerPower(0);

		drive = new Drive();
		drive.stopDrive();
		drive.resetEncoders();
		drive.resetGyro();

		oi = new OI();
	}

	@Override
	public void disabledInit() {
		Scheduler.getInstance().removeAll();

//		arm.updateYawPitchRoll();
//		wrist.updateYawPitchRoll();
		 drive.reportToSmartDashboard();
		arm.reportToSmartDashboard();
		wrist.reportToSmartDashboard();

		// drive.stopLog();
		wrist.stopLog();
		arm.stopLog();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().removeAll();

//		arm.updateYawPitchRoll();
//		wrist.updateYawPitchRoll();
		 drive.reportToSmartDashboard();
		arm.reportToSmartDashboard();
		wrist.reportToSmartDashboard();
	}

	@Override
	public void autonomousInit() {
		// Scheduler.getInstance().removeAll();

//		arm.updateYawPitchRoll();
//		wrist.updateYawPitchRoll();
		 drive.reportToSmartDashboard();
		arm.reportToSmartDashboard();
		wrist.reportToSmartDashboard();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();

//		arm.updateYawPitchRoll();
//		wrist.updateYawPitchRoll();
		 drive.reportToSmartDashboard();
		arm.reportToSmartDashboard();
		wrist.reportToSmartDashboard();
	}

	@Override
	public void teleopInit() {
		// Scheduler.getInstance().removeAll();
//		Scheduler.getInstance().add(new ResetWristEncoder());
//		Scheduler.getInstance().add(new ResetArmEncoder());
		
		arm.updateYawPitchRoll();
		wrist.updateYawPitchRoll();
		 drive.reportToSmartDashboard();
		arm.reportToSmartDashboard();
		wrist.reportToSmartDashboard();

		// drive.startLog();
		wrist.startLog();
		arm.startLog();
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

//		arm.updateYawPitchRoll();
//		wrist.updateYawPitchRoll();
		 drive.reportToSmartDashboard();
		arm.reportToSmartDashboard();
		wrist.reportToSmartDashboard();

		// drive.logToCSV();
		wrist.logToCSV();
		arm.logToCSV();
	}

	@Override
	public void testPeriodic() {
		Scheduler.getInstance().run();

//		arm.updateYawPitchRoll();
//		wrist.updateYawPitchRoll();
		 drive.reportToSmartDashboard();
		arm.reportToSmartDashboard();
		wrist.reportToSmartDashboard();
	}
}
