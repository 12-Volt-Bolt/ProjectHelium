package autonomous;

import edu.wpi.first.wpilibj.DigitalInput;

public class AutoChooser {

	/**
	 * Method for choosing our autonomous based on digital inputs, becuase
	 * SmartDashboard in unreliable. If we get a value from digital input 0,
	 * CenterAuto will run. If we get a value from digital input 1, RightAuto
	 * will run. If we get a value from digital input 2, LeftAuto will run.
	 */
	public void choose() {

		if (new DigitalInput(0).get()) {
			new CenterAuto().start();
		} else if (new DigitalInput(1).get()) {
			new RightAuto().start();
		} else if (new DigitalInput(2).get()) {
			new LeftAuto().start();
		} else {

		}
	}

}
