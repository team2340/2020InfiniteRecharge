package frc.robot.Commands;

import frc.robot.Robot;

//import org.usfirst.frc.team2340.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class AcquisitionCommand extends Command {
	

	public AcquisitionCommand() {
		requires(Robot.acquisition);
		requires(Robot.dumping);
	}


	@Override
	protected void execute() {
		Robot.acquisition.acquisitionForward(0.70);
		Robot.dumping.dumpingForward(0.55);
	}

	

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.acquisition.acquisitionStop();
		Robot.dumping.dumpingForward(0);

	}

}
