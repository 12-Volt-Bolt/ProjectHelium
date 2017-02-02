package org.usfirst.frc.team1557.robot;

import org.usfirst.frc.team1557.robot.commands.ClimbCommand;
import org.usfirst.frc.team1557.robot.commands.DefenseDriveCommand;
import org.usfirst.frc.team1557.robot.commands.GyroResetCommand;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
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

		// mainJoy.getName()

		switch (mainJoy.getName()) {

		case "Controller (XBOX 360 for Windows)":
			//varName = 0;
	    
			RobotMap.mainJoyID = 0;
		    RobotMap.defenseJoyID = 1;
		  
		    RobotMap.mainJoyXAxisID = 0;
		    RobotMap.mainJoyYAxisID = 1;
		    RobotMap.mainJoyZAxisID = 3;
		    
		     RobotMap.gyroResetButtonID = 1;
		     RobotMap.climbButtonID = 2;
		     
		     RobotMap.defenseJoyYAxisID = 1;
		     RobotMap.defenseDriveButtonID = 1;
		     
		     RobotMap.frontLeftMotorID = 2;
			 RobotMap.frontRightMotorID = 3;
			 RobotMap.rearLeftMotorID = 0;
			 RobotMap.rearRightMotorID = 1;
			 
			 /** 
				 * We don't actually know these IDs yet.
				 */
				RobotMap.defenseRightMotorID = 4;
				/** 
				 * We don't actually know these IDs yet.
				 */
				RobotMap.defenseLeftMotorID = 5;
		    
		     break;

		
		
		case "Mad Catz V.1 Stick":
			
			
			RobotMap.mainJoyID = 0;
			RobotMap.defenseJoyID = 1;
			
			
			RobotMap.mainJoyXAxisID = 0;
			RobotMap.mainJoyYAxisID = 1;
			RobotMap.mainJoyZAxisID = 3;
			
			
			RobotMap.gyroResetButtonID = 1;
			
		   //  RobotMap.climbButtonID = (Integer) null; 
		    
		    RobotMap.defenseJoyYAxisID = 1;
		    RobotMap.defenseDriveButtonID = 1;
		    
		    RobotMap.frontLeftMotorID = 3;
		   // RobotMap.frontRightMotorID = 2;
		    RobotMap.rearLeftMotorID = 0;
		    RobotMap.rearRightMotorID = 1;
		    
		 
		    /** 
			 * We don't actually know these IDs yet.
			 */
			RobotMap.defenseRightMotorID = 4;
			/** 
			 * We don't actually know these IDs yet.
			 */
			RobotMap.defenseLeftMotorID = 5;
	    
		    
			break;

		case "whatever other name":
			
			break;
		case "you get the idea":
			
			break;
			
			}
                                                                 
		}

}

	/*
	 * Xbox controller haptic feedback code (work in progress).
	 * 
0	 000public static void rumble( RumbleType kLeftRumble, int l, RumbleType
	 * kRightRumble,int r) {
	 * 
	 * rumble(RumbleType.kLeftRumble, l, kLeftRumble, l);
	 * rumble(RumbleType.kRightRumble, r, kRightRumble, r);
	 * 
	 * }
	 * 
	 * public static void rumble(int l, int r, double seconds) {
	 * 
	 * rumble(l, r, seconds);
	 * 
	 * rumbleTimer = new Timer (seconds, false, new TimerUser()) { public void
	 * timer() { rumble(0, 0); }
	 * 
	 * public void timerStop() { rumbleTimer = null; } }
	 * 
	 * Robot.start();
	 * 
	 */
	// }



