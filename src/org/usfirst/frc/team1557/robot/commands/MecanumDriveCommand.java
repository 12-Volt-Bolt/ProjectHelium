package org.usfirst.frc.team1557.robot.commands;

import org.usfirst.frc.team1557.robot.Robot;
import edu.wpi.first.wpilibj.RobotDrive;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;

import static org.usfirst.frc.team1557.robot.RobotMap.*;

import org.usfirst.frc.team1557.robot.OI;

/**
 *
 */


public class MecanumDriveCommand extends Command {
    // PLEASE FIX THE MOTOR ID NUMBERS TODO
	// seriously!
	RobotDrive myDrive = new RobotDrive(13,11,12,21);
	public MecanumDriveCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		

		requires(Robot.drive);
		
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	/* The Commented code below uses Levi's mecanum drive function
		// 	Robot.drive.mecanumDrive(OI.mainJoy.getRawAxis(leftXAxisID), OI.mainJoy.getRawAxis(leftYAxisID), OI.mainJoy.getRawAxis(mainJoyZAxisID));
	//	Robot.drive.mecanumDrive(OI.mainJoy.getRawAxis(leftXAxisID), OI.mainJoy.getRawAxis(leftYAxisID), OI.mainJoy.getRawAxis(mainJoyZAxisID), Robot.gyro.getAngle());
		*/
	 	myDrive.mecanumDrive_Cartesian(OI.mainJoy.getRawAxis(leftXAxisID), OI.mainJoy.getRawAxis(leftYAxisID), OI.mainJoy.getRawAxis(mainJoyZAxisID), Robot.gyro.getAngle());
		
		}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {

		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		
		//Robot.drive.mecanumDrive(0, 0, 0);
		
		myDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
