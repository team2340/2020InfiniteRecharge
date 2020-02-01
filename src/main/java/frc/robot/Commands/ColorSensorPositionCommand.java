package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ColorSensorPositionCommand extends Command {

    public ColorSensorPositionCommand() {
		requires(Robot.controlPanel);
	}

	@Override
	protected void initialize() {
	}


	@Override
	protected void execute() {
        
	}

	@Override
	protected boolean isFinished() {
		return false;
	}	
}
