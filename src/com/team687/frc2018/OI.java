package com.team687.frc2018;

import com.team687.frc2018.commands.arm.ResetArmEncoder;
import com.team687.frc2018.commands.arm.SetArmPosition;
import com.team687.frc2018.commands.auto.CenterToRightScale;
import com.team687.frc2018.commands.drive.ResetDriveEncoders;
import com.team687.frc2018.commands.drive.ResetGyro;
import com.team687.frc2018.commands.drive.TestDriveSubsystem;
import com.team687.frc2018.commands.intake.SetIntakeRollerPower;
import com.team687.frc2018.commands.superstructure.BackwardsScaleToStow;
import com.team687.frc2018.commands.superstructure.ForwardsScaleToStow;
import com.team687.frc2018.commands.superstructure.IntakeSequence;
import com.team687.frc2018.commands.superstructure.StowCube;
import com.team687.frc2018.commands.superstructure.StowToBackwardsScale;
import com.team687.frc2018.commands.superstructure.StowToForwardsScale;
import com.team687.frc2018.commands.wrist.ResetWristEncoder;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

public class OI {

	// public Joystick driveJoyLeft = new Joystick(0);
	public Joystick driveJoyRight = new Joystick(1);
	public Joystick driveJoyLeft = new Joystick(0);

	// left = throttle, right = turn
	// public Joystick gamepadJoy = new Joystick(0);

	public JoystickButton intake_1;

	public JoystickButton outtake_2;
	public JoystickButton outtake12V_2;
	public JoystickButton stopIntake_3;
	public JoystickButton holdIntake_4;

	public JoystickButton armOffset_11;
	public JoystickButton forwardsScaleToStow_9;
	public JoystickButton stowToScaleForwards_7;

	public JoystickButton wristStow_12;
	public JoystickButton wristIntake_12;

	public JoystickButton stowToBackwards_8;
	public JoystickButton backwardsToStow_10;

	public OI() {
		intake_1 = new JoystickButton(driveJoyLeft, 1);
		intake_1.whenPressed(new IntakeSequence());
		outtake_2 = new JoystickButton(driveJoyLeft, 2);
		outtake_2.whenPressed(new SetIntakeRollerPower(-0.5));
		// outtake12V_2 = new JoystickButton(driveJoyLeft, 2);
		// outtake12V_2.whenPressed(new SetIntakeRollerPower(-1));
		stopIntake_3 = new JoystickButton(driveJoyLeft, 3);
		stopIntake_3.whenPressed(new SetIntakeRollerPower(0));
		holdIntake_4 = new JoystickButton(driveJoyLeft, 4);
		holdIntake_4.whenPressed(new SetIntakeRollerPower(0.3));

		armOffset_11 = new JoystickButton(driveJoyLeft, 11);
		armOffset_11.whenPressed(new SetArmPosition(SuperstructureConstants.kArmOffsetPos));
		forwardsScaleToStow_9 = new JoystickButton(driveJoyLeft, 9);
		forwardsScaleToStow_9.whenPressed(new ForwardsScaleToStow());
		stowToScaleForwards_7 = new JoystickButton(driveJoyLeft, 7);
		stowToScaleForwards_7.whenPressed(new StowToForwardsScale());

		wristStow_12 = new JoystickButton(driveJoyLeft, 12);
		wristStow_12.whenPressed(new StowCube());
		// wristIntake_12 = new JoystickButton(driveJoyArtic, 12);
		// wristIntake_12.whenPressed(new StowToIntake());

		stowToBackwards_8 = new JoystickButton(driveJoyLeft, 8);
		stowToBackwards_8.whenPressed(new StowToBackwardsScale());
		backwardsToStow_10 = new JoystickButton(driveJoyLeft, 10);
		backwardsToStow_10.whenPressed(new BackwardsScaleToStow());

		// SmartDashboard.putData("Arm Position Down", new
		// SetArmPosition(SuperstructureConstants.kArmDownPos));
		// SmartDashboard.putData("Arm Position Vertical", new
		// SetArmPosition(SuperstructureConstants.kArmVerticalPos));
		// SmartDashboard.putData("Arm Position Horizontal",
		// new SetArmPosition(SuperstructureConstants.kArmHorizontalPos));
		// SmartDashboard.putData("Arm Position Offset", new
		// SetArmPosition(SuperstructureConstants.kArmOffsetPos));
		// SmartDashboard.putData("Arm Voltage 0", new SetArmVoltage(0));

		SmartDashboard.putData("Arm Reset Encoder", new ResetArmEncoder());
		SmartDashboard.putData("Wrist Reset Encoder", new ResetWristEncoder());
		SmartDashboard.putData("Reset Drive Encoders", new ResetDriveEncoders());
		SmartDashboard.putData("Reset Yaw", new ResetGyro());
		SmartDashboard.putData("Test Drive Forwards", new TestDriveSubsystem());
		SmartDashboard.putData("Auto Center To Scale", new CenterToRightScale());

		SmartDashboard.putData("Stow Position", new BackwardsScaleToStow());

		// SmartDashboard.putData("Wrist Voltage 0", new
		// SetWristPercentOutput(0));
		// SmartDashboard.putData("Wrist Position Intake", new
		// SetWristPosition(SuperstructureConstants.kWristIntakePos));
		// SmartDashboard.putData("Wrist Position Offset Stow",
		// new
		// SetWristPosition(SuperstructureConstants.kWristStowArmOffsetPos));
		// SmartDashboard.putData("Wrist Position Stow", new
		// SetWristPosition(SuperstructureConstants.kWristStowPos));
		// SmartDashboard.putData("Wrist Position Score Forward Position",
		// new
		// SetWristPosition(SuperstructureConstants.kWristScoreForwardsScalePos));
		// SmartDashboard.putData("Wrist Position Score Backward Position",
		// new
		// SetWristPosition(SuperstructureConstants.kWristScoreBackwardsScalePos));
		// //
		// SmartDashboard.putData("Set Intake Power 0.5", new
		// SetIntakeRollerPower(0.5));
		// SmartDashboard.putData("Set Intake Power -1", new
		// SetIntakeRollerPower(-1));
		// SmartDashboard.putData("Set Intake Power 0", new
		// SetIntakeRollerPower(0));

		// SmartDashboard.putData("Arm Voltage 0", new SetArmVoltage(0));
		// SmartDashboard.putData("Arm Voltage 3", new SetArmVoltage(3));
		// SmartDashboard.putData("Arm Voltage 6", new SetArmVoltage(6));
		// SmartDashboard.putData("Arm Voltage 9", new SetArmVoltage(9));
		// SmartDashboard.putData("Arm Voltage 12", new SetArmVoltage(12));
		// SmartDashboard.putData("Arm Voltage -3", new SetArmVoltage(-3));
		// SmartDashboard.putData("Arm Voltage -6", new SetArmVoltage(-6));
		// SmartDashboard.putData("Arm Voltage -9", new SetArmVoltage(-9));
		// SmartDashboard.putData("Arm Voltage -12", new SetArmVoltage(-12));
		//
		// SmartDashboard.putData("Wrist Voltage 0", new SetWristVoltage(0));
		// SmartDashboard.putData("Wrist Voltage 3", new SetWristVoltage(3));
		// SmartDashboard.putData("Wrist Voltage 6", new SetWristVoltage(6));
		// SmartDashboard.putData("Wrist Voltage 9", new SetWristVoltage(9));
		// SmartDashboard.putData("Wrist Voltage 12", new SetWristVoltage(12));
		// SmartDashboard.putData("Wrist Voltage -3", new SetWristVoltage(-3));
		// SmartDashboard.putData("Wrist Voltage -6", new SetWristVoltage(-6));
		// SmartDashboard.putData("Wrist Voltage -9", new SetWristVoltage(-9));
		// SmartDashboard.putData("Wrist Voltage -12", new
		// SetWristVoltage(-12));

		// SmartDashboard.putData("Forwards Scale to Stow", new
		// ForwardsScaleToStow());
		// SmartDashboard.putData("Stow to Forwards Scale", new
		// StowToForwardsScale());
		// SmartDashboard.putData("Backwards Scale to Stow", new
		// BackwardsScaleToStow());
		// SmartDashboard.putData("Stow to Backwards Scale", new
		// StowToBackwardsScale());
		// SmartDashboard.putData("Intake Sequence", new IntakeSequence());

	}

	/**
	 * @return input power from left drive joystick Y (-1.0 to +1.0)
	 */
	public double getDriveJoyLeftY() {
		// return -gamepadJoy.getRawAxis(1);
		return -driveJoyLeft.getY();
	}

	/**
	 * @return input power from right drive joystick Y (-1.0 to +1.0)
	 */
	public double getDriveJoyRightY() {
		// return -gamepadJoy.getRawAxis(3);
		return -driveJoyRight.getY();
	}

	/**
	 * @return input power from left drive joystick X (-1.0 to +1.0)
	 */
	public double getDriveJoyLeftX() {
		// return gamepadJoy.getRawAxis(0);
		return driveJoyLeft.getX();
	}

	/**
	 * @return input power from right drive joystick X (-1.0 to +1.0)
	 */
	public double getDriveJoyRightX() {
		// return gamepadJoy.getRawAxis(2);
		return driveJoyRight.getX();
	}

	/**
	 * @return input throttle from right drive joystick (0 to +1.0)
	 */
	public double getThrottleR() {
		return (driveJoyRight.getThrottle() + 1) / 2;
	}

	/**
	 * @return input throttle from left drive josytick
	 */
	public double getThrottleL() {
		return (driveJoyLeft.getThrottle() + 1) / 2;
	}

}
