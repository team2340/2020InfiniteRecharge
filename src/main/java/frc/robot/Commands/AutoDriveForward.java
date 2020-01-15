package frc.robot.Commands;
import frc.robot.Robot;
import frc.robot.RobotUtils;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoDriveForward extends Command {
	long startTime = 0;
	double desiredSpot = 0;
	double distance = 0;

	public AutoDriveForward(double howFar) {
		requires(Robot.drive);
		distance = howFar-(.5*RobotUtils.getLengthOfRobot());
	}

	@Override
	protected void initialize() {
		Robot.oi.gyro.reset();
		Robot.drive.setForPosition();
		startTime = System.currentTimeMillis();
//		double /*go = RobotUtils.distanceMinusRobot*/(distance);
		desiredSpot = RobotUtils.getEncPositionFromIN(distance);
//		Robot.drive.move(desiredSpot);
		Robot.oi.left.set(ControlMode.Position, desiredSpot);
		Robot.oi.right.set(ControlMode.Position, -desiredSpot);
	}

	@Override
	protected void execute() {
		int leftPos = Math.abs(Robot.oi.left.getSelectedSensorPosition(0));
		int rightPos = Math.abs(Robot.oi.right.getSelectedSensorPosition(0));
		//		int leftErr = Math.abs(Robot.oi.left.getClosedLoopError(0));
		//		int rightErr = Math.abs(Robot.oi.right.getClosedLoopError(0));

		double range = 0;
		SmartDashboard.putNumber("Current angle: ", Robot.oi.gyro.getAngle());
		SmartDashboard.putNumber("left position", Robot.oi.left.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("right position ",Robot.oi.right.getSelectedSensorPosition(0));

		
		//DO THAT HERE@!
		if(leftPos <= desiredSpot+range && leftPos >= desiredSpot-range){
			System.out.println ("LeftSide done");
		}
		if (rightPos <= desiredSpot+range && rightPos >= desiredSpot-range){
			System.out.println ("Rightside done");
		}	
	}

	protected boolean done() {
		int leftPos = Math.abs(Robot.oi.left.getSelectedSensorPosition(0));
		int rightPos = Math.abs(Robot.oi.right.getSelectedSensorPosition(0));
		//		int leftErr = Math.abs(Robot.oi.left.getClosedLoopError(0));
		//		int rightErr = Math.abs(Robot.oi.right.getClosedLoopError(0));

		double range = 0;
		if((leftPos <= desiredSpot+range && leftPos >= desiredSpot-range)
				&& (rightPos <= desiredSpot+range && rightPos >= desiredSpot-range))
		{
			return true;
		}
		return false;
	}

	@Override
	protected boolean isFinished() {
		return done();
	}
}
