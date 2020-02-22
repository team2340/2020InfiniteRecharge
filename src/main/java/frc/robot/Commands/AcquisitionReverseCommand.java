package frc.robot.Commands;

import frc.robot.Robot;

//import org.usfirst.frc.team2340.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class AcquisitionReverseCommand extends Command {
	

	public AcquisitionReverseCommand() {
		requires(Robot.acquisition);
	}


	@Override
	protected void execute() {
		Robot.acquisition.acquisitionReverse();
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
