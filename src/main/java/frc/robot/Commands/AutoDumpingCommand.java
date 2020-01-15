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
		//Robot.dumping.move(-1);
	}

	@Override
	protected boolean isFinished() {
		if (System.currentTimeMillis() >= (startTime + 4000)) {
		//	Robot.dumping
			return true;
		}
		else {
			return false;
		}

	}
}
