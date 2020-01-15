package frc.robot.LimitSwitches;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimitSwitch  {

	private DigitalInput limitSwitch = null;
	public Integer pinNumber;
    public LimitSwitch(Integer pin) {
		pinNumber=pin;
		// System.out.print("Created limit switch: " + pin);
    	limitSwitch = new DigitalInput(pin);
    }

    public boolean read() {
		// System.out.println("limitSwitch "+pinnumber+" "+limitSwitch.get());
		
    	// while (limitSwitch.get()) {
    	// 	Timer.delay(.01);
		// }
		var switchValue = limitSwitch.get();
		SmartDashboard.putBoolean("Limit Switch " + pinNumber, switchValue);
		return switchValue;
	}
}	

