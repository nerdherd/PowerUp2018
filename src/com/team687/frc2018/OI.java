package com.team687.frc2018;

import com.team687.frc2018.commands.arm.ResetArmEncoder;
import com.team687.frc2018.commands.arm.SetArmPosition;
import com.team687.frc2018.commands.arm.SetArmVoltage;
import com.team687.frc2018.commands.intake.SetIntakeRollerPower;
import com.team687.frc2018.commands.wrist.ResetWristEncoder;
import com.team687.frc2018.commands.wrist.SetWristPercentOutput;
import com.team687.frc2018.commands.wrist.SetWristPosition;
import com.team687.frc2018.constants.SuperstructureConstants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

public class OI {

    public Joystick driveJoyLeft = new Joystick(0);
    public Joystick driveJoyRight = new Joystick(1);
    public Joystick driveJoyArtic = new Joystick(2);

    // public Joystick gamepadJoy = new Joystick(0);

    public JoystickButton intake_1;

    public JoystickButton outtake_2;
    public JoystickButton stopIntake_3;
    public JoystickButton holdIntake_4;

    public JoystickButton armOffset_11;
    public JoystickButton armHorizontal_9;
    public JoystickButton armVertical_7;

    public JoystickButton wristStow_10;
    public JoystickButton wristIntake_12;

    public OI() {
	intake_1 = new JoystickButton(driveJoyArtic, 1);
	intake_1.whenPressed(new SetIntakeRollerPower(-1));
	outtake_2 = new JoystickButton(driveJoyArtic, 2);
	outtake_2.whenPressed(new SetIntakeRollerPower(0.5));
	stopIntake_3 = new JoystickButton(driveJoyArtic, 3);
	stopIntake_3.whenPressed(new SetIntakeRollerPower(0));
	holdIntake_4 = new JoystickButton(driveJoyArtic, 4);
	holdIntake_4.whenPressed(new SetIntakeRollerPower(-0.3));

	armOffset_11 = new JoystickButton(driveJoyArtic, 11);
	armOffset_11.whenPressed(new SetArmPosition(SuperstructureConstants.kArmOffsetPos));
	armHorizontal_9 = new JoystickButton(driveJoyArtic, 9);
	armHorizontal_9.whenPressed(new SetArmPosition(SuperstructureConstants.kArmHorizontalPos));
	armVertical_7 = new JoystickButton(driveJoyArtic, 7);
	armVertical_7.whenPressed(new SetArmPosition(SuperstructureConstants.kArmVerticalPos));

	wristStow_10 = new JoystickButton(driveJoyArtic, 10);
	wristStow_10.whenPressed(new SetWristPosition(SuperstructureConstants.kWristStowArmOffsetPos));
	wristIntake_12 = new JoystickButton(driveJoyArtic, 12);
	wristIntake_12.whenPressed(new SetWristPosition(SuperstructureConstants.kWristIntakePos));

	SmartDashboard.putData("Arm Position Vertical", new SetArmPosition(SuperstructureConstants.kArmVerticalPos));
	SmartDashboard.putData("Arm Position Horizontal",
		new SetArmPosition(SuperstructureConstants.kArmHorizontalPos));
	SmartDashboard.putData("Arm Position Offset", new SetArmPosition(SuperstructureConstants.kArmOffsetPos));
	SmartDashboard.putData("Arm Voltage 0", new SetArmVoltage(0));
	SmartDashboard.putData("Arm Reset Encoder", new ResetArmEncoder());

	SmartDashboard.putData("Wrist Reset Encoders", new ResetWristEncoder());
	SmartDashboard.putData("Wrist 0 Percent Output", new SetWristPercentOutput(0));
	SmartDashboard.putData("Wrist 0.3 Percent Output", new SetWristPercentOutput(0.3));
	SmartDashboard.putData("Wrist -0.3 Percent Output", new SetWristPercentOutput(-0.3));
	SmartDashboard.putData("Wrist Position Intake", new SetWristPosition(SuperstructureConstants.kWristIntakePos));
	SmartDashboard.putData("Wrist Position Offset Stow",
		new SetWristPosition(SuperstructureConstants.kWristStowArmOffsetPos));
	SmartDashboard.putData("Wrist Position Stow", new SetWristPosition(SuperstructureConstants.kWristStowPos));

	SmartDashboard.putData("Set Intake Power 1", new SetIntakeRollerPower(1));
	SmartDashboard.putData("Set Intake Power -1", new SetIntakeRollerPower(-1));
	SmartDashboard.putData("Set Intake Power 0", new SetIntakeRollerPower(0));
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
