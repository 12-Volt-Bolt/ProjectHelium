package org.usfirst.frc.team1557.robot.subsystems;

import java.text.DecimalFormat;

import org.usfirst.frc.team1557.robot.BNO055;
import org.usfirst.frc.team1557.robot.BNO055.opmode_t;
import org.usfirst.frc.team1557.robot.BNO055.vector_type_t;
import org.usfirst.frc.team1557.robot.OI;
import org.usfirst.frc.team1557.robot.Robot;
import org.usfirst.frc.team1557.robot.RobotMap;
import org.usfirst.frc.team1557.robot.commands.FODCommand;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogOutput;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * :cactus: :camel:
 */
public class DriveSubsystem extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private final double CONVERSION_VOLTAGE = (5d / 5120d);
	public static CANTalon frontRight = new CANTalon(RobotMap.frontRightMotorID);
	public static CANTalon frontLeft = new CANTalon(RobotMap.frontLeftMotorID);
	public static CANTalon rearRight = new CANTalon(RobotMap.rearRightMotorID);
	public static CANTalon rearLeft = new CANTalon(RobotMap.rearLeftMotorID);

	public static CANTalon driveChange = new CANTalon(0);

	public static CANTalon defenseRight = new CANTalon(RobotMap.defenseRightMotorID);
	public static CANTalon defenseLeft = new CANTalon(RobotMap.defenseLeftMotorID);
	public static PIDController rotationPID;
	public static PIDController xPlanePID;
	public static AnalogInput ultraSensor = new AnalogInput(3);

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());

		// Take this, Natalie.
		// setDefaultCommand(new MecanumDriveCommand());
		setDefaultCommand(new FODCommand("FODCommand"));
		// setDefaultCommand(new EncoderDriveCommand());

	}

	// add r to right subtract r from left
	public DriveSubsystem() {
		init();

	}

	private void init() {

		// gyro.calibrate();
		rotationPID = new PIDController(SmartDashboard.getNumber("P", 0.05), SmartDashboard.getNumber("I", 0.000001),
				SmartDashboard.getNumber("D", 0.01), 0, Robot.gyro, new PIDOutput() {

					@Override
					public void pidWrite(double output) {
						SmartDashboard.putNumber("pid out", output);
					}
				});
		xPlanePID = new PIDController(SmartDashboard.getNumber("P", 0.05), SmartDashboard.getNumber("I", 0.000001),
				SmartDashboard.getNumber("D", 0.01), 0, new PIDSource() {
					PIDSourceType k = PIDSourceType.kDisplacement;

					@Override
					public void setPIDSourceType(PIDSourceType pidSource) {

						k = pidSource;

					}

					@Override
					public double pidGet() {
						return /* Robot.vb.getDistanceOff() */ 0;
					}

					@Override
					public PIDSourceType getPIDSourceType() {

						return k;
					}
				}, new PIDOutput() {

					@Override
					public void pidWrite(double output) {
					}
				});
		xPlanePID.enable();
		xPlanePID.setAbsoluteTolerance(10);
		rotationPID.setContinuous();
		rotationPID.enable();
		rotationPID.setAbsoluteTolerance(6);
	}

	public static double getGyroAngle() {

		double gyroAngle = Robot.gyro.pidGet();

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
		rearRight.set(-rr);
		rearLeft.set(-rl);
		frontLeft.set(-fl);

	}

	public void defenseDrive(double leftSpeed, double rightSpeed) {

		frontRight.set(rightSpeed);
		frontLeft.set(leftSpeed);
		rearRight.set(rightSpeed);
		rearLeft.set(leftSpeed);
		// Natalie, the value is .68; however, as you mentioned, you should be
		// multiplying by the fractions to avoid the small rounding error.
		defenseRight.set(rightSpeed * .8 * .85);
		defenseLeft.set(leftSpeed * .8 * .85);

	}

	public void fodDrive(Joystick mainJoy, int xAxisMain, int yAxisMain, Joystick altJoy, int xAxisAlt, int yAxisAlt,
			boolean rotationRelativeToJoystick) {
		rotationPID.enable();
		rotationPID.setAbsoluteTolerance(2);
		double[] output = output(mainJoy, xAxisMain, yAxisMain);
		double r = 0;
		if (Math.abs(altJoy.getRawAxis(xAxisAlt)) > 0.1) {
			r = altJoy.getRawAxis(xAxisAlt);
			rotationPID.setSetpoint(getGyroAngle());
		} else {
			boolean buttonPressed = true; // Checks if any of the required
											// buttons were pressed
			if (mainJoy.getRawButton(RobotMap.xButtonID))// X
				rotationPID.setSetpoint(60); // Left
			else if (mainJoy.getRawButton(RobotMap.aButtonID)) // A
				rotationPID.setSetpoint(0); // Center
			else if (mainJoy.getRawButton(RobotMap.bButtonID)) // B
				rotationPID.setSetpoint(-60); // Right
			else
				// Button was not pressed if this code runs
				buttonPressed = false;
			// Don't change the y speed of the robot
			if (buttonPressed) {
				if (Math.abs(rotationPID.getError()) <= 1) {
					if (!xPlanePID.isEnabled()) {
						xPlanePID.enable();
					}
					xPlanePID.setSetpoint(/* Robot.vb.getDistanceOff() */0);
					// Limit the driver to only y movement. Now relative to the
					// robot, not the field.
					// TODO: Set the speeds of the x according to the
					// leftRightPID
					output[0] = xPlanePID.get();
					// Don't change the y

					output[1] = -mainJoy.getRawAxis(yAxisMain);
				} else {
					// If the button was pressed but we aren't within the
					// deadzone, 0 out the y speed. Also 0 out the x.
					if (xPlanePID.isEnabled()) {
						xPlanePID.disable();
					}
					output[1] = 0;
					output[0] = 0;
				}
			} else {
				// If the button was not pressed,

			}
			r = rotationPID.get();
		}
		SmartDashboard.putNumber("Voltage output", ultraSensor.getVoltage());
		SmartDashboard.putNumber("Distance", ultraSensor.getVoltage() / CONVERSION_VOLTAGE);
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

		frontRight.set(fr);
		rearRight.set(rr);
		rearLeft.set(-rl);
		frontLeft.set(-fl);
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

	double speed = 0;

	public void encoderTest() {

		defenseRight.setProfile(0);
		defenseRight.reverseOutput(true);
		defenseRight.setPID(0.1, 0.0, 0);
		defenseRight.setAllowableClosedLoopErr(0);
		defenseRight.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		defenseRight.setEncPosition(0);
		defenseRight.changeControlMode(TalonControlMode.Position);
		defenseRight.setSetpoint(defenseRight.getSetpoint() + 1);
		System.out.println(defenseRight.getEncPosition());

	}

	public PIDController leftPID;
	public PIDController rightPID;

	/**
	 * Used to initialize the left and right encoder PIDControllers using a
	 * custom PIDSource and custom PIDOutput
	 * 
	 * @param distance
	 *            The distance to travel in inches
	 */
	public void initEncoderDrive(double distance) {
		// Convert inches to rotations of a wheel.
		double rotations = distance / 31.42; // 31.42 is the cirvumference of
												// the center tires.
		leftPID = new PIDController(0, 0, 0, 0, new PIDSource() {
			PIDSourceType p = PIDSourceType.kDisplacement;

			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				this.p = pidSource;
			}

			@Override
			public double pidGet() {
				return defenseLeft.getEncPosition();
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return p;
			}
		}, new PIDOutput() {

			@Override
			public void pidWrite(double output) {
				// We can just call pid.get() instead of storing the output
			}
		});

		rightPID = new PIDController(0, 0, 0, 0, new PIDSource() {
			PIDSourceType t = PIDSourceType.kDisplacement;

			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				t = pidSource;
			}

			@Override
			public double pidGet() {
				return defenseRight.getEncPosition();
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return t;
			}
		}, new PIDOutput() {

			@Override
			public void pidWrite(double output) {
				// We can just call pid.get() instead of storing the output
			}
		});

		leftPID.setSetpoint(rotations);
		rightPID.setSetpoint(rotations);
		leftPID.enable();
		rightPID.enable();
		leftPID.setAbsoluteTolerance(1 / 16);
		rightPID.setAbsoluteTolerance(1 / 16);
		;
	}

	/**
	 * Call in the execute method to constantly set the speeds of all the motors
	 * using the left and right encoder PIDControllers
	 */
	public void encoderDrive() {
		// Set the speed of the defense wheels while taking into account the
		// cirvumference difference and the different gear ratios.
		defenseRight.set(rightPID.get() * .85 * .8);
		defenseLeft.set(leftPID.get() * .85 * .8);
		// set the speed of the right mecanum wheels using the relevant PID
		frontRight.set(rightPID.get());
		rearRight.set(rightPID.get());
		// set the speed of the left mecanum wheels using the relevant PID
		rearLeft.set(leftPID.get());
		frontLeft.set(leftPID.get());
	}

	public void turnOnlyDrive(boolean isDefenseDrive) {
		if (isDefenseDrive) {
			defenseRight.set(-autonomousTurnPID.get() * .85 * .8);
			defenseLeft.set(autonomousTurnPID.get() * .85 * .8);
		}
		// set the speed of the right mecanum wheels using the relevant PID
		frontRight.set(-autonomousTurnPID.get());
		rearRight.set(-autonomousTurnPID.get());
		// set the speed of the left mecanum wheels using the relevant PID
		rearLeft.set(autonomousTurnPID.get());
		frontLeft.set(autonomousTurnPID.get());
	}

	public PIDController autonomousTurnPID;

	/**
	 * @param setpoint
	 *            The rotation to turn to relative to the field.
	 */
	public void turnOnlyInit(double setpoint) {
		autonomousTurnPID = new PIDController(0, 0, 0, Robot.gyro, new PIDOutput() {

			@Override
			public void pidWrite(double output) {
				// Again, we do not need to use this.

			}
		});
		autonomousTurnPID.setSetpoint(setpoint);
		autonomousTurnPID.isEnabled();
	}

}
