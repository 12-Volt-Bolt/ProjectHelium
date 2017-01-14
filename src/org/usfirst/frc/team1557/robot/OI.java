package org.usfirst.frc.team1557.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

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
	public static Joystick mainJoy = new Joystick(3);
	// defenseJoy is a normal joystick for when we lower a wheel for defensive
	// driving. It will only be used for defensive driving mode. It will be on
	// the left side.
	public static Joystick defenseJoy; // = new Joystick(0);

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

}
