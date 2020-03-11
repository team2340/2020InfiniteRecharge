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
	double range = 50;
	Boolean leftDone = false;
	Boolean rightDone = false;

	public AutoDriveForward(double howFar /*unit: inches*/ ) {
		requires(Robot.drive);
		distance = howFar/*-(.5*RobotUtils.getLengthOfRobot())*/;
		SmartDashboard.putNumber("distance ", distance);
		leftDone = rightDone = false;
	}

	@Override
	protected void initialize() {
		Robot.oi.gyro.reset();
		Robot.drive.setForPosition();
		startTime = System.currentTimeMillis();
//		double /*go = RobotUtils.distanceMinusRobot*/(distance);
		desiredSpot = RobotUtils.getEncPositionFromIN(distance);
//		Robot.drive.move(desiredSpot);
		Robot.oi.frontLeft.set(ControlMode.Position, desiredSpot);
		Robot.oi.frontRight.set(ControlMode.Position, -desiredSpot);
		SmartDashboard.putNumber("desired position", desiredSpot);
		SmartDashboard.putNumber("range", range);
		desiredSpot = Math.abs(desiredSpot);
	}

	@Override
	protected void execute() {
		int leftPos = Robot.oi.frontLeft.getSelectedSensorPosition(0);
		int rightPos = Robot.oi.frontRight.getSelectedSensorPosition(0);
		leftPos = Math.abs(leftPos);
		rightPos = Math.abs(rightPos);
		//		int leftErr = Math.abs(Robot.oi.left.getClosedLoopError(0));
		//		int rightErr = Math.abs(Robot.oi.right.getClosedLoopError(0));

		SmartDashboard.putNumber("Current angle", Robot.oi.gyro.getAngle());
		SmartDashboard.putNumber("left position", leftPos);
		SmartDashboard.putNumber("right position", rightPos);

		
		//DO THAT HERE@!
		if(leftPos <= desiredSpot+range && leftPos >= desiredSpot-range){
			System.out.println ("LeftSide done");
			leftDone = true;
		}
		if (rightPos <= desiredSpot+range && rightPos >= desiredSpot-range){
			System.out.println ("Rightside done");
			rightDone = true;
		}	
	}

	protected boolean done() {
		if(leftDone && rightDone)
		{
			System.out.println("STOP!");
			Robot.drive.setArcadeSpeed(0, 0);
			return true;
		}
		return false;
	}

	@Override
	protected boolean isFinished() {
		return done();
	}
}
