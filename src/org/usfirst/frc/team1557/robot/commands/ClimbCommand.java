package org.usfirst.frc.team1557.robot.commands;

import org.usfirst.frc.team1557.robot.OI;
import org.usfirst.frc.team1557.robot.Robot;
import org.usfirst.frc.team1557.robot.RobotMap;
import org.usfirst.frc.team1557.robot.subsystems.ClimbSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimbCommand extends Command {

	public ClimbCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.climb);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (OI.mainJoy.getRawAxis(RobotMap.climbUpButtonID) >= .3) {
			Robot.climb.climbUp();
		} else if (OI.mainJoy.getRawAxis(RobotMap.climbDownButtonID) >= .3) {
			Robot.climb.climbDown();
		} else {
			Robot.climb.stopClimb();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.climb.stopClimb();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
