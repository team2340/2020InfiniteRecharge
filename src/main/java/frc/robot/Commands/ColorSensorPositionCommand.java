package frc.robot.Commands;

import com.revrobotics.ColorMatchResult;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Robot;

public class ColorSensorPositionCommand extends Command {

    Integer ourTargetColor = 0;
    Integer judgesTargetColor = 0;
    Integer detectedColor = 0; //string
    Double arcLength = 12.5; //inches
    Integer wedgeNumber = 0; //will be defined in the initialize function.
    Integer lastColorSeen = 0; //string

    Integer yellow = 1;
    Integer red = 2;
    Integer green = 3;
    Integer blue = 4; 


    public ColorSensorPositionCommand() {
		requires(Robot.controlPanel);
	}

	@Override
	protected void initialize() {
        //find the number of wedges depending on the target color input.
        //the motor is spinning counter-clockwise; the panel is spinning clock wise.
        //the color sequence on the panel (clockwise) would be:
        //yellow -> red -> green -> blue.

        //targetColor = getInput

        judgesTargetColor = (Integer) Robot.judgesTargetColor.getSelected();

        //this converts the color assigned by the judges to the color that should be targeted by our sensor.
        if(judgesTargetColor == yellow){
            ourTargetColor = green;
        }
        else if(judgesTargetColor == red){
            ourTargetColor = blue;
        }
        else if(judgesTargetColor == green){
            ourTargetColor = yellow;
        }
        else if(judgesTargetColor == blue){
            ourTargetColor = red;
        }
        System.out.println("OurTargetColor is " + ourTargetColor);

        detectedColor = matchTheColor();

        //The code below was to find the wedge number based on the color detected.
        //find the number of wedges that we need to go across
        //match.color is the color closet to the RGB detected by the sensor.
        //ColorMatchResult match = Robot.controlPanel.matchClosestColor(detectedColor); 
        if (detectedColor==blue) {
            wedgeNumber = ourTargetColor - blue;
          } else if (detectedColor==red/*match.color == Robot.controlPanel.kRedTarget*/) {
            wedgeNumber = ourTargetColor - red;
          } else if (detectedColor==green/*match.color == Robot.controlPanel.kGreenTarget*/) {
            wedgeNumber = ourTargetColor - green;
          } else if (detectedColor==yellow/*match.color == Robot.controlPanel.kYellowTarget*/) {
            wedgeNumber = ourTargetColor - yellow;
          } else {
            wedgeNumber = ourTargetColor;
          }
          System.out.println("WedgeNumber is " + wedgeNumber);

        lastColorSeen = detectedColor;
	}


	@Override
	protected void execute() {
        if (wedgeNumber < 0){
            Robot.controlPanel.controlPanelReverse();
            System.out.println("ControlPanel----Reverse");
        }
        else if (wedgeNumber > 0){
            Robot.controlPanel.controlPanelForward();
            System.out.println("ControlPanel----Forward");
        }

        detectedColor = matchTheColor();
        
        if(detectedColor != lastColorSeen){
            if(wedgeNumber > 0){
                wedgeNumber--;
                System.out.println("WedgeNumber is " + wedgeNumber);
            }
            else if(wedgeNumber < 0){
                wedgeNumber++;
                System.out.println("WedgeNumber is " + wedgeNumber);
            }
        }

        lastColorSeen = detectedColor;
	}

    //matchTheColor returns the integer that represents the color detected by the sensor. 
    //The color approximation and confidence level are done in this function too.
    Integer matchTheColor() { //String
		Color detectedColor = Robot.oi.colorSensor.getColor();
		Integer /*String*/ colorInteger = lastColorSeen;
		ColorMatchResult match = Robot.controlPanel.matchClosestColor(detectedColor);
		if(match != null)
		{
			if(match.confidence >= Robot.controlPanel.confidenceLevel) //TODO: Move confidence into matchClosestColor function (or maybe have confidence be a parameter)
			{
				if (match.color == Robot.controlPanel.kBlueTarget) {
				colorInteger = blue;
				} else if (match.color == Robot.controlPanel.kRedTarget) {
                colorInteger = red;
				} else if (match.color == Robot.controlPanel.kGreenTarget) {
                colorInteger = green;
				} else if (match.color == Robot.controlPanel.kYellowTarget) {
                colorInteger = yellow;
				}
			}
		}
			return colorInteger; //changed from colorString
	}


	@Override
	protected boolean isFinished() {
        if(wedgeNumber == 0){
            System.out.println("WedgeNumber is 0 so stop");
            return true;
        }
		else{
            return false;
        }
    }	
    
    protected void end() {
        Robot.controlPanel.controlPanelStop();
    }
}
