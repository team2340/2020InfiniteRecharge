package frc.robot.Commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class DrivingCameraCommand extends Command {
    boolean buttonPressed = false;
	boolean buttonMode= false;
    private Joystick controller;
    private NetworkTable table;


    public DrivingCameraCommand() {
        controller = Robot.oi.driveController;
    }

	@Override
	protected void initialize() {
        table = NetworkTableInstance.getDefault().getTable("datatable");
    }

	@Override
	protected void execute() {
        if (controller.getRawButton(RobotMap.BUTTON_3)){
            if(!buttonPressed){
					buttonPressed = true;
					if(buttonMode==false){
						buttonMode = true;
					}
					else if (buttonMode ==true){
						buttonMode =false;
					}
			}

		    else{
				buttonPressed = false;
			}
			// add to smart dash baord

		    if(buttonMode == true){
                 table.getEntry("Mode1").setBoolean(false);
                 table.getEntry("Mode2").setBoolean(true);
            }
            else if (buttonMode ==false){
                table.getEntry("Mode1").setBoolean(true);
                table.getEntry("Mode2").setBoolean(true);
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
//we want to to use the Vector one