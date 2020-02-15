package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class ClimbSubsystem extends Subsystem {
    static private ClimbSubsystem subsystem;

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
}