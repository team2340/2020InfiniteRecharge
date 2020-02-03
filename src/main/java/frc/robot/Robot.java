/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.ColorMatch;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Commands.AutoDriveForward;
import frc.robot.Commands.AutoDumpingCommand;
import frc.robot.Commands.Rotation;
import frc.robot.RobotUtils.AutoMode;
import frc.robot.subsystems.ControlPanelSubsystem;
//import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
//import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.DumpingSubsystem;

public class Robot extends TimedRobot {
  public static final OI oi = new OI();
   public static DriveSubsystem drive = null;
   public static ControlPanelSubsystem controlPanel = null;
  // public static AcquisitionSubsystem acquisition = null;
   //public static ControlPanelSubsystem controlPanel = null;
   public static DumpingSubsystem dumping = DumpingSubsystem.getInstance();
   //public static ArmSubsystem arm = null;
   //public static ClimbSubsystem climb = null;
   //public static final DebugLogger myLogger = new DebugLogger();
   public static SendableChooser<Integer> judgesTargetColor = new SendableChooser<Integer>();
   SendableChooser<AutoMode> autoMode = new SendableChooser<AutoMode>();
   CommandGroup autonomousCommand = null;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    // myLogger.open("logs/", "DebugLogger", ".csv");
    autoMode.setDefaultOption("Disabled", AutoMode.DISABLED);
    autoMode.addOption("DriveForward", AutoMode.DriveForward);
    autoMode.addOption("MiddleToPowerPort", AutoMode.MiddleToPowerPort); // One option of starting point during Autonomous
		autoMode.addOption("CloseToPowerPort", AutoMode.CloseToPowerPort); // One option of starting point during Autonomous
		autoMode.addOption("FarFromPowerPort", AutoMode.FarFromPowerPort); // One option of starting point during Autonomous
		SmartDashboard.putData("Autonomous Modes", autoMode);
    
    judgesTargetColor.setDefaultOption("unknown", 0);
    judgesTargetColor.addOption("yellow", 1);
    judgesTargetColor.addOption("red", 2);
    judgesTargetColor.addOption("green", 3);
    judgesTargetColor.addOption("blue", 4);
    SmartDashboard.putData("judges' Target Color", judgesTargetColor);

    //CameraServer.getInstance().startAutomaticCapture();
    drive = DriveSubsystem.getInstance();
    controlPanel = ControlPanelSubsystem.getInstance();
    // liftandramp = LonelyLiftSubsystem.getInstance();
    //elevator = ElevatorSubsystem.getInstance();
    //arm = ArmSubsystem.getInstance();
  }

public void teleopInit() {
    if(autonomousCommand!=null){
      autonomousCommand.cancel();
    }
	}

  @Override
  public void robotPeriodic() {
    double IR = oi.colorSensor.getIR();
    Color detectedColor = oi.colorSensor.getColor();
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("IR", IR);
    ColorMatch.makeColor(0.143, 0.427, 0.429);
  }

  @Override
  public void autonomousInit() {
    AutoMode am = (AutoMode) autoMode.getSelected();
    autonomousCommand = new CommandGroup();

    if(am == AutoMode.DISABLED) {
    }
  
  
    else if(am==AutoMode.DriveForward){
      AutoDriveForward drive5 = new AutoDriveForward (5); //We need to know unit
      Rotation rotate70 = new Rotation (70);


      autonomousCommand.addSequential(drive5);
      autonomousCommand.addSequential(rotate70);
    }

//----------------------------------------------------------------------------------------------
// From here these are options for Autonomous's starting points
    
    //Variables
    double x = 0; //TODO: robot length plus the bumper's thickness
    double y = 0; //TODO: robot width plus the bumper's thickness
    double T = 39.71; //Vertical distance between the first triangle’s bottom and the middle line of the whole field (inches)
    double shootDistance = 0;

    // Starting point: facing forward,
        //Left side lining w/ the edge of Trench, 4 ft 8 in away from the right wall
        //Center of robot on the initial line
    /*else*/ if(am == AutoMode.FarFromPowerPort){
      //Cross the line
          //drive forward until whole robot is beyond the line
      AutoDriveForward driveHalfRobotLength = new AutoDriveForward(.5*x);
          //drive backward til having the center point lining up with the starting line
      AutoDriveForward driveHalfRobotLengthBackward = new AutoDriveForward(-(.5)*x);
      //Go to shoot
      Rotation turnLeft90 = new Rotation (-90);
      AutoDriveForward driveTillFrontOfPort = new AutoDriveForward((.5)*y + 105.625 + T + 24);
      //PUT IN turnLeft90 here in Sequance!
      AutoDriveForward driveTowardPort = new AutoDriveForward (120 - (.5)*x - shootDistance);
      AutoDumpingCommand dump = new AutoDumpingCommand();
      //Prepare to go to battle field
      AutoDriveForward negative_driveTillFrontOfPort = new AutoDriveForward ((-1) * (.5*y + 105.625 + T + 24));
    
      // TODO: Call these definiations to run here.
    }

    //Starting Point: facing left 
        //(center of robot on the initial line)
    else if(am == AutoMode.MiddleToPowerPort){
      
    }

    //Starting Point: facing right 
        //(Front side lining w/ the edge of Trench, 4 ft 8 in away from the left wall, 
        //center of robot is on the line)
    else if(am == AutoMode.CloseToPowerPort){
     

    }

    autonomousCommand.start();
  }

  
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
  }
}
