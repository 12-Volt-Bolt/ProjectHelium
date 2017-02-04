package autonomous;

import edu.wpi.first.wpilibj.DigitalInput;

public class AutoChooser {
	
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
