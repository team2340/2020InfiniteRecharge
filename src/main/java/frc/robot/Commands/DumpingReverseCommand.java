package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DumpingReverseCommand extends Command {
	
	@Override
	protected void initialize() {

	}

	public DumpingReverseCommand() {
		requires(Robot.dumping);
	}

	@Override
	protected void execute() {
        Robot.dumping.dumpingForward(-.45);
    }

	@Override
	protected boolean isFinished() {
		return false;
	}

	protected void end() {
        Robot.dumping.dumpingStop(); //when the button is let go, stop the motor that dumps the ball.
    }
}