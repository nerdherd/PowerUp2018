package com.team687.frc2018;

import com.team687.frc2018.commands.auto.CenterToLeftSwitchAuto;
import com.team687.frc2018.commands.auto.CenterToRightSwitchAuto;
import com.team687.frc2018.commands.auto.LeftToLeftScaleAuto;
import com.team687.frc2018.commands.auto.LeftToRightScaleAuto;
import com.team687.frc2018.commands.auto.RightToLeftScaleAuto;
import com.team687.frc2018.commands.auto.RightToRightScaleAuto;
import com.team687.frc2018.subsystems.Arm;
import com.team687.frc2018.subsystems.Drive;
import com.team687.frc2018.subsystems.Intake;
import com.team687.frc2018.subsystems.Wrist;
import com.team687.frc2018.utilities.CSVLogger;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class Robot extends TimedRobot {

    public static Drive drive;
    public static Arm arm;
    public static Wrist wrist;
    public static Intake intake;

    public static DriverStation ds;
    public static PowerDistributionPanel pdp;
    public static OI oi;

    public static VisionAdapter visionAdapter;
    public static Odometry odometry;

    public static CSVLogger logger;

    SendableChooser<String> sideChooser = new SendableChooser<>();
    public static Command autonomousCommand;
    public static String startingPosition;
    public static boolean fmsSwitchOnLeft, fmsScaleOnLeft;

    @Override
    public void robotInit() {
	logger = CSVLogger.getInstance();

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

	visionAdapter = VisionAdapter.getInstance();
	odometry = Odometry.getInstance();

	ds = DriverStation.getInstance();

	sideChooser.addDefault("Center", "center");
	sideChooser.addObject("Left", "left");
	sideChooser.addObject("Right", "right");
    }

    @Override
    public void disabledInit() {
	Scheduler.getInstance().removeAll();

	arm.updateYawPitchRoll();
	wrist.updateYawPitchRoll();

	drive.reportToSmartDashboard();
	arm.reportToSmartDashboard();
	wrist.reportToSmartDashboard();
	intake.reportToSmartDashboard();

	visionAdapter.reportToSmartDashboard();
	odometry.update();
	odometry.reportToSmartDashboard();
	logger.stopLog();
    }

    @Override
    public void disabledPeriodic() {
	Scheduler.getInstance().removeAll();

	arm.updateYawPitchRoll();
	wrist.updateYawPitchRoll();

	drive.reportToSmartDashboard();
	arm.reportToSmartDashboard();
	wrist.reportToSmartDashboard();
	intake.reportToSmartDashboard();

	visionAdapter.reportToSmartDashboard();
	odometry.update();
	odometry.reportToSmartDashboard();
    }

    @Override
    public void autonomousInit() {
	// Scheduler.getInstance().removeAll();

	arm.updateYawPitchRoll();
	wrist.updateYawPitchRoll();

	drive.reportToSmartDashboard();
	arm.reportToSmartDashboard();
	wrist.reportToSmartDashboard();
	intake.reportToSmartDashboard();

	visionAdapter.reportToSmartDashboard();
	odometry.update();
	odometry.reportToSmartDashboard();

	String msg = DriverStation.getInstance().getGameSpecificMessage();
	while (msg == null || msg.length() < 2) {
	    msg = DriverStation.getInstance().getGameSpecificMessage();
	}

	fmsSwitchOnLeft = msg.substring(0, 1).equals("L");
	fmsScaleOnLeft = msg.substring(1, 2).equals("L");
	startingPosition = sideChooser.getSelected();

	if (startingPosition == "center" && fmsSwitchOnLeft) {
	    autonomousCommand = new CenterToLeftSwitchAuto();
	} else if (startingPosition == "center" && !fmsSwitchOnLeft) {
	    autonomousCommand = new CenterToRightSwitchAuto();
	} else if (startingPosition == "left" && fmsScaleOnLeft) {
	    autonomousCommand = new LeftToLeftScaleAuto();
	} else if (startingPosition == "left" && !fmsScaleOnLeft) {
	    autonomousCommand = new LeftToRightScaleAuto();
	} else if (startingPosition == "right" && fmsScaleOnLeft) {
	    autonomousCommand = new RightToLeftScaleAuto();
	} else if (startingPosition == "right" && !fmsScaleOnLeft) {
	    autonomousCommand = new RightToRightScaleAuto();
	} else {
	    autonomousCommand = null;
	}

	if (autonomousCommand != null) {
	    autonomousCommand.start();
	}
    }

    @Override
    public void autonomousPeriodic() {
	Scheduler.getInstance().run();

	arm.updateYawPitchRoll();
	wrist.updateYawPitchRoll();

	drive.reportToSmartDashboard();
	arm.reportToSmartDashboard();
	wrist.reportToSmartDashboard();
	intake.reportToSmartDashboard();

	visionAdapter.reportToSmartDashboard();
	odometry.update();
	odometry.reportToSmartDashboard();
    }

    @Override
    public void teleopInit() {
	// Scheduler.getInstance().removeAll();

	if (autonomousCommand != null) {
	    autonomousCommand.cancel();
	}

	arm.updateYawPitchRoll();
	wrist.updateYawPitchRoll();

	drive.reportToSmartDashboard();
	arm.reportToSmartDashboard();
	wrist.reportToSmartDashboard();
	intake.reportToSmartDashboard();

	visionAdapter.reportToSmartDashboard();
	odometry.update();
	odometry.reportToSmartDashboard();

	drive.addLoggedData();
	odometry.addLoggedData();
	arm.addLoggedData();
	wrist.addLoggedData();
	logger.startLog();
    }

    @Override
    public void teleopPeriodic() {
	Scheduler.getInstance().run();

	arm.updateYawPitchRoll();
	wrist.updateYawPitchRoll();

	drive.reportToSmartDashboard();
	arm.reportToSmartDashboard();
	wrist.reportToSmartDashboard();
	intake.reportToSmartDashboard();

	visionAdapter.reportToSmartDashboard();
	odometry.update();
	odometry.reportToSmartDashboard();

	drive.updateLog();
	odometry.updateLog();
	arm.updateLog();
	wrist.updateLog();
	logger.logToCSV();
    }

    @Override
    public void testPeriodic() {
	Scheduler.getInstance().run();

	arm.updateYawPitchRoll();
	wrist.updateYawPitchRoll();

	drive.reportToSmartDashboard();
	arm.reportToSmartDashboard();
	wrist.reportToSmartDashboard();
	intake.reportToSmartDashboard();

	visionAdapter.reportToSmartDashboard();
	odometry.update();
	odometry.reportToSmartDashboard();
    }
}
