package org.usfirst.frc.team1557.robot.subsystems;

import java.text.DecimalFormat;

import org.usfirst.frc.team1557.robot.OI;
import org.usfirst.frc.team1557.robot.RobotMap;
import org.usfirst.frc.team1557.robot.commands.FODCommand;
import org.usfirst.frc.team1557.robot.commands.MecanumDriveCommand;
import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveSubsystem extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public static CANTalon frontRight = new CANTalon(RobotMap.frontRightMotorID);
	public static CANTalon frontLeft = new CANTalon(RobotMap.frontLeftMotorID);
	public static CANTalon rearRight = new CANTalon(RobotMap.rearRightMotorID);
	public static CANTalon rearLeft = new CANTalon(RobotMap.rearLeftMotorID);
	private static ADXRS450_Gyro gyro;
	public static CANTalon driveChange = new CANTalon(0);
	public static CANTalon defenseRight = new CANTalon(RobotMap.defenseRightMotorID);
	public static CANTalon defenseLeft = new CANTalon(RobotMap.defenseLeftMotorID);
	public static PIDController rotationPID;

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
		rotationPID = new PIDController(SmartDashboard.getNumber("P", 0.05), SmartDashboard.getNumber("I", 0.000001),
				SmartDashboard.getNumber("D", 0.01), 0, gyro, new PIDOutput() {

					@Override
					public void pidWrite(double output) {
						SmartDashboard.putNumber("pid out", output);
					}
				});

		rotationPID.setContinuous();
		rotationPID.setInputRange(-180, 180);
		rotationPID.enable();
		rotationPID.setAbsoluteTolerance(6);
	}

	public void gyroReset() {
		rotationPID.reset();
		gyro.reset();
		rotationPID.setSetpoint(getGyroAngle());
	}

	public static double getGyroAngle() {

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

	public void setGyroSetpoint(double d) {
		rotationPID.setSetpoint(d);
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
		defenseRight.set(rightSpeed);
		defenseLeft.set(leftSpeed);

	}

	public void fodDrive(Joystick mainJoy, int xAxisMain, int yAxisMain, Joystick altJoy, int xAxisAlt, int yAxisAlt,
			boolean rotationRelativeToJoystick) {
		rotationPID.enable();
		rotationPID.setAbsoluteTolerance(2);
		System.out.println(rotationPID.isEnabled());
		double[] output = output(mainJoy, xAxisMain, yAxisMain);
		// rotationPID.setSetpoint(OI.getDegrees(altJoy.getRawAxis(xAxisAlt),
		// altJoy.getRawAxis(yAxisAlt)));
		// double r = rotationPID.get();
		SmartDashboard.putNumber("X", mainJoy.getRawAxis(xAxisMain));
		SmartDashboard.putNumber("Y", mainJoy.getRawAxis(yAxisMain));
		DecimalFormat m = new DecimalFormat("0.00");
		SmartDashboard.putString("Output Values", m.format(output[0]) + ":" + m.format(output[1]));
		double r = rotationPID.get();
		if (Math.abs(altJoy.getRawAxis(xAxisAlt)) > 0.09) {
			r = altJoy.getRawAxis(xAxisAlt);
			rotationPID.setSetpoint(getGyroAngle());
		}
		SmartDashboard.putNumber("rot", r);
		SmartDashboard.putNumber("Error Graph", rotationPID.getError());
		double fr = -output[1] + r + output[0];
		double fl = -output[1] - r - output[0];
		double rl = -output[1] - r + output[0];
		double rr = -output[1] + r - output[0];
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

	private double getRotation(double rot) {
		// TODO: Add some form of deadzone for setting new setpoints. Maybe ~5
		// degree allowance.
		return rotationPID.get();
	}

	private double[] output(Joystick joy, int xAxis, int yAxis) {
		double angle = 0;

		if (Math.abs(joy.getRawAxis(xAxis)) <= 0.09 && Math.abs(joy.getRawAxis(yAxis)) <= 0.09) {
			return new double[] { 0, 0 };
		}
		SmartDashboard.putNumber("Joystick", OI.getDegrees(joy.getRawAxis(xAxis), joy.getRawAxis(yAxis)));
		angle += OI.getDegrees(joy.getRawAxis(xAxis), joy.getRawAxis(yAxis));

		angle -= getGyroAngle();
		angle *= (Math.PI / 180);

		SmartDashboard.putNumber("new Angle ", angle * (180 / Math.PI));
		double speed = OI.getMagnitude(joy, xAxis, yAxis);
		// f double cosA = Math.cos(gyro.getAngle() * (Math.PI / 180));
		// f double sinA = Math.sin(gyro.getAngle() * (Math.PI / 180));
		// f double[] out = new double[2];
		// f out[0] = joy.getRawAxis(xAxis) * cosA - joy.getRawAxis(yAxis) *
		// sinA;
		// f out[1] = joy.getRawAxis(xAxis) * sinA + joy.getRawAxis(yAxis) *
		// cosA;

		return new double[] { Math.sin(angle) * speed, Math.cos(angle) * speed };
		// return out;
	}
}
