package frc.robot.Commands;

import frc.robot.Robot;
import frc.robot.RobotMap;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

//import org.usfirst.frc.team2340.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArcadeDriveCommand extends Command {
	double x1=0;
    double x2=0;
    private NetworkTable table;
	boolean buttonPressed = false;
	boolean buttonMode = false;
	private ToggleCommand tgCmdButton3 = new ToggleCommand();
	private JoystickButton button3;
	private Joystick controller;

	public ArcadeDriveCommand() {
		requires(Robot.drive);
		controller = Robot.oi.driveController;
	}

	@Override
	protected void initialize() {
		table = NetworkTableInstance.getDefault().getTable("datatable");
		button3 = new JoystickButton(controller, RobotMap.BUTTON_3);
	   button3.whenPressed(tgCmdButton3);
	}

	@Override
	protected void execute() {
		SmartDashboard.putNumber("left position", Robot.oi.frontLeft.getSensorCollection().getQuadraturePosition());
		SmartDashboard.putNumber("right position", Robot.oi.frontRight.getSensorCollection().getQuadraturePosition());

		double x, y, z;
		z = (3 - controller.getThrottle()) / 2;
		y = -controller.getY() / z;
		x = controller.getX() / z;
		// System.out.println("Z: " + z + ", y: " + y + ", x: " + x);

		x1 = table.getEntry("X1").getDouble(x1);
		x2 = table.getEntry("X2").getDouble(x2);
		double outerRange = 3;
		double innerRange = 1;


		if(!((x1<=39+outerRange)&&(x1>=39-outerRange)&&(x2 >= 39+outerRange)&&(x2 <= 39-outerRange))){
            //it's not straight at center!
            if (x2<x1) {
                SmartDashboard.putString("How To Move", "Turn Left");
                //print out turn left
            }
            else if (x2>x1) {
                SmartDashboard.putString( "How To Move", "Turn Left");
                //print out turn right
            }
		}
   	 	else {
			//it is at center
			SmartDashboard.putString("How To Move", "All Good Press 3");
			if (tgCmdButton3.GetToggle()==true){
				double betty = .3;//this means the amount that we slow down one of the robot`s moters so we turn, it was named by Grace who when asked what we shoud name this varible responed Betty
				if((x1<(x2+innerRange))&&(x1>(x2-innerRange))){
					Robot.oi.frontRight.set(y);
					Robot.oi.frontLeft.set(y);
				}
				else if (x2<x1){//goignto the right need to go left
					Robot.oi.frontRight.set(y);
					if(y<0){
						Robot.oi.frontLeft.set(y);
					}
					else if((y-betty)>0){
					Robot.oi.frontLeft.set(y-betty);
					}
					else{
						Robot.oi.frontLeft.set(0);
					}
				}
				else if(x1<x2){//going to the left need to go right
					if(y<0){
						Robot.oi.frontRight.set(y);//so  they can back up straight
					}
					else if ((y-betty)>0){
						Robot.oi.frontRight.set(y-betty);
					}
					else{
						Robot.oi.frontRight.set(0);
					}
					Robot.oi.frontLeft.set(y);
				}
			}
		}

		if(tgCmdButton3.GetToggle()==false){
			Robot.drive.setArcadeSpeed(x, y);
		}
	}

	

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
