package autonomous;

import org.usfirst.frc.team1557.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DistanceCommand extends Command {
	private double distance;
	private double tolerance;

	/**
	 * @param distance
	 *            The distance (in inches) to travel
	 * @param timeout
	 *            The maximum amount of time allowed to run this command
	 * @param absoluteTolerance
	 *            The tolerance allowed when running. If the wheels get within
	 *            the tolerance, the command will stop.
	 */
	public DistanceCommand(double distance, double timeout, double absoluteTolerance) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.drive);
		this.distance = distance;
		this.setTimeout(timeout);
		this.tolerance = absoluteTolerance;
	}

	// It initializes
	protected void initialize() {
		Robot.drive.initEncoderDrive(distance);
	}

	protected void execute() {
		Robot.drive.encoderDrive();
	}

	// Stop running if the command is timedOut or if both pids are within their
	// margin of error.
	protected boolean isFinished() {
		return isTimedOut()
				|| (Robot.drive.leftPID.getError() <= tolerance && Robot.drive.rightPID.getError() <= tolerance);
	}

	// After we have either timedOut or made it to our setpoint, reset both
	// PIDs(so they can be used again) and disable them.
	protected void end() {
		Robot.drive.leftPID.reset();
		Robot.drive.rightPID.reset();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
