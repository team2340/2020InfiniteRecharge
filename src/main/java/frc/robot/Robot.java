/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.ColorMatchResult;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.RobotUtils.AutoMode;
import frc.robot.Commands.AcquisitionCommand;
import frc.robot.Commands.AcquisitionReverseCommand;
import frc.robot.Commands.AutoDriveForward;
import frc.robot.Commands.AutoDumpingCommand;
import frc.robot.Commands.CameraCommand;
import frc.robot.Commands.ColorSensorPositionCommand;
import frc.robot.Commands.ColorSensorRotationCommand;
import frc.robot.Commands.DumpingCommand;
import frc.robot.Commands.DumpingReverseCommand;
import frc.robot.Commands.DumpingSlowCommand;
import frc.robot.Commands.Rotation;
import frc.robot.subsystems.AcquisitionSubsystem;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.ControlPanelSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.DumpingSubsystem;

public class Robot extends TimedRobot {
  public static final OI oi = new OI();
  public static DriveSubsystem drive = null;
  public static ControlPanelSubsystem controlPanel = null;
  public static AcquisitionSubsystem acquisition = null;
  public static DumpingSubsystem dumping = null;
  //public static ArmSubsystem arm = null;
  public static ClimbSubsystem climb = null;
  //public static final DebugLogger myLogger = new DebugLogger();
  public static SendableChooser<Integer> judgesTargetColor = new SendableChooser<Integer>();
  SendableChooser<AutoMode> autoMode = new SendableChooser<AutoMode>();
  CommandGroup autonomousCommand = null;
  public static UsbCamera camera1;
  public static UsbCamera camera2;


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    // myLogger.open("logs/", "DebugLogger", ".csv");
    autoMode.setDefaultOption("Disabled", AutoMode.DISABLED);
    autoMode.addOption("DriveForward", AutoMode.DriveForward);
    autoMode.addOption("Left_Cross_and_Dump", AutoMode.Left_Cross_and_Dump); // One option of starting point during Autonomous
		autoMode.addOption("Right_Cross_and_DiagonalDump", AutoMode.Right_Cross_and_DiagonalDump); // One option of starting point during Autonomous
		autoMode.addOption("Center_Cross_and_Dump", AutoMode.Center_Cross_and_Dump); // One option of starting point during Autonomous
    autoMode.addOption("Right_Cross_and_90degreesDump", AutoMode.Right_Cross_and_90degreesDump);// One option
    autoMode.addOption("CrossOnly", AutoMode.CrossOnly);// One option
    SmartDashboard.putData("Autonomous Modes", autoMode);
    
    judgesTargetColor.setDefaultOption("unknown", 0);
    judgesTargetColor.addOption("yellow", 1);
    judgesTargetColor.addOption("red", 2);
    judgesTargetColor.addOption("green", 3);
    judgesTargetColor.addOption("blue", 4);
    SmartDashboard.putData("judges' Target Color", judgesTargetColor);

    camera1 = CameraServer.getInstance().startAutomaticCapture(0);
    camera2 = CameraServer.getInstance().startAutomaticCapture(1);


    drive = new DriveSubsystem();
    controlPanel = new ControlPanelSubsystem();
    dumping = DumpingSubsystem.getInstance();
    acquisition = AcquisitionSubsystem.getInstance();
    climb = ClimbSubsystem.getInstance();

        // Binds the ColorSensorPositionCommand to be scheduled when the button3 of the joystick is pressed
        //When button 3 is pressed again, the ColorSensorPositionCommand would stop.
    JoystickButton driveButton3 = new JoystickButton(oi.driveController, RobotMap.BUTTON_3);
    driveButton3.toggleWhenPressed(new ColorSensorPositionCommand());

    JoystickButton driveButton4 = new JoystickButton(oi.driveController, RobotMap.BUTTON_4);
    driveButton4.toggleWhenPressed(new ColorSensorRotationCommand());

    JoystickButton acqButton5 = new JoystickButton(oi.acquisitionController, RobotMap.BUTTON_5);
    acqButton5.whileHeld(new AcquisitionCommand());

    JoystickButton acqButton3 = new JoystickButton(oi.acquisitionController, RobotMap.BUTTON_3);
    acqButton3.whileHeld(new AcquisitionReverseCommand());

    JoystickButton acqButton6 = new JoystickButton(oi.acquisitionController, RobotMap.BUTTON_6);
    acqButton6.whileHeld(new DumpingSlowCommand());

    JoystickButton acqButton4 = new JoystickButton (oi.acquisitionController, RobotMap.BUTTON_4);
    acqButton4.whileHeld(new DumpingReverseCommand());

    JoystickButton acqButton1 = new JoystickButton (oi.acquisitionController, RobotMap.BUTTON_1);
    acqButton1.whileHeld(new DumpingCommand());

    JoystickButton driveButton2 = new JoystickButton(oi.driveController, RobotMap.BUTTON_2);
    driveButton2.whenPressed(new CameraCommand());
  }

  @Override
  public void robotPeriodic() {
    double IR = oi.colorSensor.getIR();
    Color detectedColor = oi.colorSensor.getColor();
    String colorString;
    ColorMatchResult match = controlPanel.matchClosestColor(detectedColor);
    double confidencePercentage = .93; //TODO: Joy didn't know what she did in Feb.10, 2020... ???????
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

//--------------------------------------------------------------------------------------------------------------------------
// From here these are options for Autonomous's starting points
    
    //Variables
    double x = 24.25; //TODO: robot length plus the bumper's thickness
    double y = 31; //TODO: robot width plus the bumper's thickness
    double T = 39.71; //Vertical distance between the first triangle’s bottom and the middle line of the whole field (inches)
    double TrenchWidth = 56; //B //Vertical distance between bottom corner of power port and random point on player station that lines up with trench = B ; equal to width of Trench
    double shootDistance = 0;
    double Jimin = 0.5*y + 105.625 + T + 24; //vertical distance lining with the inner edge of the opponent's trench lining with the center point of the power port
    double Tae = 0.75*x + (120 - 0.5*x - T); //horizontal distance from where robot crosses the line that lines up with the shooting spot in front of power port
    double a = Math.toDegrees(Math.atan(Jimin/Tae)); // Angle that we need to turn in order to approach the power port diagonally 
                                                                //for dumping from the right starting position
    double MCRGerard = Math.pow((Math.pow(Jimin, 2.0) + Math.pow(Tae, 2.0)), 0.5); //the diagonal path; Jimin, Tae and MCRGerard form a triangle.
    double Fred = (56-(0.5*y)) + Math.tan(a)*(0.75*x) + (Math.cos(a)*(0.5*y)+Math.sin(a)*(0.5*x)); //(tell the alliance team) distance from the right wall to the invisible horizontal line that the alliance team should stay left of (so that we could run option “DiagonalDump”. If the alliance stay right of line M, we need to run option “90degreesDump”)
    //Grace 2.0 makes Java happy! JOY DOES TOO!!!!!!!! ALSO AMY!!!!

//----------------------------------------------------------------------------------------------------------------------------
//***All options start facing forward, except "Center_Cross_and_Dump"  AND  "Right_Cross_and_DiagonalDump"
//***All the directions are from robot’s perspective

    //1st Choice!
        //Start position: Face back, right in front of the Port
        //Choose this option if our team is the strongest among alliance teams
  /*else*/ if(am == AutoMode.Center_Cross_and_Dump) {
        AutoDriveForward driveCrossTheLine = new AutoDriveForward((-1)*(0.75*x)); // drive backward
        AutoDriveForward driveToPort = new AutoDriveForward((0.75*x + 120) - (0.5*x + shootDistance));

        //Add the actions to the Sequential
        autonomousCommand.addSequential(driveCrossTheLine);
        autonomousCommand.addSequential(driveToPort);
    }

    //Option 2
        //Starting Positiom: right side lining w/ the edge of Trench, 4 ft 8 in away from the left wall, center of robot is on the line
        // Only if alliance's robot in center starting position is far enough toward the rights side of the feild so that we don't run into them
    else if(am == AutoMode.Left_Cross_and_Dump){
      //Go to shoot
      Rotation driveTilFrontOfPort = new Rotation (90);
      AutoDriveForward driveForward= new AutoDriveForward ((0.5*x) + TrenchWidth + (0.5*48));
      Rotation turnRight90 = new Rotation (90);
      AutoDriveForward crossLine = new AutoDriveForward (-1*(0.75*x));
      //Cross the Line
      AutoDriveForward driveToPort = new AutoDriveForward (((0.75*x) + 120) - ((0.5*y) + shootDistance));
      AutoDumpingCommand dump = new AutoDumpingCommand(); //deliver power cell      

      // Add the actions to the Sequential
      autonomousCommand.addSequential(driveTilFrontOfPort);
      autonomousCommand.addSequential(driveForward);
      autonomousCommand.addSequential(turnRight90);
      autonomousCommand.addSequential(crossLine);   
      autonomousCommand.addSequential(driveToPort);   
      autonomousCommand.addSequential(dump);   

    }

    //Option 3
        //Starting Point: FACING BACK! Left side lining w/ the edge of Trench, 4 ft 8 in away from the right wall, Center of robot on the initial line
        //Only if other teams’ positions don’t interfere with our diagonal path
        //Choose this option if: If the alliance robot at “center position” stays anywhere left of the invisible line 
                                //that is Fred inches away from the right wall, then run this option.
        //If the alliance robot at “center position” stays anywhere right of the invisible line that is Fred inches away from the right wall, 
            //then run another option “Right_Cross_and_90degreesDump” (Option 4). 
    else if(am == AutoMode.Right_Cross_and_DiagonalDump){
     //Cross the line by drving backward
     AutoDriveForward crossLine = new AutoDriveForward (-1*(0.75*x));
     //Go diagonally til front of the port.
     //turn right for "a" degrees
     Rotation turnRight_a = new Rotation (a); 
     AutoDriveForward driveToPort = new AutoDriveForward(MCRGerard);
     //Turn to face the Port
     Rotation turnLeft_a = new Rotation ((-1)*a);
     //now the robot is right in front of the port, ready to dump.
     AutoDumpingCommand dump = new AutoDumpingCommand(); //deliver power cells
     
      // Add the actions to the Sequential
      autonomousCommand.addSequential(crossLine);
      autonomousCommand.addSequential(turnRight_a);
      autonomousCommand.addSequential(driveToPort);
      autonomousCommand.addSequential(turnLeft_a);
      autonomousCommand.addSequential(dump);   
    }

    //Option 4
    // Start position: Left side lining w/ the edge of Trench, 4 ft 8 in away from the right wall, Center of robot on the initial line
    // Robot goes around the sector instead of going diagonally.
    // Choose this option if the alliance robot at “center position” stays anywhere right of the invisible line that is Fred inches away from the right wall.
    else if(am == AutoMode.Right_Cross_and_90degreesDump) {
      AutoDriveForward crossLine = new AutoDriveForward (0.75*x);
      Rotation turnLeft90 = new Rotation (-90);
      AutoDriveForward driveToFrontOfPort = new AutoDriveForward (Jimin);
      //PUT IN turnLeft90
      AutoDriveForward driveToPort = new AutoDriveForward(Tae);
      AutoDumpingCommand dump = new AutoDumpingCommand();
      
       // Add the actions to the Sequential
       autonomousCommand.addSequential(crossLine);
       autonomousCommand.addSequential(turnLeft90);
       autonomousCommand.addSequential(driveToFrontOfPort);
       autonomousCommand.addSequential(turnLeft90);
       autonomousCommand.addSequential(driveToPort);   
       autonomousCommand.addSequential(dump);
     }

    //Option 5
    // Start Position: ANY! Face forward
    // Only cross the line
    // Last choice!
    else if(am == AutoMode.CrossOnly) {
      
    AutoDriveForward crossDriveForward = new AutoDriveForward (0.75*x);

    autonomousCommand.addSequential(crossDriveForward);


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
    Scheduler.getInstance().run();

  }
}
