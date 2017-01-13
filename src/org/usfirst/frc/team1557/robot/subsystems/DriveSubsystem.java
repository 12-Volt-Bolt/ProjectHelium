package org.usfirst.frc.team1557.robot.subsystems;

import org.usfirst.frc.team1557.robot.commands.MecanumDriveCommand;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

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

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new MecanumDriveCommand());
    }
    
    // add r to right subtract r from left
    
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
    	
    	if ( Math.abs(fr) > highestValue ) {
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
    	rearRight.set(-rr/2);
    	rearLeft.set(-rl/2);
    	frontLeft.set(-fl);
    	
    }
    
    public void defenseDrive(double leftSpeed, double rightSpeed) {
    	
    	frontRight.set(rightSpeed);
    	frontLeft.set(leftSpeed);
    	rearRight.set(rightSpeed);
    	rearLeft.set(leftSpeed);
    	
    }
}

