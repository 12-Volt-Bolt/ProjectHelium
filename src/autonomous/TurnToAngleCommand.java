package autonomous;

import org.usfirst.frc.team1557.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TurnToAngleCommand extends Command {
	private double angle;
	private double tolerance;
	private boolean isDefenseDrive;

	/**
	 * @param desiredAngle
	 *            The angle to turn to relative to the field
	 * @param timeout
	 *            The timeout
	 * @param absoluteTolerance
	 *            The absolute tolerance allowed in degrees.
	 * @param isDefenseDrive
	 *            Whether or not the defense wheels are down. Probably won't
	 *            make a difference either way.
	 */
	public TurnToAngleCommand(double desiredAngle, double timeout, double absoluteTolerance, boolean isDefenseDrive) {
		requires(Robot.drive);
		this.angle = desiredAngle;
		this.setTimeout(timeout);
		this.tolerance = absoluteTolerance;
		this.isDefenseDrive = isDefenseDrive;
	}

	protected void initialize() {
		Robot.drive.turnOnlyInit(angle);

	}

	protected void execute() {
		Robot.drive.turnOnlyDrive(isDefenseDrive);
	}

	@Override
	protected boolean isFinished() {

		return isTimedOut() || Robot.drive.autonomousTurnPID.getError() <= tolerance;
	}

	protected void end() {
		Robot.drive.autonomousTurnPID.reset();
		Robot.drive.autonomousTurnPID.disable();
	}

	protected void interrupted() {
		end();
	}

}
