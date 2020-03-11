package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class AcquisitionSubsystem extends Subsystem {
    static private AcquisitionSubsystem subsystem;

    private AcquisitionSubsystem() {
        create();
	}

    public static AcquisitionSubsystem getInstance() {
		if (subsystem == null) {
			subsystem = new AcquisitionSubsystem();
		}
		return subsystem;
    }

	private void create() {
		try {
			Robot.oi.acquisition = new WPI_TalonSRX(RobotMap.ACQUISITION_TAL_ID);//change to new talon
			// Robot.oi.frontLeft.setSensorPhase(true);
		} catch (Exception ex) {
			System.out.println("createAcquisition FAILED");
		}
    }

    
    
    public void acquisitionForward(double speed){
        Robot.oi.acquisition.set(-speed);
    }

    public void acquisitionReverse(){
        Robot.oi.acquisition.set(.5);
    }
    
    public void acquisitionStop(){
        Robot.oi.acquisition.set(0);
    }


    @Override
    protected void initDefaultCommand() {
       // setDefaultCommand(new AcquisitionCommand());
    }
}