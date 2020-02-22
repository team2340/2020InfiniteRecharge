package frc.robot.Commands;

import frc.robot.Robot;
//import org.usfirst.frc.team2340.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArcadeDriveCommand extends Command {
	double x1=0;
    double x2=0;
	boolean buttonPressed = false;
	boolean buttonMode = false;
	private Joystick controller;

	public ArcadeDriveCommand() {
		requires(Robot.drive);
		controller = Robot.oi.driveController;
	}


	@Override
	protected void execute() {
		SmartDashboard.putNumber("left position", Robot.oi.frontLeft.getSensorCollection().getQuadraturePosition());
		SmartDashboard.putNumber("right position", Robot.oi.frontRight.getSensorCollection().getQuadraturePosition());

		double x, y, z;
		z = (3 - controller.getThrottle()) / 2;
		y = controller.getY() / z;
		x = controller.getX() / z;
		// System.out.println("Z: " + z + ", y: " + y + ", x: " + x);

		Robot.drive.setArcadeSpeed(x, y);
	}

	

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
