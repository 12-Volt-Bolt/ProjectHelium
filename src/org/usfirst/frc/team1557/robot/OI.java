package org.usfirst.frc.team1557.robot;

import org.usfirst.frc.team1557.robot.commands.ClimbCommand;
import org.usfirst.frc.team1557.robot.commands.DefenseDriveCommand;
import org.usfirst.frc.team1557.robot.commands.GyroResetCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	// During mecanum drive, only mainJoy will be used. During defensive
	// driving, tank drive will be used, in which we will use both mainJoy and
	// defenseJoy;

	// mainJoy is the twisty one for mecanum drive. It will be one the right
	// side.
	public static Joystick mainJoy = new Joystick(RobotMap.mainJoyID);
	public static Joystick defenseJoy = new Joystick(RobotMap.defenseJoyID);
	public static JoystickButton gyroResetButton = new JoystickButton(mainJoy, RobotMap.gyroResetButtonID);
	public static JoystickButton climbButton = new JoystickButton(mainJoy, RobotMap.climbButtonID);
	public static JoystickButton defenseDriveButton = new JoystickButton(defenseJoy, RobotMap.defenseDriveButtonID);
	
	
	// defenseJoy is a normal joystick for when we lower a wheel for defensive
	// driving. It will only be used for defensive driving mode. It will be on
	// the left side.

	/**
	 * Returns the angle in degrees (Not Radians)
	 * 
	 * @param X
	 * @param Y
	 * @return
	 */
	public static double getDegrees(double X, double Y) {
		return Math.atan2(X, -Y) * (180 / Math.PI);

	}

	/**
	 * Returns the magnitude of the given joystick <u><b>hopefully</b></u>
	 * <sup>TM</sup> with a maximum of 1 and a minimum of 0
	 * 
	 * @param joy
	 * @param xAxisID
	 * @param yAxisID
	 * @return
	 */
	public static double getMagnitude(Joystick joy, int xAxisID, int yAxisID) {
		return Math.sqrt(Math.pow(joy.getRawAxis(xAxisID), 2) + Math.pow(joy.getRawAxis(yAxisID), 2));
	}
	
	public void init() {
		
		climbButton.whileHeld(new ClimbCommand());
		gyroResetButton.whileHeld(new GyroResetCommand());
		defenseDriveButton.whenPressed(new DefenseDriveCommand());
		
	}

}
