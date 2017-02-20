package autonomous;

import org.usfirst.frc.team1557.robot.Robot;
import org.usfirst.frc.team1557.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LeftDefenseDownCommand extends Command {

	public LeftDefenseDownCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		RobotMap.timeSinceLowerOrLift = System.currentTimeMillis();
		this.setTimeout(3);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.defense.wheelToggleMotorLeft.set(-1.0);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return  !Robot.defense.leftLimitSwitch.get() || isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.defense.wheelToggleMotorLeft.set(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
