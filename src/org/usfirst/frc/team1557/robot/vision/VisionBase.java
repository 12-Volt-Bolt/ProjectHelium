package org.usfirst.frc.team1557.robot.vision;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.first.wpilibj.CameraServer;

public class VisionBase {
	// TODO: Looks into CameraServer.getInstance().putVideo() for custom
	// DriverStation images/video
	CvSink sink;

	public VisionBase() {
	}

	public void init(String name, String... ip) {
		CameraServer.getInstance().addAxisCamera(name, ip);
		sink = CameraServer.getInstance().getVideo();

	}

	public double[] process() {
		//As with all cases when using a mat, an empty mat must be created first.
		Mat currentFrame = new Mat();
		sink.grabFrame(currentFrame);
		return null;
	}

}
