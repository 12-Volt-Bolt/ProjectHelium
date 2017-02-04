package autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterAuto extends CommandGroup {

	public CenterAuto() {

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
				12 * 12 /*
						 * TODO: Find the distance from the wall to the center
						 * LIFT
						 */,
				5 /* TODO: Find how long it usually takes to make the drive */,
				1 / 16 /* TODO: Find a good tolerance */));

	}
}
