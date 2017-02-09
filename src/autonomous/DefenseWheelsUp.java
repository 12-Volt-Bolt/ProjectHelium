package autonomous;

import org.usfirst.frc.team1557.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DefenseWheelsUp extends Command {

    public DefenseWheelsUp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.defense);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	this.setTimeout(5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.defense.wheelsUp();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.defense.wheelToggleMotorLeft.set(0);
    	Robot.defense.wheelToggleMotorRight.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
