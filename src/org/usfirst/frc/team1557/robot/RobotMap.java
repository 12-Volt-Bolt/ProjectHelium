package org.usfirst.frc.team1557.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	
	public static int mainJoyID = 0;
	public static int defenseJoyID = 1;
	
	public static int mainJoyYAxisID = 1;
	public static int mainJoyXAxisID = 0;
	public static int mainJoyZAxisID = 3;
	
	public static int gyroResetButtonID = 1;
	public static int climbButtonID = 2;
	
	public static int defenseJoyYAxisID = 1;
	public static int defenseDriveButtonID = 3;
	
	public static int frontRightMotorID = 2;
	public static int frontLeftMotorID = 3;
	public static int rearRightMotorID = 1;
	public static int rearLeftMotorID = 0;
	
	/** 
	 * We don't actually know these IDs yet.
	 */
	public static int defenseRightMotorID = 6;
	/** 
	 * We don't actually know these IDs yet.
	 */
	public static int defenseLeftMotorID = 5;
}
