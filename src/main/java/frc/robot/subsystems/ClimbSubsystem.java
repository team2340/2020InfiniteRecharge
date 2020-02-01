package frc.robot.subsystems;

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
			Robot.oi.climb = new WPI_TalonSRX(RobotMap.CLIMB_TAL_ID);//change to new talon
			// Robot.oi.left.setSensorPhase(true);
		} catch (Exception ex) {
			System.out.println("createClimb FAILED");
		}
    }
    
    public void climbForward(){
        Robot.oi.climb.set(1);
    }

    public void climbReverse(){
        Robot.oi.climb.set(-1);
    }
    
    public void climbStop(){
        Robot.oi.climb.set(0);
    }

    @Override
    protected void initDefaultCommand() {
       // setDefaultCommand(new ClimbCommand());
    }
}