package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class DumpingSubsystem extends Subsystem {
    static private DumpingSubsystem subsystem;

    private DumpingSubsystem() {
        create();
	}

    public static DumpingSubsystem getInstance() {
		if (subsystem == null) {
			subsystem = new DumpingSubsystem();
		}
		return subsystem;
    }

	private void create() {
		try {
			Robot.oi.dumping = new WPI_TalonSRX(RobotMap.DUMPING_TAL_ID);//change to new talon
			Robot.oi.left.setSensorPhase(true);
		} catch (Exception ex) {
			System.out.println("createDumping FAILED");
		}
    }

    
    public void dumpingForward(){
        Robot.oi.dumping.set(1);
    }

    public void dumpingReverse(){
        Robot.oi.dumping.set(-1);
    }
    
    public void dumpingStop(){
        Robot.oi.dumping.set(0);
    }


    @Override
    protected void initDefaultCommand() {
        //setDefaultCommand(new DumpingCommand());
    }
}