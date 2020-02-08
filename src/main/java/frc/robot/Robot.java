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
    autoMode.addOption("Middle", AutoMode.Middle);
		autoMode.addOption("Home", AutoMode.Home);
		autoMode.addOption("Test", AutoMode.Test);
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
    if(match != null)
    {
      if(match.confidence >= .93) //TODO: Move confidence into matchClosestColor function (or maybe have confidence be a parameter)
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
  
    else if(am == AutoMode.Middle){
    }
    else if(am == AutoMode.DriveForward){
      AutoDriveForward drive5 = new AutoDriveForward (5); //We need to know unit
      Rotation rotate70 = new Rotation (70);


      autonomousCommand.addSequential(drive5);
      autonomousCommand.addSequential(rotate70);
    }
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
