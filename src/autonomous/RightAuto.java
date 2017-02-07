package autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightAuto extends CommandGroup {

    public RightAuto() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	
    	addSequential(new DistanceCommand((7 * 12) + 9.25, 5, 1/16));
    	addSequential(new DefenseWheelsUp());
    	addSequential(new TurnToAngleCommand(-60, 5, 1/16, false));
    	addSequential(new DefenseWheelsDownCommand());
    	//the distance from the base line to the lift at 60 degrees is approximately 50 inches
    	addSequential(new DistanceCommand(50, 5, 1/16));
    	addSequential(new DistanceCommand(-8, 5, 1/16));
    	addSequential(new DefenseWheelsUp());
    	//strafe right past baseline
    	addSequential(new StrafeCommand(5, 1.0));
    	
    }
}
