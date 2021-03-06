package org.usfirst.frc.team1557.robot.subsystems;

import org.usfirst.frc.team1557.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DefenseWheelsSubsystem extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public static CANTalon wheelToggleMotorLeft = new CANTalon(RobotMap.defenseToggleMotorLeft);
	public static CANTalon wheelToggleMotorRight = new CANTalon(RobotMap.defenseToggleMotorRight);
	public static DigitalInput leftLimitSwitch = new DigitalInput(9);
	public static DigitalInput rightLimitSwitch = new DigitalInput(8);

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

}
