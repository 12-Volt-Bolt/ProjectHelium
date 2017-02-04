package org.usfirst.frc.team1557.robot.subsystems;

import org.usfirst.frc.team1557.robot.Robot;
import org.usfirst.frc.team1557.robot.commands.ClimbCommand;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ClimbSubsystem extends Subsystem {
	
	public CANTalon climbMotorOne = new CANTalon(10);
	public CANTalon climbMotorTwo = new CANTalon(11);

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new ClimbCommand());
    }
    
    public void climbUp() {
    	Robot.climb.climbMotorOne.set(1.0);
    	Robot.climb.climbMotorTwo.set(-1.0);
    }
    
    public void climbDown() {
    	Robot.climb.climbMotorOne.set(-1.0);
    	Robot.climb.climbMotorTwo.set(1.0);
    }
    
    public void stopClimb() {
    	Robot.climb.climbMotorOne.set(0);
    	Robot.climb.climbMotorTwo.set(0);
    }
    
}

