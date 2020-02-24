package frc.robot.Commands;

import frc.robot.Robot;

//import org.usfirst.frc.team2340.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class AcquisitionCommand extends Command {
	

	public AcquisitionCommand() {
		requires(Robot.acquisition);
	}


	@Override
	protected void execute() {
		Robot.acquisition.acquisitionForward(0.70);
	}

	

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.acquisition.acquisitionStop();

	}

}
