/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*------frc.robot----------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//magic numbers~~
		public static final int BUTTON_1 = 1;
		public static final int BUTTON_2 = 2; 
		public static final int BUTTON_3 = 3;
		public static final int BUTTON_4 = 4;
		public static final int BUTTON_5 = 5;
		public static final int BUTTON_6 = 6;
		public static final int BUTTON_7 = 7;
	/*Controller Ports*/
		public static final int DRIVE_PORT = 1;
		public static final int ACQUISITION_PORT = 2;
	/*Limit Swich IDs */
		public static final int ELEVATOR1_BOTTOM_ID = 0;
		public static final int ELEVATOR2_BOTTOM_ID = 1;
	/*CAN IDs*/
		//Wheel Ids
		public static final int FRONT_RIGHT_TAL_ID = 3;
		public static final int FRONT_LEFT_TAL_ID = 1;
		public static final int BACK_RIGHT_TAL_ID = 4;
		public static final int BACK_LEFT_TAL_ID = 2;
		//Acquisition Ids
		public static final int ACQUISITION_TAL_ID = 7;
		public static final int CLIMB1_TAL_ID = 5;
		public static final int CLIMB2_TAL_ID = 6;
		public static final int CONTROLPANEL_NEO_ID = 50; 
		public static final int DUMPING_TAL_ID = 8;
	}
