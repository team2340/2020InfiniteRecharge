package frc.robot;

public class RobotUtils {
	private static double wheelDiameter = 6;
	private static double lengthOfRobot = 33;
	private static double widthOfRobot = 28;
	private static double heightOfRobotArms = 0;
	private static double heightOfBox = 0;
	private static double armRatio = 1;
	private static double elevatorOneRatio = 1.5;
	private static double elevatorTwoRatio=2;
	private static double heightOfRobotFromTheGround = 3;
	public static  double elevatorRev = 0.314961;
	public enum AutoMode {
		DISABLED,
		DriveForward,
		CloseToPowerPort,
		FarFromPowerPort,
		MiddleToPowerPort
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
		return (distanceInInches/(wheelDiameter * Math.PI))*4096/*2900*/;
	}

	public static double getEncPositionFromINArms(double distanceInInches) {
		double armRev = 0.314961;//this is 8 mm in inches
		System.out.println("Encoder Ticks Per Revolutions for arms: "+armRev);
		return (distanceInInches /(armRev/armRatio) ) * 42;
	}
	
	// public static double getEncPositionFromINElevatorOne(double distanceInInches) {
	// 	System.out.println("Encoder Ticks Per Revolutions for elevator one: "+elevatorRev);
	// 	return (distanceInInches /(elevatorRev/elevatorOneRatio) ) * 42;
	// }
	// public static double getEncPositionFromINElevatorTwo(double distanceInInches) {
	// 	System.out.println("Encoder Ticks Per Revolutions for elevator two: "+elevatorRev);
	// 	return (distanceInInches /(elevatorRev/elevatorTwoRatio) ) * 42;
	// }
	// public static double getDistanceInInchesFromElevatorOne(double encoderVaule){
	// 	return ((encoderVaule/42)*(elevatorRev/elevatorOneRatio));
	// }
	// public static double getDistanceInInchesFromElevatorTwo(double encoderVaule){
	// 	return ((encoderVaule/42)*(elevatorRev/elevatorTwoRatio));
	// }
	// public static double distanceMinusRobot(double distance){
	// 	return distance-lengthOfRobot;
	// }
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
	public static void setArmRatio(double _armRate){
		armRatio = _armRate;
	}
	public static void setElevatorOneRatio(double _elevatorRate){
		elevatorOneRatio = _elevatorRate;
	}
	public static void setElevatorTwoRatio(double _elevatorRate){
		elevatorTwoRatio = _elevatorRate;
	}
	public static void heightOfRobotFromTheGround(double _heightOfRobotFromTheGround){
		heightOfRobotFromTheGround = _heightOfRobotFromTheGround;
	}
	public static double getHeightOfRobotFromTheGround(){
		return heightOfRobotFromTheGround;
	}
}
