package org.usfirst.frc.team1557.robot.subsystems;

import java.text.DecimalFormat;

import org.usfirst.frc.team1557.robot.OI;
import org.usfirst.frc.team1557.robot.commands.FODCommand;
import org.usfirst.frc.team1557.robot.commands.MecanumDriveCommand;
import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveSubsystem extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public static CANTalon frontRight = new CANTalon(2);
	public static CANTalon frontLeft = new CANTalon(3);
	public static CANTalon rearRight = new CANTalon(1);
	public static CANTalon rearLeft = new CANTalon(0);
	private ADXRS450_Gyro gyro;
	// PIDController rotationPID = new PIDController(0, 0, 0, 0, gyro, new
	// PIDOutput() {
	//
	// @Override
	// public void pidWrite(double output) {
	// }
	// });

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());

		// Take this, Natalie. //setDefaultCommand(new MecanumDriveCommand());
		setDefaultCommand(new FODCommand());
	}

	// add r to right subtract r from left
	public DriveSubsystem() {

		init();

	}

	private void init() {
		gyro = new ADXRS450_Gyro();
		gyro.calibrate();
	}

	public void gyroReset() {
		gyro.reset();
	}

	public double getGyroAngle() {

		double gyroAngle = gyro.getAngle();

		if (Math.abs(gyroAngle) > 360) {
			gyroAngle = gyroAngle % 360;
		}
		if (Math.abs(gyroAngle) > 180) {
			if (gyroAngle > 0) {
				gyroAngle = gyroAngle - 360;
			} else {
				gyroAngle = 360 + gyroAngle;
			}
		}

		return gyroAngle;
	}

	public void mecanumDrive(double x, double y, double r) {

		if (Math.abs(x) < .09) {
			x = 0;
		}
		if (Math.abs(y) < .09) {
			y = 0;
		}
		if (Math.abs(r) < .09) {
			r = 0;
		}

		double fr = y + r + x;
		double fl = y - r - x;
		double rl = y - r + x;
		double rr = y + r - x;

		double highestValue = 1;

		if (Math.abs(fr) > highestValue) {
			highestValue = Math.abs(fr);
		}

		if (Math.abs(fl) > highestValue) {
			highestValue = Math.abs(fl);
		}
		if (Math.abs(rl) > highestValue) {
			highestValue = Math.abs(rl);
		}
		if (Math.abs(rr) > highestValue) {
			highestValue = Math.abs(rr);
		}

		fr = fr / highestValue;
		fl = fl / highestValue;
		rl = rl / highestValue;
		rr = rr / highestValue;

		fl = fl * (-1);

		frontRight.set(-fr);
		rearRight.set(-rr / 2);
		rearLeft.set(-rl / 2);
		frontLeft.set(-fl);

	}

	public void defenseDrive(double leftSpeed, double rightSpeed) {

		frontRight.set(rightSpeed);
		frontLeft.set(leftSpeed);
		rearRight.set(rightSpeed);
		rearLeft.set(leftSpeed);

	}

	public void fodDrive(Joystick mainJoy, int xAxisMain, int yAxisMain, Joystick altJoy, int xAxisAlt, int yAxisAlt) {
		double[] output = output(mainJoy, xAxisMain, yAxisMain);
		// rotationPID.setSetpoint(OI.getDegrees(altJoy.getRawAxis(xAxisAlt),
		// altJoy.getRawAxis(yAxisAlt)));
		// double r = rotationPID.get();
		DecimalFormat m = new DecimalFormat("0.00");
		SmartDashboard.putString("Output Values", m.format(output[0]) + ":" + m.format(output[1]));
		double r = mainJoy.getRawAxis(xAxisAlt);
		double fr = output[1] + r + output[0];
		double fl = output[1] - r - output[0];
		double rl = output[1] - r + output[0];
		double rr = output[1] + r - output[0];
		double highestValue = 1;
		// (IF) ?THEN :ELSE
		highestValue = (Math.abs(fr) > highestValue) ? Math.abs(fr) : highestValue;
		highestValue = (Math.abs(rr) > highestValue) ? Math.abs(rr) : highestValue;
		highestValue = (Math.abs(fl) > highestValue) ? Math.abs(fl) : highestValue;
		highestValue = (Math.abs(rl) > highestValue) ? Math.abs(rl) : highestValue;

		fr = fr / highestValue;
		fl = fl / highestValue;
		rl = rl / highestValue;
		rr = rr / highestValue;

		fl = -fl;
		frontRight.set(-fr);
		rearRight.set(-rr / 2);
		rearLeft.set(-rl / 2);
		frontLeft.set(-fl);
	}

	private double[] output(Joystick joy, int xAxis, int yAxis) {
		double angle = 0;

		if (Math.abs(joy.getRawAxis(xAxis)) <= 0.09 && Math.abs(joy.getRawAxis(yAxis)) <= 0.09) {
			return new double[] { 0, 0 };
		}
		/*
		 * angle += OI.getDegrees(joy.getRawAxis(xAxis), joy.getRawAxis(yAxis));
		 * 
		 * angle += getGyroAngle(); SmartDashboard.putNumber("halp", angle);
		 * angle *= (Math.PI / 180);
		 */
		// SmartDashboard.putNumber("tots rad dude", angle);
		// double speed = OI.getMagnitude(joy, xAxis, yAxis);
		double cosA = Math.cos(gyro.getAngle() * (Math.PI / 180));
		double sinA = Math.sin(gyro.getAngle() * (Math.PI / 180));
		double[] out = new double[2];
		out[0] = joy.getRawAxis(xAxis) * cosA - joy.getRawAxis(yAxis) * sinA;
		out[1] = joy.getRawAxis(xAxis) * sinA + joy.getRawAxis(yAxis) * cosA;

		// return new double[] { Math.sin(angle) * speed, Math.cos(angle) *
		// speed };
		return out;
	}
}
