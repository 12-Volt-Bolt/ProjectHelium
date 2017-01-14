package org.usfirst.frc.team1557.robot.commands;

import org.usfirst.frc.team1557.robot.OI;
import org.usfirst.frc.team1557.robot.RobotMap;
import static org.usfirst.frc.team1557.robot.RobotMap.*;
import static org.usfirst.frc.team1557.robot.Robot.*;

import edu.wpi.first.wpilibj.command.Command;

public class FODCommand extends Command {
	public FODCommand() {
		requires(drive);
	}

	@Override
	protected boolean isFinished() {

		return false;
	}

	protected void execute() {
		// double desiredAngle = 0;
		// desiredAngle = Robot.drive.getGyroAngle();
		// desiredAngle += OI.mainJoy.getDirectionDegrees();
		// desiredAngle *= (Math.PI / 180);
		// TODO: Get rid of magic numbers.
		drive.fodDrive(OI.mainJoy, mainJoyXAxisID, mainJoyYAxisID, OI.mainJoy, 4, 5);
	}

}
