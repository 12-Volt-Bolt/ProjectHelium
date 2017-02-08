package org.usfirst.frc.team1557.robot.commands;

import org.usfirst.frc.team1557.robot.OI;
import org.usfirst.frc.team1557.robot.Robot;
import org.usfirst.frc.team1557.robot.RobotMap;
import static org.usfirst.frc.team1557.robot.RobotMap.*;
import static org.usfirst.frc.team1557.robot.Robot.*;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FODCommand extends Command {
	public FODCommand() {
		requires(drive);
	}

	@Override
	protected boolean isFinished() {

		return false;
	}

	protected void execute() {
		// double desiredAngle = 0;
		// desiredAngle = Robot.drive.getGyroAngle();
		// desiredAngle += OI.mainJoy.getDirectionDegrees();
		// desiredAngle *= (Math.PI / 180);

		// if
		if (!Robot.defense.limitSwitch.get() && (OI.defenseDriveButtonOne.get() || OI.defenseDriveButtonTwo.get())
				&& false /* Limit Switch doesn't exist */) {
			Robot.defense.wheelsDown();

		} else if (Robot.defense.limitSwitch.get()
				&& !(OI.defenseDriveButtonOne.get() && OI.defenseDriveButtonTwo.get())
				&& false /* Limit Switch doesn't exist */) {
			Robot.defense.wheelsUp();
		}

		if (!Robot.defense.limitSwitch.get()) {
			Robot.drive.defenseDrive(OI.defenseJoy.getRawAxis(RobotMap.defenseJoyYAxisID),
					OI.defenseJoy.getRawAxis(RobotMap.mainJoyYAxisID));
		} else {
			drive.fodDrive(OI.mainJoy, mainJoyXAxisID, mainJoyYAxisID, OI.mainJoy, 4, 5,
					SmartDashboard.getBoolean("use borkened thing", false));
		}
		// TODO: Get rid of magic numbers.
		// if ((int) convertAngle(OI.mainJoy.getPOV()) != -1) {
		// drive.setGyroSetpoint(convertAngle(OI.mainJoy.getPOV()));
		// }

	}

	double convertAngle(double angle) {

		if (Math.abs(angle) > 360) {
			angle = angle % 360;
		}
		if (Math.abs(angle) > 180) {
			if (angle > 0) {
				angle = angle - 360;
			} else {
				angle = 360 + angle;
			}
		}
		return angle;
	}
}
