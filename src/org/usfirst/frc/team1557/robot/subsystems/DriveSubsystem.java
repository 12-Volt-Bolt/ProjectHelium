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
    	
    	double fr = y + r + x;
    	double fl = y - r - x;
    	double rl = y + r + x;
    	double rr = y - r - x;
    
    	double highestValue = 1;
    	
    	if ( fr > highestValue ) {
    		highestValue = fr;
    	} 
    	if (fl > highestValue) {
    		highestValue = fl;
    	}
    	if (rl > highestValue) {
    		highestValue = rl;
    	}
    	if (rr > highestValue) {
    		highestValue = rr;
    	}
    	
    	fr = fr / highestValue;
    	fl = fl / highestValue;
    	rl = rl / highestValue;
    	rr = rr / highestValue;
    	
    	frontRight.set(fr);
    	rearRight.set(rr);
    	rearLeft.set(rl);
    	frontLeft.set(fl);
    	
    }
    
    public void defenseDrive(double leftSpeed, double rightSpeed) {
    	
    	frontRight.set(rightSpeed);
    	frontLeft.set(leftSpeed);
    	rearRight.set(rightSpeed);
    	rearLeft.set(leftSpeed);
    	
    }
}

