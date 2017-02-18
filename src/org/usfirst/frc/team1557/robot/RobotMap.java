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

	// public static int defenseJoyID;
	//
	// public static int defenseJoyYAxisID;
	// public static int defenseDriveButtonOneID = 5;
	// public static int defenseDriveButtonTwoID = 6;
	// public static int defenseWheelsDownButtonID;
	// public static int defenseWheelsUpButtonID;
	//
	// {
	// whichController();
	// }
	//
	// void whichController() {
	//
	// if (/* we are using the video game controller */ true) {
	// defenseJoyID = 0;
	// defenseJoyYAxisID = 5;
	// defenseDriveButtonOneID = 5;
	// defenseDriveButtonTwoID = 6;
	//
	// defenseWheelsDownButtonID = 0;
	// defenseWheelsUpButtonID = 0;
	//
	// } else if (/* we are using joysticks */ true) {
	//
	// defenseJoyID = 1;
	// defenseJoyYAxisID = 1;
	// defenseDriveButtonOneID = 1;
	// defenseDriveButtonTwoID = 2;
	//
	//
	// }
	// }

	public static int mainJoyZAxisID = 15;

	public static int mainJoyID = 0;

	public static int leftXAxisID = 0;
	public static int leftYAxisID = 1;

	public static int leftTriggerAxisID = 2; // climb down
	public static int rightTriggerAxisID = 3; // climb up

	public static int rightXAxisID = 4;
	public static int rightYAxisID = 5;

	public static int aButtonID = 1;
	public static int bButtonID = 2; // line up with right gear scoring
	public static int xButtonID = 3; // line up with left gear scoring
	public static int yButtonID = 4;
	public static int leftBumperID = 5; // hold for defense wheels
	public static int rightBumperID = 6; // hold for defense wheels

	public static int frontRightMotorID = 12;
	public static int frontLeftMotorID = 13;
	public static int rearRightMotorID = 21;
	public static int rearLeftMotorID = 11;

	public static int defenseRightMotorID = 2;
	public static int defenseLeftMotorID = 22;

	public static int defenseToggleMotorLeft = 6;
	public static int defenseToggleMotorRight = 7;

	public static int climbMotorID = 1;

	public static long timeSinceLowerOrLift = 0;
}
