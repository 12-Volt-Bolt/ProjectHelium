package autonomous;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftAuto extends CommandGroup {

    public LeftAuto() {

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
    	
    	//ditance from wall to baseline is 7 feet and 9.25 inches, still need to adjust time and tolerance
    	addSequential(new DistanceCommand((7 * 12) + 9.25, 5, 1/16));
    	//turn to an angle to be perpendicular to the hexagon (airship)
    	addSequential(new TurnToAngleCommand(60, 5, 1/16, true));
    	//the distance from the base line to the lift at 60 degrees is approximately 50 inches, but we need to account for robot space. We are subtracting 5.5 because the peg in 9 inches long and our gear catcher in 3.5 inches deep; 9 - 3.5 = 5.5. Though, I think this may have to be adjusted later because of bumpers as well.
    	addSequential(new DistanceCommand(50 - 5.5, 5, 1/16));
    	//move back 8 inches 
    	addSequential(new DistanceCommand(-8, 5, 1/16));
    	//lift defense wheels so we can strafe
    	addSequential(new DefenseWheelsUp());
    	//strafe left for 5 seconds (will probably need to be changed) past the baseline
    	addSequential(new StrafeCommand(5, -1.0));
    }
}
