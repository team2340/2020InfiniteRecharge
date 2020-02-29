package frc.robot.Commands;

import com.revrobotics.ColorMatchResult;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Robot;

public class ColorSensorPositionCommand extends Command {

    Integer ourTargetColor = 0;
    Integer judgesTargetColor = 0;
    Color detectedColor = null;
    Double arcLength = 12.5; //inches
    Integer wedgeNumber = 0; //will be defined in the initialize function.
    Color lastColorSeen = null;

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

        detectedColor = Robot.oi.colorSensor.getColor();

        //find the number of wedges that we need to go across
        //match.color is the color closet to the RGB detected by the sensor.
        ColorMatchResult match = Robot.controlPanel.matchClosestColor(detectedColor);
        if (match.color == Robot.controlPanel.kBlueTarget) {
            wedgeNumber = ourTargetColor - blue;
          } else if (match.color == Robot.controlPanel.kRedTarget) {
            wedgeNumber = ourTargetColor - red;
          } else if (match.color == Robot.controlPanel.kGreenTarget) {
            wedgeNumber = ourTargetColor - green;
          } else if (match.color == Robot.controlPanel.kYellowTarget) {
            wedgeNumber = ourTargetColor - yellow;
          } else {
            wedgeNumber = ourTargetColor;
          }

        lastColorSeen = detectedColor;
	}


	@Override
	protected void execute() {
        if (wedgeNumber < 0){
            Robot.controlPanel.controlPanelReverse();
        }
        else if (wedgeNumber > 0){
            Robot.controlPanel.controlPanelForward();
        }

        detectedColor = Robot.oi.colorSensor.getColor();
        
        if(detectedColor != lastColorSeen){
            if(wedgeNumber > 0){
                wedgeNumber--;
            }
            else if(wedgeNumber < 0){
                wedgeNumber++;
            }
        }

        lastColorSeen = detectedColor;
	}

	@Override
	protected boolean isFinished() {
        if(wedgeNumber == 0){
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
