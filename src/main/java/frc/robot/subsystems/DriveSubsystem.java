package frc.robot.subsystems;

import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.Commands.ArcadeDriveCommand;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveSubsystem extends Subsystem {

	DifferentialDrive robotDrive;
	public double speedP = 1.5;
	public double speedI = 0.0;
	public double speedD = 0.0;
	public double speedF = 0.001;
	public double speedPeakOutputVoltage = 1f;
	
	public double positionP = 5 ; //25% power at 3000 error ((%*1023)/desired error)- increases power or drecers error ( bigger nubmer, closere to one) pos- 15% power at 1024/)
//	public double positionP = 0.127875; // 25% power at 2000 error
//	public double positionP = 0.25575; // 25% power at 1000 error              //3000 should be smaller
//	public double positionP = 2/*0.5115*/; // 50% power at 1000 error
//	public double positionP = 0.1023; // 30% power at 3000 error
//	public double positionP = 0.15345;// 30% power at 2000 error
//	public double positionP = 0.1023;// 30% power at 1000 error
//	public double positionI = 0.00001; 
	//public double positionD = 10.0;
	

//	public double positionI = 0.0001;
	public double positionI = 0.000;
	public double positionD = 0.0;
	public double positionF = 0.0;
//	public float positionPeakOutputVoltage = 3.0f/12.0f;
	public float positionPeakOutputVoltage = 10.0f/12.0f;
	
	public double vBusMaxOutput = 1.0; //An output multiplier
	public double vBusPeakOutputVoltage = 1f; //the peak output (between 0 and 1)
	protected void initDefaultCommand() {
		setDefaultCommand(new ArcadeDriveCommand());
	}

	public DriveSubsystem() {
		createLeftSide();
		createRightSide();
//		setBrakeMode(true);
		setForPosition();
		robotDrive = new DifferentialDrive(Robot.oi.frontLeft, Robot.oi.frontRight);
		robotDrive.setSafetyEnabled(false);
	}

	private void createLeftSide() {
		try {
			Robot.oi.frontLeft = new WPI_TalonSRX(RobotMap.FRONT_LEFT_TAL_ID);
			Robot.oi.frontLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
		    Robot.oi.frontLeft.selectProfileSlot(0,0);
			Robot.oi.frontLeft.setSensorPhase(false);

			Robot.oi.backLeft = new WPI_TalonSRX(RobotMap.BACK_LEFT_TAL_ID);
			Robot.oi.backLeft.set(ControlMode.Follower, RobotMap.FRONT_LEFT_TAL_ID);

		} catch (Exception ex) {
			System.out.println("createLeftSide FAILED");
		}
	}

	private void createRightSide() {
		try {
			Robot.oi.frontRight = new WPI_TalonSRX(RobotMap.FRONT_RIGHT_TAL_ID);
			Robot.oi.frontRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
		    Robot.oi.frontRight.selectProfileSlot(0,0);
			Robot.oi.frontRight.setSensorPhase(false);
			
			Robot.oi.backRight = new WPI_TalonSRX(RobotMap.BACK_RIGHT_TAL_ID);
			Robot.oi.backRight.set(ControlMode.Follower, RobotMap.FRONT_RIGHT_TAL_ID);
		} catch (Exception ex) {
			System.out.println("createRightSide FAILED");
		}
	}
	public void move(double amt) {
		SmartDashboard.putNumber("starting left position", Robot.oi.frontLeft.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("starting right position ",Robot.oi.frontRight.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("left position", Robot.oi.frontLeft.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("desired amt ",amt);
		Robot.oi.frontLeft.set(ControlMode.Position, amt + Robot.oi.frontLeft.getSelectedSensorPosition(0));
		Robot.oi.frontRight.set(ControlMode.Position, -amt + Robot.oi.frontRight.getSelectedSensorPosition(0));

	}
	public void setForSpeed() {
		Robot.oi.frontLeft.set(ControlMode.Velocity, 0);
		Robot.oi.frontRight.set(ControlMode.Velocity, 0);
		Robot.oi.frontRight.config_kF(0,speedF,0);
	    Robot.oi.frontRight.config_kP(0,speedP,0);
	    Robot.oi.frontRight.config_kI(0,speedI,0); 
	    Robot.oi.frontRight.config_kD(0,speedD,0);
	    Robot.oi.frontLeft.config_kF(0,speedF,0);
	    Robot.oi.frontLeft.config_kP(0,speedP,0);  
	    Robot.oi.frontLeft.config_kI(0,speedI,0);  
	    Robot.oi.frontLeft.config_kD(0,speedD,0);
	    Robot.oi.frontLeft.configPeakOutputForward(speedPeakOutputVoltage,0); 
	    Robot.oi.frontLeft.configPeakOutputReverse(-speedPeakOutputVoltage,0);
	    Robot.oi.frontRight.configPeakOutputForward(speedPeakOutputVoltage,0);
	    Robot.oi.frontRight.configPeakOutputReverse(-speedPeakOutputVoltage,0);	
	}
	
	public void setPeakOutputVoltage(float voltage) {
		Robot.oi.frontLeft.configPeakOutputForward(voltage,0);
		Robot.oi.frontLeft.configPeakOutputReverse(-voltage,0);
	    Robot.oi.frontRight.configPeakOutputForward(voltage,0);
	    Robot.oi.frontRight.configPeakOutputReverse(-voltage,0);	
	}
	
	public void setForPosition() {
		Robot.oi.frontLeft.set(ControlMode.Position, 0);
		Robot.oi.frontRight.set(ControlMode.Position,0);
		Robot.oi.frontRight.selectProfileSlot(0,0);
		Robot.oi.frontRight.config_kF(0,positionF,0);
	    Robot.oi.frontRight.config_kP(0,positionP,0);
	    Robot.oi.frontRight.config_kI(0,positionI,0); 
	    Robot.oi.frontRight.config_kD(0,positionD,0);
	    Robot.oi.frontLeft.selectProfileSlot(0,0);
	    Robot.oi.frontLeft.config_kF(0,positionF,0);
	    Robot.oi.frontLeft.config_kP(0,positionP,0);  
	    Robot.oi.frontLeft.config_kI(0,positionI,0);  
	    Robot.oi.frontLeft.config_kD(0,positionD,0);
	    Robot.oi.frontLeft.configPeakOutputForward(positionPeakOutputVoltage,0);
	    Robot.oi.frontLeft.configPeakOutputReverse(-positionPeakOutputVoltage,0);
	    Robot.oi.frontRight.configPeakOutputForward(positionPeakOutputVoltage,0);
	    Robot.oi.frontRight.configPeakOutputReverse(-positionPeakOutputVoltage,0);
	    Robot.oi.frontRight.setSelectedSensorPosition(0,0,0);
		Robot.oi.frontLeft.setSelectedSensorPosition(0,0,0);
		
		Robot.oi.frontLeft.setSelectedSensorPosition(0, 0, 0);
		Robot.oi.frontRight.setSelectedSensorPosition(0, 0, 0);
	}
	
	public void setForVBus() {
		Robot.oi.frontLeft.set(ControlMode.PercentOutput,0);
        Robot.oi.frontRight.set(ControlMode.PercentOutput,0);
        Robot.oi.frontLeft.configPeakOutputForward(vBusPeakOutputVoltage,0); 
	    Robot.oi.frontLeft.configPeakOutputReverse(-vBusPeakOutputVoltage,0);
	    Robot.oi.frontRight.configPeakOutputForward(vBusPeakOutputVoltage,0);
	    Robot.oi.frontRight.configPeakOutputReverse(-vBusPeakOutputVoltage,0);
        robotDrive.setMaxOutput(vBusMaxOutput);
		setArcadeSpeed(0,0);
	}
	
	public void setBrakeMode(boolean brake) {
		Robot.oi.frontRight.setNeutralMode(NeutralMode.Brake);
		Robot.oi.frontLeft.setNeutralMode(NeutralMode.Brake);
	}
	
	public void setArcadeSpeed(double x, double y){
		robotDrive.arcadeDrive(-y, x);
	}
}
