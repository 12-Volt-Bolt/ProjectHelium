
package org.usfirst.frc.team1557.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.text.DecimalFormat;

import org.usfirst.frc.team1557.robot.subsystems.ClimbSubsystem;
import org.usfirst.frc.team1557.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team1557.robot.vision.VisionBase;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	// boolean running = false;
	// Thread t = new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// while (true)
	// while (running) {
	// vb.process();
	// try {
	// Thread.sleep(20);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }
	// });
	public static DriveSubsystem drive;
	public static ClimbSubsystem climb;
	public static OI oi;
	VisionBase vb = new VisionBase();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		SmartDashboard.putNumber("P", 0.04);
		SmartDashboard.putNumber("I", 0.00001);
		SmartDashboard.putNumber("D", 0.0);
		oi = new OI();
		climb = new ClimbSubsystem();
		drive = new DriveSubsystem();
		oi.init();
		drive.gyroReset();
		// vb.start("MainCamera", "10.15.57.90");
		// vb.startProcess();

		// t.start();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		drive.gyroReset();
		// running = false;
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
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
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		// running = true;
		drive.initDefaultCommand();
		DriveSubsystem.rotationPID.setPID(SmartDashboard.getNumber("P", 0.01), SmartDashboard.getNumber("I", 0.01),
				SmartDashboard.getNumber("D", 0.01));

	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		// System.out.println("Post Porkcessing is a go!");
		Scheduler.getInstance().run();
		// vb.process();
		// vb.process();
		SmartDashboard.putString("Gyro Angle in Degress", new DecimalFormat("0.00").format(drive.getGyroAngle()));

	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
