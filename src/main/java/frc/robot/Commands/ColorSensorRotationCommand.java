package frc.robot.Commands;

import com.revrobotics.ColorMatchResult;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Robot;

// TODO: SOMETHING ABOUT THE SPEED OF THE MOTOR NEED TO BE CHANGED LATER.

public class ColorSensorRotationCommand extends Command {

    public ColorSensorRotationCommand() {
		requires(Robot.controlPanel);
	}

	String startColor = null;
	int countRevolution = 0;
	int countForDetectedColor = 1; // 1means olor sensor already sees the startColor for one time
	String detectedColor = null;
	String lastDetectedColor = null; // the color in the last wedge(triangle) 20 milliseconds ago, so it differs from teh current new detectedColor


	@Override
	protected void initialize() {
		countRevolution = 0;
		countForDetectedColor = 1;
		startColor = matchTheColor(); 
		// Record what the color sensor sees
		lastDetectedColor = startColor;
		System.out.println("Start Color is " + startColor);
	}


	@Override
	protected void execute() {

		//rotates
		Robot.controlPanel.controlPanelForward();

		// Sees the current new color
		detectedColor = matchTheColor();

		// When moves on to next color wedge
		if (detectedColor != lastDetectedColor) {
			System.out.println("Last detected Color is " + lastDetectedColor);
			System.out.println("New detected Color is " + detectedColor);
			//When the start color reappears
			if (detectedColor == startColor) {
				countForDetectedColor ++;
				System.out.println("count For Detected Color is " + countForDetectedColor);
			}
		}
		
		// When the color panel has already done one reolution
		if (countForDetectedColor == 3 /* 3 means one revolution is completed, so the sensor sees the startCoor for the 3rd time*/) {
			countRevolution ++;
			System.out.println("count Revolution is " + countRevolution);
			countForDetectedColor = 1;
		}

		// Record what the color sensor sees
		lastDetectedColor = detectedColor;
		
	}

	String matchTheColor() {
		Color detectedColor = Robot.oi.colorSensor.getColor();
		String colorString = lastDetectedColor;
		ColorMatchResult match = Robot.controlPanel.matchClosestColor(detectedColor);
		if(match != null)
		{
			if(match.confidence >= Robot.controlPanel.confidenceLevel) //TODO: Move confidence into matchClosestColor function (or maybe have confidence be a parameter)
			{
				if (match.color == Robot.controlPanel.kBlueTarget) {
				colorString = "Blue";
				} else if (match.color == Robot.controlPanel.kRedTarget) {
				colorString = "Red";
				} else if (match.color == Robot.controlPanel.kGreenTarget) {
				colorString = "Green";
				} else if (match.color == Robot.controlPanel.kYellowTarget) {
				colorString = "Yellow";
				}
			}
		}
			return colorString;
	}


	// When to stop? When to restart over?
	@Override
	protected boolean isFinished() {

		//Mission completed
		if(countRevolution == 3 || countRevolution == 4){
			System.out.println("Should end here cuz count Revolution is " + countRevolution);
			return true;
		}

		//Restarts task if panel has too many revolutions
		else if (countRevolution == 5) {
			countRevolution = 0;//maybe change countRevolution to 1 at the 5th rotation?
			countForDetectedColor = 1;
			System.out.println("Spun too far");
			return false;
		}
		
		else {
			return false;
		}
	}	

	protected void end() {
        Robot.controlPanel.controlPanelStop();
	}
	
	 
}
