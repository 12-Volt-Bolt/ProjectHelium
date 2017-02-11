package autonomous;

import org.usfirst.frc.team1557.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CameraCorrectAngleCommand extends Command {
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
	public CameraCorrectAngleCommand(double timeout, double absoluteTolerance, boolean isDefenseDrive) {
		requires(Robot.drive);

		this.setTimeout(timeout);
		this.tolerance = absoluteTolerance;
		this.isDefenseDrive = isDefenseDrive;
	}

	protected void initialize() {
		Robot.drive.turnOnlyInit(
				Robot.drive.getGyroAngle() + /* Robot.vb.getAngleOff() */ 0);

	}

	protected void execute() {
		Robot.drive.autonomousTurnPID.setSetpoint(
				Robot.drive.getGyroAngle() + /* Robot.vb.getAngleOff() */ 0);
		Robot.drive.turnOnlyDrive(isDefenseDrive);
	}

	@Override
	protected boolean isFinished() {

		return isTimedOut() || Robot.drive.autonomousTurnPID.getError() <= tolerance;
	}

	protected void end() {
		Robot.drive.autonomousTurnPID.reset();
	}

	protected void interrupted() {
		end();
	}

}
