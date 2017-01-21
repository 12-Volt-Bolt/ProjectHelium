package org.usfirst.frc.team1557.robot.vision;

import org.opencv.imgproc.Imgproc;

import java.awt.Color;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionBase {

	// DriverStation images/video
	AxisCamera cam;
	/**
	 * This is the object used to acquire the current frame. Call
	 * sink.grabFrame() and supply a Mat object. For example,
	 * sink.grabFrame(currentPureFrame);
	 */
	CvSink sink;
	/**
	 * This object is used to publish a video to the SmrtDrshbrd
	 */
	CvSource outputStream;
	CvSource secondaryOutput;
	/**
	 * This object should be kept pure. Any processing should be applied to the
	 * postProcessingFrame
	 */
	Mat currentPureFrame;
	/**
	 * This should be the Mat object that is pushed to the outputStream
	 */
	Mat postProcessingFrame;
	/**
	 * "'BINGO BONGO get out!' ...is documentation" - Jim Lenahan 2k17
	 */
	private boolean process = false;

	int b = 220;
	int g = 255;
	int r = 72;
	float[] hsv;

	public VisionBase() {

	}

	/**
	 * Start running the camera. It doesn't start processing the images until
	 * you call
	 * 
	 * @param name
	 * @param ip
	 */
	public void start(String name, String ip) {
		new Thread(new Runnable() {

			@Override
			public void run() {

				// Camera setup code
				cam = CameraServer.getInstance().addAxisCamera(name, ip);

				cam.setResolution(320, 240);
				sink = CameraServer.getInstance().getVideo();
				outputStream = CameraServer.getInstance().putVideo("Post Porkcessing", 320, 240);
				secondaryOutput = CameraServer.getInstance().putVideo("Post Porkcessing 2: Electric Boogaloo", 320,
						240);
				// When using a Mat object, you must first create an empty
				// object of it before doing anything.
				currentPureFrame = new Mat();
				postProcessingFrame = new Mat();
				ArrayList<MatOfPoint> contours = new ArrayList<>();

				while (true) {
					// This is where the image processing code goes.
					if (process) {
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// Acquires the current frame from the camera
						sink.grabFrame(currentPureFrame);
						double[] color = currentPureFrame.get(120, 160);
						System.out.println(color[0] + " : " + color[1] + " : " + color[2]);
						postProcessingFrame = currentPureFrame;
						cam.setExposureManual(20);
						cam.setBrightness(80);
						cam.setWhiteBalanceManual(50);

						/*
						 * Basic theory: Go! After acquiring the image, you
						 * first must filter by color. Call the
						 * Imgproc.inRange() function and supply a minimum HSV
						 * and maximum HSV. This will return a 'binary' image.
						 * With this image, we can detect contours(basically
						 * shapes). Then, we can find the bounding box of each
						 * individual contour and filter accordingly.
						 * 
						 * range was 70, 120, 120 to 100, 255, 255
						 * 
						 */
						if (true) {
							Imgproc.cvtColor(postProcessingFrame, postProcessingFrame, Imgproc.COLOR_BGR2HSV);
							Imgproc.blur(postProcessingFrame, postProcessingFrame, new Size(5, 5));
							Core.inRange(postProcessingFrame, new Scalar(70, 120, 120), new Scalar(100, 255, 255),
									postProcessingFrame);
							Imgproc.findContours(postProcessingFrame, contours, new Mat(), Imgproc.RETR_LIST,
									Imgproc.CHAIN_APPROX_SIMPLE);
							Imgproc.cvtColor(postProcessingFrame, postProcessingFrame, Imgproc.COLOR_GRAY2BGR);
							outputStream.putFrame(postProcessingFrame);
							SmartDashboard.putNumber("Contours", contours.size());
							// secondaryOutput.putFrame(currentPureFrame);
						} else if (false) {
						} else if (false) {
						}
						// outputStream.putFrame(postProcessingFrame);
					}
				}
			}
		}).start();

	}

	/**
	 * Starts the processing of images
	 */
	public void startProcess() {
		process = true;
	}

	/**
	 * Stops the processing of images
	 */
	public void stopProcess() {
		process = false;
	}

}
