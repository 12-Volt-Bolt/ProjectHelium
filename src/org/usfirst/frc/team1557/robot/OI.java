package org.usfirst.frc.team1557.robot;

import org.usfirst.frc.team1557.robot.commands.AlignToLoadCommand;
import org.usfirst.frc.team1557.robot.commands.ClimbCommand;

import autonomous.DefenseWheelsDownCommand;
import autonomous.DefenseWheelsUp;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.WaitCommand;

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
	// public static Joystick defenseJoy = new Joystick(RobotMap.defenseJoyID);
	// public static JoystickButton defenseDriveButtonOne = new
	// JoystickButton(mainJoy, RobotMap.defenseDriveButtonOneID);
	// public static JoystickButton defenseDriveButtonTwo = new
	// JoystickButton(mainJoy, RobotMap.defenseDriveButtonTwoID);

	// public static JoystickButton defenseWheelsDownButton = new
	// JoystickButton(defenseJoy,
	// RobotMap.defenseWheelsDownButtonID);
	// public static JoystickButton defenseWheelsUpButton = new
	// JoystickButton(defenseJoy,
	// RobotMap.defenseWheelsUpButtonID);

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
		new Trigger() {

			@Override
			public boolean get() {
				return (/* Robot.defense.limitSwitch.get() && */ (OI.mainJoy.getRawButton(RobotMap.rightBumperID)));
			}
		}.whenActive(new DefenseWheelsDownCommand());

		new Trigger() {

			@Override
			public boolean get() {
				return (/* !Robot.defense.limitSwitch.get() && */OI.mainJoy.getRawButton(RobotMap.leftBumperID));
			}
		}.whenActive(new DefenseWheelsUp());
		// new JoystickButton(mainJoy, RobotMap.yButtonID).whenPressed(new
		// AlignToLoadCommand("AlignCommand"));
	}

}
