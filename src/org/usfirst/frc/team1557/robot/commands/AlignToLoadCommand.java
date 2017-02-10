package org.usfirst.frc.team1557.robot.commands;

import autonomous.DefenseWheelsDownCommand;
import autonomous.DistanceCommand;
import autonomous.TurnToAngleCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AlignToLoadCommand extends CommandGroup {
	public AlignToLoadCommand(String s) {
		super(s);
		addSequential(new TurnToAngleCommand(-45, 2, 2, false));
		addSequential(new DefenseWheelsDownCommand());
		addSequential(new DistanceCommand(-7, 5, (1d / 16d)));

	}
}
