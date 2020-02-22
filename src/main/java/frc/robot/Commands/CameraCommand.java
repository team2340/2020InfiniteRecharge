package frc.robot.Commands;

import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class CameraCommand extends Command {
    Boolean isCamera1 = true;
    VideoSink server;

	@Override
	protected void initialize() {
	
	}

	public CameraCommand() {
        server = CameraServer.getInstance().getServer();

	}

	@Override
	protected void execute() {
        if (isCamera1) {
            System.out.println("Setting camera 2");
            server.setSource(Robot.camera2);
            isCamera1 = false;
        } else {
            System.out.println("Setting camera 1");
            server.setSource(Robot.camera1);
            isCamera1 = true;
        }
	}

	@Override
	protected boolean isFinished() {
        return true;
    }

	
}