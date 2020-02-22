package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DumpingCommand extends Command {
	
	@Override
	protected void initialize() {

	}

	public DumpingCommand() {
		requires(Robot.dumping);
	}

	@Override
	protected void execute() {
        Robot.dumping.dumpingForward(1);
    }

	@Override
	protected boolean isFinished() {
		return false;
	}

	protected void end() {
        Robot.dumping.dumpingStop(); //when the button is let go, stop the motor that dumps the ball.
    }
}