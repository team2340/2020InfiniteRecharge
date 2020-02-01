package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class ControlPanelSubsystem extends Subsystem {
    static private ControlPanelSubsystem subsystem;

    private ControlPanelSubsystem() {
        create();
        I2C.Port i2cPort = I2C.Port.kOnboard;
        Robot.oi.colorSensor = new ColorSensorV3(i2cPort);
	}

    public static ControlPanelSubsystem getInstance() {
		if (subsystem == null) {
			subsystem = new ControlPanelSubsystem();
		}
		return subsystem;
    }

	private void create() {
		try {
			Robot.oi.controlPanel = new WPI_TalonSRX(RobotMap.CONTROLPANEL_TAL_ID);//change to new talon
			Robot.oi.left.setSensorPhase(true);
		} catch (Exception ex) {
			System.out.println("createControlPanel FAILED");
		}
    }

    
    
    public void controlPanelForward(){
        Robot.oi.controlPanel.set(1);
    }

    public void controlPanelReverse(){
        Robot.oi.controlPanel.set(-1);
    }
    
    public void controlPaneStop(){
        Robot.oi.controlPanel.set(0);
    }


    @Override
    protected void initDefaultCommand() {
        //setDefaultCommand(new ControlPanelCommand());
    }
}