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
	public static int defenseJoyID;

	public static int defenseJoyYAxisID;
	public static int defenseDriveButtonOneID;
	public static int defenseDriveButtonTwoID;
	public static int defenseWheelsDownButtonID;
	public static int defenseWheelsUpButtonID;

	void whichController() {

		if (/* we are using the video game controller */ true) {
			defenseJoyID = 0;
			defenseJoyYAxisID = 5;
			defenseDriveButtonOneID = 5;
			defenseDriveButtonTwoID = 6;
			
			defenseWheelsDownButtonID = 0;
			defenseWheelsUpButtonID = 0;

		} else if (/* we are using joysticks */ true)	{
		
			defenseJoyID = 1;
			defenseJoyYAxisID = 1;
			defenseDriveButtonOneID = 1;
			defenseDriveButtonTwoID = 2;
			
			defenseWheelsDownButtonID = 0;
			defenseWheelsUpButtonID = 0;

		}
	}

	public static int mainJoyYAxisID = 1;
	public static int mainJoyXAxisID = 0;
	public static int mainJoyZAxisID = 3;

	public static int gyroResetButtonID = 1;
	public static int climbUpButtonID = 2;
	public static int climbDownButtomID = 3;

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
	
	public static int defenseToggleMotorLeft = 0;
	public static int defenseToggleMotorRight = 0;
}
