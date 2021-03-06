package com.team687.frc2018;

import com.team687.frc2018.commands.auto.CenterToLeftSwitchAuto;
import com.team687.frc2018.commands.auto.CenterToRightSwitchAuto;
import com.team687.frc2018.commands.auto.DriveStraightBackwardsWithoutCube;
import com.team687.frc2018.commands.auto.DriveStraightForwardsWithoutCube;
import com.team687.frc2018.commands.auto.LeftToLeftCompatibleScaleAuto;
import com.team687.frc2018.commands.auto.LeftToLeftScale2CubeAuto;
import com.team687.frc2018.commands.auto.LeftToLeftSwitchAuto;
import com.team687.frc2018.commands.auto.LeftToRightScaleAuto;
import com.team687.frc2018.commands.auto.RightToLeftScaleAuto;
import com.team687.frc2018.commands.auto.RightToRightCompatibleScaleAuto;
import com.team687.frc2018.commands.auto.RightToRightScale2CubeAuto;
import com.team687.frc2018.commands.auto.RightToRightSwitchAuto;
import com.team687.frc2018.constants.DriveConstants;
import com.team687.frc2018.constants.SuperstructureConstants;
import com.team687.frc2018.subsystems.Arm;
import com.team687.frc2018.subsystems.Drive;
import com.team687.frc2018.subsystems.Intake;
import com.team687.frc2018.subsystems.Wrist;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

    public static final String kDate = "2018_03_20_";

    public static Drive drive;
    public static Arm arm;
    public static Wrist wrist;
    public static Intake intake;

    public static DriverStation ds;
    public static PowerDistributionPanel pdp;
    public static Compressor compressor;

    public static OI oi;

    SendableChooser<String> sideChooser;
    public static Command autonomousCommand;
    public static String startingPosition;
    public static boolean switchOnLeft, scaleOnLeft;
    SendableChooser<String> typeChooser;
    public static String autoType;

    @Override
    public void robotInit() {
	pdp = new PowerDistributionPanel();
	LiveWindow.disableTelemetry(pdp);
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
	ds = DriverStation.getInstance();

	// CameraServer.getInstance().startAutomaticCapture();

	sideChooser = new SendableChooser<>();
	sideChooser.addDefault("Center", "center");
	sideChooser.addObject("Left", ""
			+ "left");
	sideChooser.addObject("Right", "right");
	SmartDashboard.putData("Starting Pos Chooser", sideChooser);

	typeChooser = new SendableChooser<>();
	typeChooser.addDefault("Main", "main");
	typeChooser.addObject("Compatible", "compatible");
	SmartDashboard.putData("Auto Type Chooser", typeChooser);
    }

    @Override
    public void disabledInit() {
	Scheduler.getInstance().removeAll();

	drive.reportToSmartDashboard();
	arm.reportToSmartDashboard();
	wrist.reportToSmartDashboard();
	intake.reportToSmartDashboard();
	SmartDashboard.putBoolean("HEALTHY DRIVE CURRENT",
		!(drive.getLeftMasterCurrent() > DriveConstants.kDriveSafeCurrent
			|| drive.getRightMasterCurrent() > DriveConstants.kDriveSafeCurrent
			|| drive.getLeftSlaveCurrent() > DriveConstants.kDriveSafeCurrent
			|| drive.getRightSlaveCurrent() > DriveConstants.kDriveSafeCurrent));
	SmartDashboard.putBoolean("HEALTHY SUPERSTRUCTURE CURRENT",
		!(arm.getCurrent() > SuperstructureConstants.kArmSafeCurrent
			|| wrist.getCurrent() > SuperstructureConstants.kWristSafeCurrent));

	drive.stopLog();
	arm.stopLog();
	wrist.stopLog();
    }

    @Override
    public void disabledPeriodic() {
	// Scheduler.getInstance().removeAll();
	if (Robot.oi.driveJoyLeft.getRawButton(5) && Robot.oi.driveJoyRight.getRawButton(5)) {
	    wrist.resetEncoder();
	}
	if (Robot.oi.driveJoyLeft.getRawButton(6) && Robot.oi.driveJoyRight.getRawButton(6)) {
	    arm.setVoltage(0.75); // get rid of backlash
	    arm.resetEncoder();
	}
	if (Robot.oi.driveJoyLeft.getRawButton(3) && Robot.oi.driveJoyRight.getRawButton(3)) {
	    drive.resetEncoders();
	}
	if (Robot.oi.driveJoyLeft.getRawButton(4) && Robot.oi.driveJoyRight.getRawButton(4)) {
	    drive.resetGyro();
	}

	drive.resetEncoders();
	drive.resetGyro();

	drive.reportToSmartDashboard();
	arm.reportToSmartDashboard();
	wrist.reportToSmartDashboard();
	intake.reportToSmartDashboard();
	SmartDashboard.putBoolean("HEALTHY DRIVE CURRENT",
		!(drive.getLeftMasterCurrent() > DriveConstants.kDriveSafeCurrent
			|| drive.getRightMasterCurrent() > DriveConstants.kDriveSafeCurrent
			|| drive.getLeftSlaveCurrent() > DriveConstants.kDriveSafeCurrent
			|| drive.getRightSlaveCurrent() > DriveConstants.kDriveSafeCurrent));
	SmartDashboard.putBoolean("HEALTHY SUPERSTRUCTURE CURRENT",
		!(arm.getCurrent() > SuperstructureConstants.kArmSafeCurrent
			|| wrist.getCurrent() > SuperstructureConstants.kWristSafeCurrent));

	startingPosition = sideChooser.getSelected();
	SmartDashboard.putString("Selected starting position", startingPosition);

	autoType = typeChooser.getSelected();
	SmartDashboard.putString("Selected type of auto", startingPosition);
    }

    @Override
    public void autonomousInit() {
	// Scheduler.getInstance().removeAll();

	String msg = DriverStation.getInstance().getGameSpecificMessage();
	while (msg == null || msg.length() < 2) {
	    msg = DriverStation.getInstance().getGameSpecificMessage();
	}

	switchOnLeft = msg.substring(0, 1).equals("L");
	scaleOnLeft = msg.substring(1, 2).equals("L");

	if (switchOnLeft) {
	    SmartDashboard.putString("Switch Position", "Left");
	} else {
	    SmartDashboard.putString("Switch Position", "Right");
	}

	if (scaleOnLeft) {
	    SmartDashboard.putString("Scale Position", "Left");
	} else {
	    SmartDashboard.putString("Scale Position", "Right");
	}

	if (startingPosition == "center") {
	    if (switchOnLeft) {
		autonomousCommand = new CenterToLeftSwitchAuto();
	    } else if (!switchOnLeft) {
		autonomousCommand = new CenterToRightSwitchAuto();
	    } else {
		autonomousCommand = new DriveStraightForwardsWithoutCube();
	    }
	} else if (startingPosition == "left") {
	    if (scaleOnLeft && autoType == "main") {
		autonomousCommand = new LeftToLeftScale2CubeAuto();
	    } else if (scaleOnLeft && autoType == "compatible") {
		autonomousCommand = new LeftToLeftCompatibleScaleAuto();
//	    } else if (switchOnLeft) {
//		autonomousCommand = new LeftToLeftSwitchAuto();
	    } else {
		autonomousCommand = new LeftToRightScaleAuto();
	    }
	} else if (startingPosition == "right") {
	    if (!scaleOnLeft && autoType == "main") {
		autonomousCommand = new RightToRightScale2CubeAuto();
	    } else if (!scaleOnLeft && autoType == "compatible") {
		autonomousCommand = new RightToRightCompatibleScaleAuto();
//	    } else if (!switchOnLeft) {
//		autonomousCommand = new RightToRightSwitchAuto();
	    } else {
		autonomousCommand = new RightToLeftScaleAuto();
	    }
	} else {
	    autonomousCommand = new DriveStraightBackwardsWithoutCube();
	}
	
//	autonomousCommand = new RightToRightScale2CubeAuto();

	if (autonomousCommand != null) {
	    autonomousCommand.start();
	}

	 drive.startLog();
	 arm.startLog();
	 wrist.startLog();
    }

    @Override
    public void autonomousPeriodic() {
	Scheduler.getInstance().run();

	drive.reportToSmartDashboard();
	arm.reportToSmartDashboard();
	wrist.reportToSmartDashboard();
	intake.reportToSmartDashboard();
	SmartDashboard.putBoolean("HEALTHY DRIVE CURRENT",
		!(drive.getLeftMasterCurrent() > DriveConstants.kDriveSafeCurrent
			|| drive.getRightMasterCurrent() > DriveConstants.kDriveSafeCurrent
			|| drive.getLeftSlaveCurrent() > DriveConstants.kDriveSafeCurrent
			|| drive.getRightSlaveCurrent() > DriveConstants.kDriveSafeCurrent));
	SmartDashboard.putBoolean("HEALTHY SUPERSTRUCTURE CURRENT",
		!(arm.getCurrent() > SuperstructureConstants.kArmSafeCurrent
			|| wrist.getCurrent() > SuperstructureConstants.kWristSafeCurrent));

	 drive.logToCSV();
	 arm.logToCSV();
	 wrist.logToCSV();
    }

    @Override
    public void teleopInit() {
	drive.reportToSmartDashboard();
	arm.reportToSmartDashboard();
	wrist.reportToSmartDashboard();
	intake.reportToSmartDashboard();
	SmartDashboard.putBoolean("HEALTHY DRIVE CURRENT",
		!(drive.getLeftMasterCurrent() > DriveConstants.kDriveSafeCurrent
			|| drive.getRightMasterCurrent() > DriveConstants.kDriveSafeCurrent
			|| drive.getLeftSlaveCurrent() > DriveConstants.kDriveSafeCurrent
			|| drive.getRightSlaveCurrent() > DriveConstants.kDriveSafeCurrent));
	SmartDashboard.putBoolean("HEALTHY SUPERSTRUCTURE CURRENT",
		!(arm.getCurrent() > SuperstructureConstants.kArmSafeCurrent
			|| wrist.getCurrent() > SuperstructureConstants.kWristSafeCurrent));
	compressor.start();
//	drive.startLog();
//	arm.startLog();
//	wrist.startLog();
    }

    @Override
    public void teleopPeriodic() {
	Scheduler.getInstance().run();
	compressor.start();
	drive.reportToSmartDashboard();
	arm.reportToSmartDashboard();
	wrist.reportToSmartDashboard();
	intake.reportToSmartDashboard();
	SmartDashboard.putBoolean("HEALTHY DRIVE CURRENT",
		!(drive.getLeftMasterCurrent() > DriveConstants.kDriveSafeCurrent
			|| drive.getRightMasterCurrent() > DriveConstants.kDriveSafeCurrent
			|| drive.getLeftSlaveCurrent() > DriveConstants.kDriveSafeCurrent
			|| drive.getRightSlaveCurrent() > DriveConstants.kDriveSafeCurrent));
	SmartDashboard.putBoolean("HEALTHY SUPERSTRUCTURE CURRENT",
		!(arm.getCurrent() > SuperstructureConstants.kArmSafeCurrent
			|| wrist.getCurrent() > SuperstructureConstants.kWristSafeCurrent));

//	drive.logToCSV();
//	arm.logToCSV();
//	wrist.logToCSV();

	if (ds.getMatchTime() < 5) {
	    Robot.wrist.enableBrakeMode();
	}
    }

    @Override
    public void testPeriodic() {
    }
}
