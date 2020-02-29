package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DumpingSlowCommand extends Command {
	
	@Override
	protected void initialize() {

	}

	public DumpingSlowCommand() {
		requires(Robot.dumping);
	}

	@Override
	protected void execute() {
        Robot.dumping.dumpingForward(.8);
    }

	@Override
	protected boolean isFinished() {
		return false;
	}

	protected void end() {
        Robot.dumping.dumpingStop(); //when the button is let go, stop the motor that dumps the ball.
    }
}