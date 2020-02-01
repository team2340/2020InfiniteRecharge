/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Commands.AutoDriveForward;
import frc.robot.Commands.AutoDumpingCommand;
import frc.robot.Commands.Rotation;
import frc.robot.RobotUtils.AutoMode;
//import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
//import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.DumpingSubsystem;

public class Robot extends TimedRobot {
  public static final OI oi = new OI();
   public static DriveSubsystem drive = null;
  // public static AcquisitionSubsystem acquisition = null;
   //public static ControlPanelSubsystem controlPanel = null;
   public static DumpingSubsystem dumping = DumpingSubsystem.getInstance();
   //public static ArmSubsystem arm = null;
   //public static ClimbSubsystem climb = null;
   //public static final DebugLogger myLogger = new DebugLogger();
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

    //CameraServer.getInstance().startAutomaticCapture();
    drive = DriveSubsystem.getInstance();         
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
  }

  @Override
  public void autonomousInit() {
    AutoMode am = (AutoMode) autoMode.getSelected();
    autonomousCommand = new CommandGroup();

    if(am == AutoMode.DISABLED) {
    }
  
    else if(am == AutoMode.Middle){
      autonomousCommand = new CommandGroup();
    }
    else if(am==AutoMode.DriveForward){
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
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
  }
}
