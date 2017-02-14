package org.usfirst.frc.team1557.robot.commands;

import org.usfirst.frc.team1557.robot.OI;
import org.usfirst.frc.team1557.robot.Robot;
import org.usfirst.frc.team1557.robot.RobotMap;
import static org.usfirst.frc.team1557.robot.RobotMap.*;
import static org.usfirst.frc.team1557.robot.Robot.*;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FODCommand extends Command {
	public FODCommand(String s) {
		super();
		requires(drive);
	}

	@Override
	protected void initialize() {
		Robot.drive.rotationPID.enable();
	}

	@Override
	protected boolean isFinished() {

		return false;
	}

	protected void execute() {

//		if (!Robot.defense.limitSwitch.get()) {
			Robot.drive.defenseDrive(OI.mainJoy.getRawAxis(RobotMap.leftYAxisID),
					OI.mainJoy.getRawAxis(RobotMap.rightYAxisID));
	             OI.mainJoy.setRumble(GenericHID.RumbleType.kLeftRumble, 0.25);
	             OI.mainJoy.setRumble(GenericHID.RumbleType.kRightRumble, 0.25);
			
			//	OI.mainJoy.setRumble(RumbleType.kLeftRumble, 0.25);
		//	OI.mainJoy.setRumble(RumbleType.kRightRumble, 0.25);
	//	} else {
	//		drive.fodDrive(OI.mainJoy, leftXAxisID, leftYAxisID, OI.mainJoy, 4, 5,
	//				SmartDashboard.getBoolean("use borkened thing", false));
	//	}
		// TODO: Get rid of magic numbers.

	}

}
