
package org.usfirst.frc.team1557.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.text.DecimalFormat;
import java.util.Arrays;

import org.usfirst.frc.team1557.robot.BNO055.opmode_t;
import org.usfirst.frc.team1557.robot.BNO055.vector_type_t;
import org.usfirst.frc.team1557.robot.commands.FODCommand;
import org.usfirst.frc.team1557.robot.subsystems.ClimbSubsystem;
import org.usfirst.frc.team1557.robot.subsystems.DefenseWheelsSubsystem;
import org.usfirst.frc.team1557.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1557.robot.vision.VisionBase;

import com.kauailabs.navx.frc.*;

import autonomous.AutoChooser;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
// TODO: Rid our world of the automagically generated TODOs
public class Robot extends IterativeRobot {

	public static DriveSubsystem drive;
	public static ClimbSubsystem climb;
	public static OI oi;
	public static VisionBase vb = new VisionBase();
	// public static LEDServer ledServer = new LEDServer();
	public static DefenseWheelsSubsystem defense;
	public static AutoChooser autoChooser;
	//public static BNO055 gyro; TODO: hello
	public static AHRS gyro;
	public static Encoder encLeft = new Encoder(new DigitalInput(0), new DigitalInput(1));
	public static Encoder encRight = new Encoder(new DigitalInput(2), new DigitalInput(3));

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
	
	
		 // 58-62: try to "Constructed the NavX Gyro; if the gyro is not, the 
		 // the runtime error will be caught to allow the system to continue"
		 try {
			 gyro = new AHRS(SerialPort.Port.kUSB);
			 
			 gyro.getYaw();
		 } catch (RuntimeException ex) {
			 DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
		 }
		 
		 gyro.reset();
		 gyro.zeroYaw();
		 
		 
		 
		 
		 SmartDashboard.putBoolean("Is Nav-X here", gyro.isConnected());
		
		
		/*	gyro = BNO055.getInstance(opmode_t.OPERATION_MODE_IMUPLUS, vector_type_t.VECTOR_EULER, Port.kOnboard,
				(byte) 0x28);
		// gyro.setMode(opmode_t.OPERATION_MODE_GYRONLY);
		SmartDashboard.putNumber("P", 0.01);
		SmartDashboard.putNumber("I", 0.0001);
		SmartDashboard.putNumber("D", 0.0);

		SmartDashboard.putNumber("Px", 0.005);
		SmartDashboard.putNumber("Ix", 0.00000);
		SmartDashboard.putNumber("Dx", 0.0); */

		SmartDashboard.putBoolean("DefenseDrive", false);

		oi = new OI();
		climb = new ClimbSubsystem();
		drive = new DriveSubsystem();
		defense = new DefenseWheelsSubsystem();
		oi.init();
		vb.start("MainCamera", "10.15.57.90");
		vb.startProcess();
	//	gyro.setOffsetValues(); WHAT IS THE  TODO
	// Maybe change this to drive
		DriveSubsystem.rotationPID.setSetpoint(0);
		// ledServer.init(5801);
		autoChooser = new AutoChooser();

	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		// running = false;
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		DriveSubsystem.rotationPID.disable();
		SmartDashboard.putBoolean("Is Enabled", drive.rotationPID.isEnabled());
		SmartDashboard.putNumber("navX", drive.getGyroAngle());
		DecimalFormat d = new DecimalFormat("0.00");
		SmartDashboard.putNumber("Left Encoder", encLeft.getDistance());
		SmartDashboard.putNumber("Right Encoder", encRight.getDistance());

		// The code below is supposed print information for from the gyro
		// Though must be fixed for navX TODO
		
	/*	SmartDashboard.putString("SystemStatus", "self-test" + gyro.getSystemStatus().self_test_result + "error"
				+ gyro.getSystemStatus().system_error + "status" + gyro.getSystemStatus().system_status);
		SmartDashboard.putBoolean("Is Present", gyro.isSensorPresent());
		SmartDashboard.putString("Rev number",
				"Gyro:" + gyro.getRevInfo().gyro_rev + "    Sys: " + gyro.getRevInfo().sw_rev);
		SmartDashboard.putString("Calibration",
				"Gyro: " + gyro.getCalibration().gyro + "   Sys: " + gyro.getCalibration().sys);

*/

		
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		
		
		
		
		encLeft.reset();
		encRight.reset();
		// 22.58 is one foot 
		//
		
		while((-encLeft.getDistance() > -146.64 && encRight.getDistance() > -146.64) && isEnabled()) {
			
			DriveSubsystem.frontRight.set(-0.4);
			DriveSubsystem.frontLeft.set(0.4);
			DriveSubsystem.rearRight.set(-0.4);
			DriveSubsystem.rearLeft.set(0.4);
					}
		
	
		DriveSubsystem.rotationPID.enable();
		  DriveSubsystem.rotationPID.setOutputRange(-0.2, 0.2);
		 DriveSubsystem.rotationPID.setSetpoint(-45);
		
	 
		 while(Math.abs(DriveSubsystem.rotationPID.getError()) > 2 && isEnabled()) {
			  
			 double  r = DriveSubsystem.rotationPID.get(); 

				DriveSubsystem.frontRight.set(r);
				DriveSubsystem.frontLeft.set(r);
				DriveSubsystem.rearRight.set(r);
				DriveSubsystem.rearLeft.set(r);
		 }
		 
		
		 encLeft.reset();
		encRight.reset();
		
while((-encLeft.getDistance() > -100.8   && encRight.getDistance() > -100.8) && isEnabled()) {
			
			    
			
			DriveSubsystem.frontRight.set(-0.4);
			DriveSubsystem.frontLeft.set(0.4);
			DriveSubsystem.rearRight.set(-0.4);
			DriveSubsystem.rearLeft.set(0.4);
		//	Thread.yield();
			
			
			
				}
		




		
		
		
		
		

		
		
	/*	while(encLeft.getDistance() > -225.8 && encRight.getDistance() > -225.8) {
			
			
			DriveSubsystem.frontRight.set(-0.2);
			DriveSubsystem.frontLeft.set(0.2);
			DriveSubsystem.rearRight.set(-0.2);
			DriveSubsystem.rearLeft.set(0.2);
		//	DriveSubsystem.rearRight.set(-0.2);
			//DriveSubsystem.rearLeft.set(0.2);
			Thread.yield();
		}
		
		
		
		
		
		
		/*while (encLeft.getRaw() < -3600 && encRight.getRaw() < -3600) {
			
			DriveSubsystem.frontRight.set(-0.2);
			DriveSubsystem.frontLeft.set(0.2);
			
		}
		
		*/
		
		DriveSubsystem.frontRight.set(0);
		DriveSubsystem.frontLeft.set(0);
		DriveSubsystem.rearRight.set(0);
		DriveSubsystem.rearLeft.set(0);
		
		
		
	//	drive.rotationPID.setSetpoint(0);
	//	gyro.setOffsetValues(); TODO
	//	autoChooser.choose(); TODO
	  	
	 //     while (isOperatorControl() && isEnabled()) {
	         
	    /*	  if ( OI.mainJoy.getRawButton(0)) {
	            gyro.reset();
 	            gyro.zeroYaw();
	    	  }    */
 	    	  
	//      }

	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		//Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops when
		// teleop starts. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		// running = true;
		drive.rotationPID.setSetpoint(0);
//		gyro.setOffsetValues(); // TODO: Remove this at competition!
		drive.rotationPID.setSetpoint(0);
		drive.initDefaultCommand();
		climb.initDefaultCommand();
		

		// ledServer.sendData("Let's hope this works!");
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
	
		Scheduler.getInstance().run();
		if (OI.mainJoy.getRawButton(RobotMap.yButtonID) == false) {
			if (drive.getCurrentCommand().getName() != "FODDrive" && drive.getCurrentCommand() != null) {
				new FODCommand("FODDrive").start();
			}
		}
		SmartDashboard.putNumber("NavX", drive.getGyroAngle());
		DecimalFormat d = new DecimalFormat("0.00");
		SmartDashboard.putNumber("Encoder Raw Data Left", Robot.encLeft.getRaw());
		SmartDashboard.putNumber("Encoder Raw Data Right", Robot.encRight.getRaw());

		

		// Same here  
		/*	SmartDashboard.putString("SystemStatus", "self-test: " + gyro.getSystemStatus().self_test_result + "error: "
				+ gyro.getSystemStatus().system_error + "status: " + gyro.getSystemStatus().system_status);
		SmartDashboard.putBoolean("Is Present", gyro.isConnected());
E

*/
		

	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}

}
