package com.team687.frc2018;

import com.team687.frc2018.commands.arm.ResetArmEncoder;
import com.team687.frc2018.commands.arm.SetArmPosition;
import com.team687.frc2018.commands.arm.SetArmVoltage;
import com.team687.frc2018.commands.auto.CenterToRightScale;
import com.team687.frc2018.commands.drive.ResetDriveEncoders;
import com.team687.frc2018.commands.drive.ResetGyro;
import com.team687.frc2018.commands.intake.SetIntakeRollerPower;
import com.team687.frc2018.commands.superstructure.CalibrateGyro;
import com.team687.frc2018.commands.superstructure.ForwardsScaleToStow;
import com.team687.frc2018.commands.superstructure.IntakeWithoutIntaking;
import com.team687.frc2018.commands.superstructure.ResetPigeons;
import com.team687.frc2018.commands.superstructure.BackwardsScaleToStow;
import com.team687.frc2018.commands.superstructure.StowToBackwardsScale;
import com.team687.frc2018.commands.superstructure.StowToForwardsScale;
import com.team687.frc2018.commands.superstructure.StowToIntake;
import com.team687.frc2018.commands.wrist.ResetWristEncoder;
import com.team687.frc2018.commands.wrist.SetWristPercentOutput;
import com.team687.frc2018.commands.wrist.SetWristPosition;
import com.team687.frc2018.commands.wrist.SetWristVoltage;
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

//     public Joystick gamepadJoy = new Joystick(0);

    public JoystickButton quickTurn_1;
    
    public JoystickButton intake_1;
    
    public JoystickButton outtake_2;
    public JoystickButton stopIntake_3;
    public JoystickButton finishedIntake_4;
    
    public JoystickButton armOffset_11;
//    public JoystickButton armHorizontal_9;
//    public JoystickButton armVertical_7;
    
    public JoystickButton forwardsToStow_9;
    public JoystickButton stowToForwards_7;
    public JoystickButton stowToBackwards_6;
    public JoystickButton backwardsToStow_4;
    
    public JoystickButton wristIntakeWithoutIntake_8;
    public JoystickButton wristStow_10;
    public JoystickButton wristIntake_12;

    public OI() {
//	quickTurn_1 = new JoystickButton(driveJoyRight, 1);
    	
    intake_1 = new JoystickButton(driveJoyArtic, 1);
    intake_1.whenPressed(new SetIntakeRollerPower(-1));
    outtake_2 = new JoystickButton(driveJoyArtic, 2);
    outtake_2.whenPressed(new SetIntakeRollerPower(0.4));
    stopIntake_3 = new JoystickButton(driveJoyArtic, 3);
    stopIntake_3.whenPressed(new SetIntakeRollerPower(0));
    
    armOffset_11 = new JoystickButton(driveJoyArtic, 11);
    armOffset_11.whenPressed(new SetArmPosition(SuperstructureConstants.kArmOffsetPos));
//    armHorizontal_9 = new JoystickButton(driveJoyArtic, 9);
//    armHorizontal_9.whenPressed(new SetArmPosition(SuperstructureConstants.kArmHorizontalPos));
//    armVertical_7 = new JoystickButton(driveJoyArtic, 7);
//    armVertical_7.whenPressed(new SetArmPosition(SuperstructureConstants.kArmVerticalPos));
    
    forwardsToStow_9 = new JoystickButton(driveJoyArtic, 9);
    forwardsToStow_9.whenPressed(new ForwardsScaleToStow());
    stowToForwards_7 = new JoystickButton(driveJoyArtic, 7);
    stowToForwards_7.whenPressed(new StowToForwardsScale());
    
    stowToBackwards_6 = new JoystickButton(driveJoyArtic, 6);
    stowToBackwards_6.whenPressed(new StowToBackwardsScale());
    backwardsToStow_4 = new JoystickButton(driveJoyArtic, 4);
    backwardsToStow_4.whenPressed(new BackwardsScaleToStow());
    
    wristIntakeWithoutIntake_8 = new JoystickButton(driveJoyArtic, 8);
    wristIntakeWithoutIntake_8.whenPressed(new IntakeWithoutIntaking());
    wristStow_10 = new JoystickButton(driveJoyArtic, 10);
    wristStow_10.whenPressed(new SetWristPosition(Robot.wrist.angleAbsoluteToTicks(90)));
    wristIntake_12 = new JoystickButton(driveJoyArtic, 12);
    wristIntake_12.whenPressed(new StowToIntake());
    
	
	SmartDashboard.putData("Arm Position Vertical", new SetArmPosition(SuperstructureConstants.kArmVerticalPos));
//	SmartDashboard.putData("Arm Position Horizontal", new SetArmPosition(SuperstructureConstants.kArmHorizontalPos));
	SmartDashboard.putData("Arm Position Offset", new SetArmPosition(SuperstructureConstants.kArmOffsetPos));
//	SmartDashboard.putData("Arm Voltage 0", new SetArmVoltage(0));
	SmartDashboard.putData("Arm Reset Encoder", new ResetArmEncoder());
	SmartDashboard.putData("Arm Reset Pigeon", new ResetPigeons());
	
	SmartDashboard.putData("Wrist Reset Encoders", new ResetWristEncoder());
	SmartDashboard.putData("Wrist 0 Percent Output", new SetWristPercentOutput(0));
	SmartDashboard.putData("Wrist 3V", new SetWristVoltage(3));
	SmartDashboard.putData("Wrist 6V", new SetWristVoltage(6));
	SmartDashboard.putData("Wrist 9V", new SetWristVoltage(9));
	SmartDashboard.putData("Wrist 12V", new SetWristVoltage(12));
	SmartDashboard.putData("Wrist -3V", new SetWristVoltage(-3));
	SmartDashboard.putData("Wrist -6V", new SetWristVoltage(-6));
	SmartDashboard.putData("Wrist -9V", new SetWristVoltage(-9));
	SmartDashboard.putData("Wrist -12V", new SetWristVoltage(-12));

	SmartDashboard.putData("Wrist Position Intake", new SetWristPosition(-3309));
	SmartDashboard.putData("Wrist Position 90 Deg", new SetWristPosition(Robot.wrist.angleAbsoluteToTicks(90)));
	SmartDashboard.putData("Wrist Position Score", new SetWristPosition(-7155));
	
	SmartDashboard.putData("Set Positive Arm Voltage", new SetArmVoltage(.75));	
	SmartDashboard.putData("Set Negative Arm Voltage", new SetArmVoltage(-.75));	

//	SmartDashboard.putData("Wrist Position Stow", new SetWristPosition(SuperstructureConstants.kWristStowPos));
//	SmartDashboard.putData("Wrist Position Score", new SetWristPosition(SuperstructureConstants.kWristScoreBackwardsScalePos));
	
//	SmartDashboard.putData("Wrist Position Vertical Upwards", new SetWristPosition(-2500));
//	SmartDashboard.putData("Wrist Position Intake", new SetWristPosition(-5000));
//	SmartDashboard.putData("Wrist Position Downwards", new SetWristPosition(-7300));
	
	SmartDashboard.putData("Calibrate Gyro", new CalibrateGyro());
	SmartDashboard.putData("Reset All Pigeons", new ResetPigeons());
	
	SmartDashboard.putData("Set Intake Power 1", new SetIntakeRollerPower(1));
	SmartDashboard.putData("Set Intake Power -0.5", new SetIntakeRollerPower(-0.5));
	SmartDashboard.putData("Set Intake Power 0", new SetIntakeRollerPower(0));
	
//	SmartDashboard.putData("Superstructure Stow Position", new StowPosition());
//	SmartDashboard.putData("Superstructure Stow to Backwards Scale", new StowToBackwardsScale());
	SmartDashboard.putData("Superstructure Stow to Forwards Scale", new StowToForwardsScale());
	SmartDashboard.putData("Superstructure Forwards Scale to Stow", new ForwardsScaleToStow());
	SmartDashboard.putData("Superstructure Intake", new StowToIntake());
//	
//	SmartDashboard.putData("Reset Gyro", new ResetGyro());
//	SmartDashboard.putData("Reset Drive Encoders", new ResetDriveEncoders());
//	SmartDashboard.putData("Center to right Scale", new CenterToRightScale());
	
	
    }

    /**
     * @return input power from left drive joystick Y (-1.0 to +1.0)
     */
    public double getDriveJoyLeftY() {
//	 return -gamepadJoy.getRawAxis(1);
	return -driveJoyLeft.getY();
    }

    /**
     * @return input power from right drive joystick Y (-1.0 to +1.0)
     */
    public double getDriveJoyRightY() {
//	 return -gamepadJoy.getRawAxis(3);
	return -driveJoyRight.getY();
    }

    /**
     * @return input power from left drive joystick X (-1.0 to +1.0)
     */
    public double getDriveJoyLeftX() {
//	 return gamepadJoy.getRawAxis(0);
	return driveJoyLeft.getX();
    }

    /**
     * @return input power from right drive joystick X (-1.0 to +1.0)
     */
    public double getDriveJoyRightX() {
//	 return gamepadJoy.getRawAxis(2);
	return driveJoyRight.getX();
    }
   

//    /**
//     * @return input throttle from right drive joystick (0 to +1.0)
//     */
//    public double getThrottleR() {
//	return (driveJoyRight.getThrottle() + 1) / 2;
//    }
//
//    /**
//     * @return input throttle from left drive josytick
//     */
//    public double getThrottleL() {
//	return (driveJoyLeft.getThrottle() + 1) / 2;
//    }
//
//    /**
//     * @return if quick turn state in cheesydrive mode
//     */
//    public boolean getQuickTurn() {
//	return quickTurn_1.get();
//    }

}
