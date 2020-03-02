package frc.robot.subsystems;

import java.util.ArrayList;

import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.ColorShim;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class ControlPanelSubsystem extends Subsystem { 
    public final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    public final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    public final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    public final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
    private ArrayList<Color> m_colorsToMatch = new ArrayList<Color>();

    public ControlPanelSubsystem() {
        create();
        I2C.Port i2cPort = I2C.Port.kOnboard;
        Robot.oi.colorSensor = new ColorSensorV3(i2cPort);
        m_colorsToMatch.add(kBlueTarget);
        m_colorsToMatch.add(kGreenTarget);
        m_colorsToMatch.add(kRedTarget);
        m_colorsToMatch.add(kYellowTarget);
	}

	private void create() {
		try {
            Robot.oi.controlPanel = new CANSparkMax(RobotMap.CONTROLPANEL_NEO_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
            Robot.oi.controlPanel.setIdleMode(IdleMode.kBrake); //if motor not moving
		} catch (Exception ex) {
			System.out.println("createControlPanel FAILED");
		}
    }

    public void controlPanelForward(){
        Robot.oi.controlPanel.set(.1);
    }

    public void controlPanelReverse(){
        Robot.oi.controlPanel.set(-.1);
    }
    
    public void controlPanelStop(){
        Robot.oi.controlPanel.set(0);
    }

    @Override
    protected void initDefaultCommand() {
        //setDefaultCommand(new ControlPanelCommand());
    }

    private static double CalculateDistance(Color color1, Color color2) {
        double redDiff = color1.red - color2.red;
        double greenDiff = color1.green - color2.green;
        double blueDiff = color1.blue - color2.blue;
    
        double distanceVal = Math.sqrt((redDiff*redDiff + greenDiff*greenDiff + blueDiff*blueDiff)/2);
        return distanceVal;
    }
    
    public ColorMatchResult matchClosestColor(Color color) {
        double magnitude = color.red + color.blue + color.green;

        if (magnitude > 0.0 && m_colorsToMatch.size() > 0) {
            Color normalized = new ColorShim(color.red / magnitude, color.green / magnitude, color.blue / magnitude);
            double minDistance = 1.0;
            int idx = 0;

            for (int i=0; i < m_colorsToMatch.size(); i++) {
                double targetDistance = CalculateDistance(m_colorsToMatch.get(i), normalized);
                if (targetDistance < minDistance) {
                    minDistance = targetDistance;
                    idx = i;
                }
            }
            ColorMatchResult match = new ColorMatchResult(m_colorsToMatch.get(idx), 1.0 - minDistance);
            return match;
        } else {
            //return frc::Color::kBlack;
            return new ColorMatchResult(Color.kBlack, 0.0);
        }
    }
}