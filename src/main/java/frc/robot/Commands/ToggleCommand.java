package frc.robot.Commands;

import edu.wpi.first.wpilibj.command.Command;

interface FuncInterface
{
    public boolean run();
}

public class ToggleCommand extends Command {
	FuncInterface operation = null;
    boolean mode = false;

    ToggleCommand(){}
    ToggleCommand(FuncInterface _op) { operation = _op; }
    
    protected void initialize() {
        mode = !mode; 
        System.out.println("mode "+mode);
    }
    @Override
    protected boolean isFinished() {
        return ((operation != null)? operation.run() : true); 
    }//this true means we are done swapping

    public boolean GetToggle(){ return mode; }
}
