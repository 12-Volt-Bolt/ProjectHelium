package autonomous;

import org.usfirst.frc.team1557.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StrafeCommand extends Command {

	double to;
	/*
	 * double left = -1.0; double right = 1.0;
	 */
	double si;

	public StrafeCommand(double timeOut, double strafeInput) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.drive);
		to = timeOut;
		si = strafeInput;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.drive.turnOnlyInit(Robot.gyro.pidGet());
		this.setTimeout(to);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.drive.mecanumDrive(si, 0, Robot.drive.autonomousTurnPID.get());

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return this.isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drive.autonomousTurnPID.reset();
		Robot.drive.autonomousTurnPID.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
