package com.team687.frc2018;

import com.team687.frc2018.commands.arm.SetArmPercentOutput;
import com.team687.frc2018.commands.arm.SetArmPosition;
import com.team687.frc2018.commands.intake.SetIntakeRollerPower;
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

    // public Joystick gamepadJoy = new Joystick(0);

    public JoystickButton quickTurn_1;

    public OI() {
	quickTurn_1 = new JoystickButton(driveJoyRight, 1);

	SmartDashboard.putData("Set Intake Rollers On", new SetIntakeRollerPower(SuperstructureConstants.kRollerPower));
	SmartDashboard.putData("Set Intake Rollers Reverse",
		new SetIntakeRollerPower(-SuperstructureConstants.kRollerPower));

	SmartDashboard.putData("Set Wrist Positive Voltage Test",
		new SetWristPercentOutput(SuperstructureConstants.kWristMaxVoltageForward / 12));
	SmartDashboard.putData("Set Wrist Negative Voltage Test",
		new SetWristPercentOutput(SuperstructureConstants.kWristMaxVoltageReverse / 12));

	SmartDashboard.putData("Set Arm Positive Voltage Test",
		new SetArmPercentOutput(SuperstructureConstants.kArmMaxVoltageForward / 12));
	SmartDashboard.putData("Set Arm Negative Voltage Test",
		new SetArmPercentOutput(SuperstructureConstants.kArmMaxVoltageReverse / 12));

	SmartDashboard.putData("Set Wrist Position Intake",
		new SetWristPosition(SuperstructureConstants.kWristIntakePos));
	SmartDashboard.putData("Set Wrist Position Stow", new SetWristPosition(SuperstructureConstants.kWristStowPos));

	SmartDashboard.putData("Set Arm Position Down", new SetArmPosition(SuperstructureConstants.kArmDownPos));
	SmartDashboard.putData("Set Arm Position Scale Forward",
		new SetArmPosition(SuperstructureConstants.kArmScaleMidPos));
    }

    /**
     * @return input power from left drive joystick Y (-1.0 to +1.0)
     */
    public double getDriveJoyLeftY() {
	// return -gamepadJoy.getRawAxis(2);
	return driveJoyLeft.getY();
    }

    /**
     * @return input power from right drive joystick Y (-1.0 to +1.0)
     */
    public double getDriveJoyRightY() {
	// return -gamepadJoy.getRawAxis(4);
	return driveJoyRight.getY();
    }

    /**
     * @return input power from left drive joystick X (-1.0 to +1.0)
     */
    public double getDriveJoyLeftX() {
	// return gamepadJoy.getRawAxis(1);
	return driveJoyLeft.getX();
    }

    /**
     * @return input power from right drive joystick X (-1.0 to +1.0)
     */
    public double getDriveJoyRightX() {
	// return gamepadJoy.getRawAxis(3);
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

    /**
     * @return if quick turn state in cheesydrive mode
     */
    public boolean getQuickTurn() {
	return quickTurn_1.get();
    }

}
