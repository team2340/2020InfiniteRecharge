package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class AutoDriveWithTime extends Command {
	long startTime = 0;
	@Override
	protected void initialize() {
		//Robot.myLogger.log("AutoDumpingCommand", "", "");

		startTime = System.currentTimeMillis();
	}

	public AutoDriveWithTime() {
		requires(Robot.drive);
	}

	@Override
	protected void execute() {
        Robot.drive.setArcadeSpeed(0, -.6); //Drive straight forward with 0.5 speed
    }

	@Override
	protected boolean isFinished() {
		if (System.currentTimeMillis() >= (startTime + 750/*the time that the dumping motor runs*/)) {
            Robot.drive.setArcadeSpeed(0, 0); //Stop driving
            return true;
		}
		else {
			return false;
		}
	}

	
}
