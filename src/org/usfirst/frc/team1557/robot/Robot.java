
package org.usfirst.frc.team1557.robot;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.IterativeRobot;
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
	public static BNO055 gyro;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		gyro = BNO055.getInstance(opmode_t.OPERATION_MODE_IMUPLUS, vector_type_t.VECTOR_EULER, Port.kOnboard,
				(byte) 0x28);
		// gyro.setMode(opmode_t.OPERATION_MODE_GYRONLY);
		SmartDashboard.putNumber("P", 0.01);
		SmartDashboard.putNumber("I", 0.00000);
		SmartDashboard.putNumber("D", 0.0);
		oi = new OI();
		climb = new ClimbSubsystem();
		drive = new DriveSubsystem();
		defense = new DefenseWheelsSubsystem();
		oi.init();
		vb.start("MainCamera", "10.15.57.90");
		vb.startProcess();
		gyro.setOffsetValues();
		drive.rotationPID.setSetpoint(0);
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
		SmartDashboard.putNumber("BNO055", drive.getGyroAngle());
		DecimalFormat d = new DecimalFormat("0.00");
		SmartDashboard.putString("SystemStatus", "self-test" + gyro.getSystemStatus().self_test_result + "error"
				+ gyro.getSystemStatus().system_error + "status" + gyro.getSystemStatus().system_status);
		SmartDashboard.putBoolean("Is Present", gyro.isSensorPresent());
		SmartDashboard.putString("Rev number",
				"Gyro:" + gyro.getRevInfo().gyro_rev + "    Sys: " + gyro.getRevInfo().sw_rev);
		SmartDashboard.putString("Calibration",
				"Gyro: " + gyro.getCalibration().gyro + "   Sys: " + gyro.getCalibration().sys);

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
		drive.rotationPID.setSetpoint(0);
		gyro.setOffsetValues();
		autoChooser.choose();

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
		drive.rotationPID.setSetpoint(0);
		gyro.setOffsetValues(); // TODO: Remove this at competition!
		drive.initDefaultCommand();
		climb.initDefaultCommand();
		DriveSubsystem.rotationPID.setPID(SmartDashboard.getNumber("P", 0.01), SmartDashboard.getNumber("I", 0.01),
				SmartDashboard.getNumber("D", 0.01));
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
		SmartDashboard.putNumber("BNO055", drive.getGyroAngle());
		DecimalFormat d = new DecimalFormat("0.00");
		SmartDashboard.putString("SystemStatus", "self-test: " + gyro.getSystemStatus().self_test_result + "error: "
				+ gyro.getSystemStatus().system_error + "status: " + gyro.getSystemStatus().system_status);
		SmartDashboard.putBoolean("Is Present", gyro.isSensorPresent());

	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}

}
