package frc.robot;

public class RobotUtils {
	private static double wheelDiameter = 4;
	private static double lengthOfRobot = 33;
	private static double widthOfRobot = 28;
	private static double heightOfRobotArms = 0;
	private static double heightOfBox = 0;
	private static double heightOfRobotFromTheGround = 3;
	public static  double elevatorRev = 0.314961;
	public enum AutoMode {
		DISABLED,
		// DriveForward,
		Right_Cross_and_DiagonalDump,
		Center_Cross_and_Dump,
		Left_Cross_and_Dump,
		Right_Cross_and_90degreesDump,
		CrossOnly,
		DriveWithTime
	}

	
	public static void heightOfBox(double _heightOfBox) {
		heightOfBox = _heightOfBox;
	}
	public static void lengthOfRobot(double _lengthOfRobot) {
		lengthOfRobot = _lengthOfRobot;
	}
	public static void widthOfRobot(double _widthOfRobot) {
		widthOfRobot = _widthOfRobot;
	}
	public static void heightOfRobotArms(double _heightOfRobotArms) {
		heightOfRobotArms = _heightOfRobotArms;
	}
	public static double getEncPositionFromIN(double distanceInInches) {
		return (distanceInInches/(wheelDiameter * Math.PI))*306; //Create a function to do the inverse thing from enc to in
	}
	
	public static double getLengthOfRobot() {
		return lengthOfRobot;
	}
	public static double getWidthOfRobot() {
		return widthOfRobot;
	}
	public static double getHeightOfRobotArms() {
		return heightOfRobotArms;
	}
	public static double getHeightOfBox() {
		return heightOfBox;
	}
	public static void setWheelDiameter(double _wheelDiameter) {
		wheelDiameter = _wheelDiameter;
	}
	
	public static void heightOfRobotFromTheGround(double _heightOfRobotFromTheGround){
		heightOfRobotFromTheGround = _heightOfRobotFromTheGround;
	}
	public static double getHeightOfRobotFromTheGround(){
		return heightOfRobotFromTheGround;
	}
}
