package com.team687.frc2018;

import com.team687.frc2018.commands.arm.ResetArmEncoder;
import com.team687.frc2018.commands.arm.SetArmPosition;
import com.team687.frc2018.commands.auto.CenterToLeftSwitch;
import com.team687.frc2018.commands.auto.CenterToRightSwitch;
import com.team687.frc2018.commands.auto.SideToSameSideScaleLeft;
import com.team687.frc2018.commands.auto.SideToSameSideScaleRight;
import com.team687.frc2018.commands.wrist.ResetWristEncoder;
import com.team687.frc2018.constants.SuperstructureConstants;
import com.team687.frc2018.subsystems.Arm;
import com.team687.frc2018.subsystems.Drive;
import com.team687.frc2018.subsystems.Intake;
import com.team687.frc2018.subsystems.Wrist;
import com.team687.frc2018.utilities.CSVLogger;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Robot extends TimedRobot {

	public static Drive drive;
	public static Arm arm;
	public static Wrist wrist;
	public static Intake intake;

	public static PowerDistributionPanel pdp;
	public static OI oi;
	
	public static int m_counter = 0;
	
	public static Compressor compressor;

//	SendableChooser<String> startingPosition = new SendableChooser<>();	
//	
//	String gameData;
//	String switchPosition;
//	String scalePosition;
	
	@Override
	public void robotInit() {
		// logger = CSVLogger.getInstance();

		pdp = new PowerDistributionPanel();
		compressor = new Compressor();
		compressor.start();
		
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
		
//		startingPosition.addObject("Left", "left");
//		startingPosition.addObject("Right", "right");
//		startingPosition.addObject("Center", "center");
	}

	@Override
	public void disabledInit() {
		Scheduler.getInstance().removeAll();

//		arm.updateYawPitchRoll();
//		wrist.updateYawPitchRoll();
//		drive.reportToSmartDashboard();
//		arm.reportToSmartDashboard();
//		wrist.reportToSmartDashboard();

		drive.stopLog();
//		wrist.stopLog();
//		arm.stopLog();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().removeAll();

//		arm.updateYawPitchRoll();
//		wrist.updateYawPitchRoll();
//		drive.reportToSmartDashboard();
		arm.reportToSmartDashboard();
		wrist.reportToSmartDashboard();
	}

	@Override
	public void autonomousInit() {
		// Scheduler.getInstance().removeAll();

//		arm.updateYawPitchRoll();
//		wrist.updateYawPitchRoll();
//		 drive.reportToSmartDashboard();
//		arm.reportToSmartDashboard();
//		wrist.reportToSmartDashboard();
		
		
		
//		gameData = DriverStation.getInstance().getGameSpecificMessage();
//		while (m_counter < 100) {
//			Timer.delay(0.001);
//			if (gameData.length() <=  0) {
//				m_counter++;
//			} else {
//				m_counter = 0;
//			}
//		}
//	
//		if(gameData.charAt(0) == 'L') {
//			switchPosition = "L";
//		}
//		else if(gameData.charAt(0) == 'R') {
//			switchPosition = "R";
//		}
//		
//		if(gameData.charAt(1) == 'L') {
//			scalePosition = "L";
//		}
//		else if(gameData.charAt(1) == 'R') {
//			scalePosition = "R";
//		}
//			
//		if (startingPosition.equals("center")) {
//			if (switchPosition == "L") {
//				Scheduler.getInstance().add(new CenterToLeftSwitch());
//			}
//			else if (switchPosition == "R") {
//				Scheduler.getInstance().add(new CenterToRightSwitch());
//			}
//			
//		}
//		
//		
//		else if (startingPosition.equals("left")) {
//			if (scalePosition == "L") {
//				Scheduler.getInstance().add(new SideToSameSideScaleLeft());
//			}
//			else if (scalePosition == "R") {
////				Scheduler.getInstance().add(new LeftSideToRightScale());
//			}
//		}
//		
//		else if (startingPosition.equals("right")) {
//			if (scalePosition == "L") {
////				Scheduler.getInstance().add(new RightSideToLeftScale);
//			}
//			else if (scalePosition == "R") {
//				Scheduler.getInstance().add(new SideToSameSideScaleRight());
//			}
//		}
		
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();

//		arm.updateYawPitchRoll();
//		wrist.updateYawPitchRoll();
//		drive.reportToSmartDashboard();
//		arm.reportToSmartDashboard();
//		wrist.reportToSmartDashboard();
	}

	@Override
	public void teleopInit() {
		// Scheduler.getInstance().removeAll();
//		Scheduler.getInstance().add(new ResetWristEncoder());
//		Scheduler.getInstance().add(new ResetArmEncoder());
//		Scheduler.getInstance().add(new SetArmPosition(SuperstructureConstants.kArmOffsetPos));

//		drive.reportToSmartDashboard();
//		arm.reportToSmartDashboard();
//		wrist.reportToSmartDashboard();

		drive.startLog();
//		wrist.startLog();
//		arm.startLog();
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

//		arm.updateYawPitchRoll();
//		wrist.updateYawPitchRoll();
//		drive.reportToSmartDashboard();
//		arm.reportToSmartDashboard();
//		wrist.reportToSmartDashboard();

		drive.logToCSV();
//		wrist.logToCSV();
//		arm.logToCSV();
	}

	@Override
	public void testPeriodic() {
		Scheduler.getInstance().run();

//		arm.updateYawPitchRoll();
//		wrist.updateYawPitchRoll();
//		drive.reportToSmartDashboard();
//		arm.reportToSmartDashboard();
//		wrist.reportToSmartDashboard();
	}
}
