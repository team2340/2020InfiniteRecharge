/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.ColorMatchResult;

import edu.wpi.first.wpilibj.TimedRobot;
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
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.DumpingSubsystem;

public class Robot extends TimedRobot {
  public static final OI oi = new OI();
  public static DriveSubsystem drive = null;
  public static ControlPanelSubsystem controlPanel = null;
  // public static AcquisitionSubsystem acquisition = null;
  public static DumpingSubsystem dumping = null;
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
    drive = new DriveSubsystem();
    controlPanel = new ControlPanelSubsystem();
    // liftandramp = LonelyLiftSubsystem.getInstance();
    // dumping = DumpingSubsystem.getInstance();
    //elevator = ElevatorSubsystem.getInstance();
    //arm = ArmSubsystem.getInstance();
  }

  @Override
  public void robotPeriodic() {
    double IR = oi.colorSensor.getIR();
    Color detectedColor = oi.colorSensor.getColor();
    String colorString;
    ColorMatchResult match = controlPanel.matchClosestColor(detectedColor);
    double confidencePercentage = .93; //Joy didn't know what she did in Feb.10, 2020... ???????
    if(match != null)
    {
      if(match.confidence >= confidencePercentage) //TODO: Move confidence into matchClosestColor function (or maybe have confidence be a parameter)
      {
        if (match.color == controlPanel.kBlueTarget) {
          colorString = "Blue";
        } else if (match.color == controlPanel.kRedTarget) {
          colorString = "Red";
        } else if (match.color == controlPanel.kGreenTarget) {
          colorString = "Green";
        } else if (match.color == controlPanel.kYellowTarget) {
          colorString = "Yellow";
        }
        else
        {
          colorString = "Unknown";
        }
      }
      else {
        colorString = "Not Confident";
      }
      SmartDashboard.putNumber("Confidence", match.confidence);
      SmartDashboard.putString("Detected Color", colorString);
    }

    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("IR", IR);
  }

  @Override
  public void autonomousInit() {
    AutoMode am = autoMode.getSelected();
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
    double B = 56; //Vertical distance between bottom corner of power port and random point on player station that lines up with trench = B ; equal to width of Trench
    double shootDistance = 0;

    //Option 1
        //Starting point: facing forward,
        //Left side of robot lining w/ the edge of Trench, 4 ft 8 in away from the right wall
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
      //turnLeft90 here in Sequance!
      AutoDriveForward driveTowardPort = new AutoDriveForward (120 - (.5)*x - shootDistance);
      AutoDumpingCommand dump = new AutoDumpingCommand();
      //Prepare to go to battle field
      AutoDriveForward negative_driveTillFrontOfPort = new AutoDriveForward ((-1) * (.5*y + 105.625 + T + 24));
    
      //Add the actions to the Sequential
      autonomousCommand.addSequential(driveHalfRobotLength);
      autonomousCommand.addSequential(driveHalfRobotLengthBackward);
      autonomousCommand.addSequential(turnLeft90);
      autonomousCommand.addSequential(driveTillFrontOfPort);
      autonomousCommand.addSequential(turnLeft90);
      autonomousCommand.addSequential(driveTowardPort);
      autonomousCommand.addSequential(dump);
      autonomousCommand.addSequential(negative_driveTillFrontOfPort);
    }

    //Option 2
        //Starting Point: facing left 
        //center of robot Center of robot on the intersection of the initial line and the middle horizontal line on the floor
    else if(am == AutoMode.MiddleToPowerPort){
      //Go to shoot
      AutoDriveForward driveTilFrontOfPort = new AutoDriveForward (24 + T);
      Rotation turnLeft90 = new Rotation (-90);
      AutoDriveForward driveToPort = new AutoDriveForward ((120 - (0.5 * x)) - shootDistance);
      AutoDumpingCommand deliverToPowerPort = new AutoDumpingCommand ();
      //Cross the Line
      AutoDriveForward crossLine = new AutoDriveForward ((-1.0 * (120 - (0.5 * x)) - shootDistance)); 
      AutoDriveForward driveBackwardRobotLength = new AutoDriveForward (x);

      // Add the actions to the Sequential
      autonomousCommand.addSequential(driveTilFrontOfPort);
      autonomousCommand.addSequential(turnLeft90);
      autonomousCommand.addSequential(driveToPort);
      autonomousCommand.addSequential(deliverToPowerPort);
      autonomousCommand.addSequential(crossLine);   
      autonomousCommand.addSequential(driveBackwardRobotLength);   
    }

    //Option 3
        //Starting Point: facing right 
        //(Front side lining w/ the edge of Trench, 4 ft 8 in away from the left wall, 
        //center of robot is on the line)
    else if(am == AutoMode.CloseToPowerPort){
     //Go to shoot
     AutoDriveForward driveTilFrontOfPort = new AutoDriveForward((0.5*x) + B + 24);
     Rotation turnRight90 = new Rotation (90);
     AutoDriveForward driveToPort = new AutoDriveForward((120 - (0.5*x)) - shootDistance);
     AutoDumpingCommand deliverPowerCell = new AutoDumpingCommand();
     //Cross the Line
     AutoDriveForward crossLine = new AutoDriveForward (120 - shootDistance);

      // Add the actions to the Sequential
      autonomousCommand.addSequential(driveTilFrontOfPort);
      autonomousCommand.addSequential(turnRight90);
      autonomousCommand.addSequential(driveToPort);
      autonomousCommand.addSequential(deliverPowerCell);
      autonomousCommand.addSequential(crossLine);   
    }

    // Start to run the actions!
    autonomousCommand.start();
  }

  
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    if(autonomousCommand!=null){
      autonomousCommand.cancel();
    }
  }
  
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
  }
}
