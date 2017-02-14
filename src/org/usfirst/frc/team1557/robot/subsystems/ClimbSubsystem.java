package org.usfirst.frc.team1557.robot.subsystems;

import org.usfirst.frc.team1557.robot.Robot;
import org.usfirst.frc.team1557.robot.RobotMap;
import org.usfirst.frc.team1557.robot.commands.ClimbCommand;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ClimbSubsystem extends Subsystem {

	public CANTalon climbMotor = new CANTalon(RobotMap.climbMotorLeftID);

	// Put methods for controlling this subsystem	
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new ClimbCommand());
	}
	
	//TODO: Ask Nic if we are using one or two motor controllers for climbing. Then based on that, fix this. If we are using two, we need to see which motors need to be positive and negative for climbing up and down. If we are using one, they should fix the opoosite problem in wiring and this ends up simpler.

	/**
	 * Sets the left and right climb motors to climb up. One is negative and one
	 * is positive because they are facing oppposite each other. We still need
	 * to actually see which is which. There's also the possibility that they
	 * both might be controlled by one and they have to make them opposite in
	 * electrical.
	 */
	public void climbUp() {
		Robot.climb.climbMotor.set(1.0);
	}

	public void climbDown() {
		Robot.climb.climbMotor.set(-1.0);
	}

	public void stopClimb() {
		Robot.climb.climbMotor.set(0);
	}

}
