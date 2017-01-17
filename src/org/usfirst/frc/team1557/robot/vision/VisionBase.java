package org.usfirst.frc.team1557.robot.vision;

import org.opencv.imgproc.Imgproc;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionBase {
	// TODO: Looks into CameraServer.getInstance().putVideo() for custom
	// DriverStation images/video

	public VisionBase() {

	}

	public void init(String name, String... ip) {

	}

	public double[] process() {
		// As with all cases when using a mat, an empty mat must be created
		// first.
		Random r = new Random();
		Mat currentFrame = new Mat();
		try {
			currentFrame = ProcessUtil.getImageInMat(new URL("http://10.15.57.56/mjpg/video.mjpg"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		currentFrame = ProcessUtil.filterColors(currentFrame, new Scalar(100, 100, 100), new Scalar(200, 200, 200));

		List<MatOfPoint> m = ProcessUtil.createContours(currentFrame);
		System.out.println(m.size());
		// currentFrame.
		for (int i = 0; i < m.size(); i++) {
			Imgproc.drawContours(currentFrame, m, i, new Scalar(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
		}
		// putImage(currentFrame);

		return null;
	}

}
