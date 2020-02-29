package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Robot;

// TODO: SOMETHING ABOUT THE SPEED OF THE MOTOR NEED TO BE CHANGED LATER.

public class ColorSensorRotationCommand extends Command {

    public ColorSensorRotationCommand() {
		requires(Robot.controlPanel);
	}

	Color startColor = null;
	int countRevolution = 0;
	int countForDetectedColor = 1; // 1means olor sensor already sees the startColor for one time
	Color detectedColor = null;
	Color lastDetectedColor = null; // the color in the last wedge(triangle) 20 milliseconds ago, so it differs from teh current new detectedColor


	@Override
	protected void initialize() {
		startColor = Robot.oi.colorSensor.getColor();
		// Record what the color sensor sees
		lastDetectedColor = Robot.oi.colorSensor.getColor();

	}


	@Override
	protected void execute() {

		//rotates
		Robot.controlPanel.controlPanelForward();

		// Sees the current new color
		detectedColor = Robot.oi.colorSensor.getColor();

		// When moves on to next color wedge
		if (detectedColor != lastDetectedColor) {
			//When the start color reappears
			if (detectedColor == startColor) {
				countForDetectedColor ++;
			}
		}
		
		// When the color panel has already done one reolution
		if (countForDetectedColor == 3 /* 3 means one revolution is completed, so the sensor sees teh startCoor for the 3rd time*/) {
			countRevolution ++;
			countForDetectedColor = 1;
		}

		// Record what the color sensor sees
		lastDetectedColor = Robot.oi.colorSensor.getColor();


	}

	// When to stop? When to restart over?
	@Override
	protected boolean isFinished() {

		//Mission completed
		if(countRevolution == 3 || countRevolution == 4){
			return true;
		}

		//Restarts task if panel has too many revolutions
		else if (countRevolution == 5) {
			countRevolution = 0;
			countForDetectedColor = 1;
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
