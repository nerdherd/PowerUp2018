package com.team687.frc2018;

import com.team687.frc2018.commands.arm.ResetArmEncoder;
import com.team687.frc2018.commands.arm.SetArmPosition;
import com.team687.frc2018.commands.arm.SetArmVoltage;
import com.team687.frc2018.commands.auto.CenterToLeftSwitchAuto;
import com.team687.frc2018.commands.auto.CenterToRightSwitchAuto;
import com.team687.frc2018.commands.auto.LeftToLeftScale2CubeAuto;
import com.team687.frc2018.commands.auto.LeftToRightScaleAuto;
import com.team687.frc2018.commands.auto.RightToLeftScaleAuto;
import com.team687.frc2018.commands.auto.RightToRightCompatibleScaleAuto;
import com.team687.frc2018.commands.auto.RightToRightScale2CubeAuto;
import com.team687.frc2018.commands.auto.RightToRightScale3CubeAuto;
import com.team687.frc2018.commands.drive.ResetDriveEncoders;
import com.team687.frc2018.commands.drive.ResetGyro;
import com.team687.frc2018.commands.intake.ClawClose;
import com.team687.frc2018.commands.intake.ClawOpen;
import com.team687.frc2018.commands.intake.SetIntakeRollerPower;
import com.team687.frc2018.commands.superstructure.AdjustForwardsScale;
import com.team687.frc2018.commands.superstructure.DefaultIntake;
import com.team687.frc2018.commands.superstructure.DefaultStow;
import com.team687.frc2018.commands.superstructure.EmergencyWristSave;
import com.team687.frc2018.commands.superstructure.ForwardsScaleToStow;
import com.team687.frc2018.commands.superstructure.StackCubes;
import com.team687.frc2018.commands.superstructure.StowToBackwardsScale;
import com.team687.frc2018.commands.superstructure.StowToForwardsScale;
import com.team687.frc2018.commands.superstructure.SwitchScorePositionTeleop;
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

    public Joystick driveJoyLeft = new Joystick(0);
    public Joystick driveJoyRight = new Joystick(1);
    public Joystick driveJoyArtic = new Joystick(2);

    // public Joystick gamepadJoy = new Joystick(0);

    
    public JoystickButton intake_1;
    public JoystickButton outtake_2;
    public JoystickButton stopIntake_3;
    public JoystickButton intakePosition_4;

    public JoystickButton switchPosition_11;
    public JoystickButton intakeRollers_9;
    public JoystickButton stowToForwards_7;
    public JoystickButton adjustMiddle_8;
    public JoystickButton defaultStow_10;

    
    
    public JoystickButton openClaw_6;
    public JoystickButton closeClaw_5;
    
    public JoystickButton sketchyStowToBackwards_12;

    public OI() {
	intake_1 = new JoystickButton(driveJoyArtic, 1);
	intake_1.whenPressed(new DefaultIntake());
	
	outtake_2 = new JoystickButton(driveJoyArtic, 2);
	outtake_2.whenPressed(new SetIntakeRollerPower(.8333333333333333));
	stopIntake_3 = new JoystickButton(driveJoyArtic, 3);
	stopIntake_3.whenPressed(new SetIntakeRollerPower(0));
	intakePosition_4 = new JoystickButton(driveJoyArtic, 4);
	intakePosition_4.whenPressed(new DefaultIntake());

	intakeRollers_9 = new JoystickButton(driveJoyArtic, 9);
	intakeRollers_9.whenPressed(new SetIntakeRollerPower(-.8333333333333333));
//	intakeRollers_9.whenPressed(new StackCubes(0));
	stowToForwards_7 = new JoystickButton(driveJoyArtic, 7);
	
	stowToForwards_7.whenPressed(new StowToForwardsScale());
//	stowToForwards_7.whenPressed(new StackCubes(-15));
	
	adjustMiddle_8 = new JoystickButton(driveJoyArtic, 8);
	adjustMiddle_8.whenPressed(new AdjustForwardsScale(SuperstructureConstants.kArmMiddleScalePosition));
	defaultStow_10 = new JoystickButton(driveJoyArtic, 10);
	defaultStow_10.whenPressed(new DefaultStow());
	switchPosition_11 = new JoystickButton(driveJoyArtic, 11);
	switchPosition_11.whenPressed(new SwitchScorePositionTeleop());
//	switchPosition_11.whenPressed(new StackCubes(35));

	openClaw_6 = new JoystickButton(driveJoyArtic, 5);
	openClaw_6.whenPressed(new ClawOpen());
	closeClaw_5 = new JoystickButton(driveJoyArtic, 6);
	closeClaw_5.whenPressed(new ClawClose());

	sketchyStowToBackwards_12 = new JoystickButton(driveJoyArtic, 12);
	sketchyStowToBackwards_12.whenPressed(new StowToBackwardsScale());
	
//	sketchyStowToBackwards_12.whenPressed(new StackCubes(55));
	
	
	SmartDashboard.putData("****EMERGENCY WRIST SAVE****", new EmergencyWristSave());
	SmartDashboard.putData("Stack Cube 0 deg", new StackCubes(0));
	SmartDashboard.putData("Stack Cube 45 deg", new StackCubes(45));
	SmartDashboard.putData("Stack Cube 60 deg", new StackCubes(60));
	 SmartDashboard.putData("Arm Reset Encoder", new ResetArmEncoder());
	 SmartDashboard.putData("Wrist Reset Encoder", new ResetWristEncoder());
	 SmartDashboard.putData("Drive Reset Encoders", new ResetDriveEncoders());
	 SmartDashboard.putData("Drive Reset Gyro", new ResetGyro());

	// SmartDashboard.putData("Drive Straight Test", new TestDriveSubsystem());
	
	 
	 SmartDashboard.putData("Arm Voltage 0", new SetArmVoltage(0));
	 SmartDashboard.putData("Arm Position Vertical", new
	 SetArmPosition(SuperstructureConstants.kArmVerticalPos));
	 SmartDashboard.putData("Arm Position Horizontal",
	 new SetArmPosition(SuperstructureConstants.kArmHorizontalPos));
	 SmartDashboard.putData("Arm Position Offset", new
	 SetArmPosition(SuperstructureConstants.kArmOffsetPos));
	//
	// SmartDashboard.putData("Wrist Voltage 0", new SetWristPercentOutput(0));
	// SmartDashboard.putData("Wrist Position Intake", new
	// SetWristPosition(Robot.wrist.angleAbsoluteToTicks(0)));
	// SmartDashboard.putData("Wrist Position Offset Stow",
	// new SetWristPosition(SuperstructureConstants.kWristStowArmOffsetPos));
	// SmartDashboard.putData("Wrist Position Stow", new
	// SetWristPosition(Robot.wrist.angleAbsoluteToTicks(90)));
	//
	// SmartDashboard.putData("Set Intake Power 1", new SetIntakeRollerPower(1));
	// SmartDashboard.putData("Set Intake Power -1", new SetIntakeRollerPower(-1));
	// SmartDashboard.putData("Set Intake Power 0", new SetIntakeRollerPower(0));
	// SmartDashboard.putData("Outtake", new SetIntakeRollerPower(0.4));
	// SmartDashboard.putData("Open Intake Claw", new ClawOpen());
	// SmartDashboard.putData("Close Intake Claw", new ClawClose());

	// SmartDashboard.putData("Superstructure Stow to Backwards Scale", new
	// StowToBackwardsScale());
	// SmartDashboard.putData("Superstructure Stow to Forwards Scale", new
	// StowToForwardsScale());
	// SmartDashboard.putData("Superstructure Backwards Scale To Stow", new
	// BackwardsScaleToStow());
	// SmartDashboard.putData("Superstructure Forwards Scale to Stow", new
	// ForwardsScaleToStow());
	//
	// SmartDashboard.putData("Superstructure Stow", new DefaultStow());
	// SmartDashboard.putData("Superstructure Intake", new DefaultIntake());
	// SmartDashboard.putData("Superstructure Intake Position", new
	// IntakePosition());

	// SmartDashboard.putData("Turn To 0", new TurnToAngle(0, 4, 2));
	// SmartDashboard.putData("Turn To 90", new TurnToAngle(90, 4, 2));
	// SmartDashboard.putData("Turn To -90", new TurnToAngle(-90, 4, 2));
	// SmartDashboard.putData("Turn To 5", new TurnToAngle(5, 4, 2));
	// SmartDashboard.putData("Turn To -5", new TurnToAngle(-5, 4, 2));
	// SmartDashboard.putData("Turn To 20", new TurnToAngle(20, 4, 2));
	// SmartDashboard.putData("Turn To -20", new TurnToAngle(-20, 4, 2));

	SmartDashboard.putData("Center To Left Switch", new CenterToLeftSwitchAuto());
	SmartDashboard.putData("Center To Right Switch", new CenterToRightSwitchAuto());
	SmartDashboard.putData("Left To Left 2 Cube Scale", new LeftToLeftScale2CubeAuto());
	SmartDashboard.putData("Right To Right 2 Cube Scale", new RightToRightScale2CubeAuto());
	SmartDashboard.putData("Right to Right 3 cube Scale", new RightToRightScale3CubeAuto());
//	SmartDashboard.putData("Left To Right Scale", new LeftToRightScaleAuto());
//	SmartDashboard.putData("Right To Left Scale", new RightToLeftScaleAuto());
	SmartDashboard.putData("Right to Right Scale Compatible", new RightToRightCompatibleScaleAuto());

	// SmartDashboard.putData("Drive Straight Auto", new DriveStraightAuto());
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
     * @return input power from artic joy
     */
    public double getArticJoyY() {
    	return driveJoyArtic.getY();
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
