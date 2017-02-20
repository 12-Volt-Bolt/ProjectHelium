package autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class RightAuto extends CommandGroup {

	public RightAuto() {
		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.

		// 7 feet 9.25 INCHES IS DISTANCE FROM WALL to baseline, still need to
		// adjust time and tolerance, and distance might need to be adjusted cuz
		// robot space, also peg space 9 in, our collector is 3.5, 9-3.5=?
		addSequential(new DistanceCommand(((7 * 12) + 9.25), 5, 1 / 16));
		// turn to ange of hexagon (airship)
		addSequential(new TurnToAngleCommand(-60, 5, 1 / 16, true));
		// the distance from the base line to the lift at 60 degrees is
		// approximately 50 inches
		addSequential(new DistanceCommand(50 - 5.5 - 35, 5, 1 / 16));
		// move back so we can strafe
		addSequential(new DistanceCommand(-8, 5, 1 / 16));
		// lift defense wheels so we can strafe
		addSequential(new LeftDefenseUpCommand());
		// strafe right past baseline
		addSequential(new StrafeCommand(5, 1.0));
		// addSequential(new TurnToAngleCommand(90, 3, 3, false));
		// addSequential(new WaitCommand(5));
		// addSequential(new StrafeCommand(5, 1.0));
	}
}
