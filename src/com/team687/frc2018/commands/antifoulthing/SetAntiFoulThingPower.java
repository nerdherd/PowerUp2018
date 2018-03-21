package com.team687.frc2018.commands.antifoulthing;

import com.team687.frc2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SetAntiFoulThingPower extends Command {

    private double m_power;

    public SetAntiFoulThingPower(double power) {
	m_power = power;

	requires(Robot.antiFoulThing);
    }

    protected void initialize() {
    }

    protected void execute() {
	Robot.antiFoulThing.setPower(m_power);
    }

    protected boolean isFinished() {
	return false;
    }

    protected void end() {
	Robot.antiFoulThing.setPower(0);
    }

    protected void interrupted() {
	end();
    }
}
