/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public Joystick driveController = new Joystick(RobotMap.DRIVE_PORT);
	public Joystick acquisitionController = new Joystick(RobotMap.ACQUISITION_PORT);
	public ADXRS450_Gyro gyro = null;
   	public ColorSensorV3 colorSensor = null;

	public WPI_TalonSRX frontLeft = null;
	public WPI_TalonSRX backLeft = null;
	public WPI_TalonSRX frontRight = null;
	public WPI_TalonSRX backRight = null;
	public WPI_TalonSRX acquisition = null;
	public CANSparkMax controlPanel = null;
	public WPI_TalonSRX dumping = null;
	// public CANSparkMax elevator1 = null;
	public WPI_TalonSRX climb1 = null;
	public WPI_TalonSRX climb2 = null;
	// public WPI_TalonSRX arm = null;
	
	public final double CAM_VIEWING_ANGLE = 61.0;
	public final double IMG_WIDTH = 160.0;
	public final double IMG_HEIGHT = 120.0;
}
