package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class AutoDumpingCommand extends Command {
	long startTime = 0;
	@Override
	protected void initialize() {
		//Robot.myLogger.log("AutoDumpingCommand", "", "");

		startTime = System.currentTimeMillis();
	}

	public AutoDumpingCommand() {
		requires(Robot.dumping);
	}

	@Override
	protected void execute() {
		Robot.dumping.dumpingForward(.5);
	}

	@Override
	protected boolean isFinished() {
		if (System.currentTimeMillis() >= (startTime + 4000/*the time that the dumping motor runs*/)) {
			Robot.dumping.dumpingStop();
			return true;
		}
		else {
			return false;
		}
	}

	
}
