package autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterAuto extends CommandGroup {
	
	double strafeLeft = -1.0;
	double strafeRight = 1.0; 

	public CenterAuto() {
		
		
		//encoders 
		//right side is positive
		//left side is negative

		// Drive forward a distance;
		// Maybe correct rotation at the end of the drive using the camera
		// After we are sure the rotation is correct, we drive forward a small
		// amount;
		// Then, wait a few seconds. Also, give some sort of feedback to the
		// pilot. (LEDs)
		// While waiting, raise the wings.
		// After completion, back up 5ish feet?
		// Then, strafe to either the left or right while giving the potentially
		// scoring robots a wide berth.(lots of clearance)
		// Finally, drive forward to gain those sweet +5 points.
		// And promptly start dancing with the remaing time.
		// Alternatively, start making headway to the loading station; however,
		// make sure that you do not A) run into other robots 2) drive into the
		// enemy auto zone.
		addSequential(new DistanceCommand(
				((7 * 12) + 9.25) - 5.5 - 35 /*
						 * wall to center lift if 7ft 9.25 inches, subtract 5.5 because of how far back our gear catcher is, subtract 35 because our robot plus bumbers is 35 inches
						 */,
				5 /* TODO: Find how long it usually takes to make the drive */,
				1 / 16 /* TODO: Find a good tolerance */));
		
		//raise defense wheels so we can strafe
		addSequential(new LeftDefenseUpCommand());
		//move back so we can strafe
		addSequential(new DistanceCommand(-8, 5, 1/16));
		//strafe (in either direction but for now left), far enough to avoid the airship and other robots before moving forward past the baseline for those 5 points 
		addSequential(new StrafeCommand(2.5, strafeLeft));
		//put the defense wheels back down so we can use their encoders
		addSequential(new LeftDefenseDownCommand());
		//move forward past baseline
		addSequential(new DistanceCommand(11, 5, 1/16));
		//we'll probably want the defense wheels up when we start tele-op so
		addSequential(new LeftDefenseUpCommand());
	}
}
