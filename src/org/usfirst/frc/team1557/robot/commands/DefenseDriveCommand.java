package org.usfirst.frc.team1557.robot.commands;

import org.usfirst.frc.team1557.robot.OI;
import org.usfirst.frc.team1557.robot.Robot;
import org.usfirst.frc.team1557.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DefenseDriveCommand extends Command {

	public DefenseDriveCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.drive);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		Robot.drive.defenseDrive(OI.defenseJoy.getRawAxis(RobotMap.defenseJoyYAxisID), RobotMap.mainJoyYAxisID);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drive.defenseDrive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
