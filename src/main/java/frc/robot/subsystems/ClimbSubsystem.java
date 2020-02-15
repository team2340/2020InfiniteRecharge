package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class ClimbSubsystem extends Subsystem {
    static private ClimbSubsystem subsystem;

    //controls the smoothness of the motion. We will change them while testing.
    public float positionPeakOutputVoltage = 10.0f/12.0f;
    public double positionI = 0.000;
	public double positionD = 0.0;
	public double positionF = 0.0;
	public double positionP = 0.08525; //25% power at 3000 error ((%*1023)/desiered error)- increases power or drecers error ( bigger nubmer, closere to one) pos- 15% power at 1024/)


    private ClimbSubsystem() {
        create();
	}

    public static ClimbSubsystem getInstance() {
		if (subsystem == null) {
			subsystem = new ClimbSubsystem();
		}
		return subsystem;
    }

	private void create() {
		try {
            Robot.oi.climb1 = new WPI_TalonSRX(RobotMap.CLIMB1_TAL_ID);
			Robot.oi.climb1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,0,0);
		    Robot.oi.climb1.selectProfileSlot(0,0); 
			Robot.oi.climb1.setSensorPhase(true); //TODO: find out which way the motor should turn

			Robot.oi.climb2 = new WPI_TalonSRX(RobotMap.CLIMB2_TAL_ID);
			Robot.oi.climb2.set(ControlMode.Follower, RobotMap.CLIMB1_TAL_ID);
			// Robot.oi.left.setSensorPhase(true);
		} catch (Exception ex) {
			System.out.println("createClimb FAILED");
		}
    }
    
    public void climbForward(){
        Robot.oi.climb1.set(1);
    }

    public void climbReverse(){
        Robot.oi.climb1.set(-1);
    }
    
    public void climbStop(){
        Robot.oi.climb1.set(0);
    }

    @Override
    protected void initDefaultCommand() {
       // setDefaultCommand(new ClimbCommand());
    }


    public void setForPosition() {
		Robot.oi.climb1.set(ControlMode.Position, 0);
	    Robot.oi.climb1.selectProfileSlot(0,0);
	    Robot.oi.climb1.config_kF(0,positionF,0);
	    Robot.oi.climb1.config_kP(0,positionP,0);  
	    Robot.oi.climb1.config_kI(0,positionI,0);  
        Robot.oi.climb1.config_kD(0,positionD,0);
        //setting up max and min voltage (which turns into speed) from -12 to +12.
	    Robot.oi.climb1.configPeakOutputForward(positionPeakOutputVoltage,0);
	    Robot.oi.climb1.configPeakOutputReverse(-positionPeakOutputVoltage,0);
	
	    Robot.oi.climb1.setSelectedSensorPosition(0,0,0);
    }
    
    public void move(double amt) {
        //move to this position
		Robot.oi.climb1.set(ControlMode.Position, amt + Robot.oi.climb1.getSelectedSensorPosition(0));
		

	}
}
