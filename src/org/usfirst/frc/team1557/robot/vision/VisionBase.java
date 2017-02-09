package org.usfirst.frc.team1557.robot.vision;

import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team1557.robot.OI;

import java.awt.Color;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionBase {
	final double FOV_of_M1011 = 61;
	final double cameraWidth = 320;
	final double degreesPerPixel;

	{
		degreesPerPixel = (double) (FOV_of_M1011/* degrees */ / cameraWidth /* pixels */);
	}

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
	// CvSource tertiaryOutput;
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

	// NOTE: Thanks to Nicholas, I was able to find out what causes the too many
	// client streams error. The error is thrown whenever we are no longer
	// sending frames often enough to the smartdashboard stream, as I expected.
	// However! Any errors that are thrown within this thread will kill the
	// thread. Subsequently, ANY errors not caught in this thread will crash
	// this thread and print out that error. Also, you will need to change the
	// print settings on the driverstation in order to see the actual error that
	// caused the crash.
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
				cam.setBrightness(0);
				cam.setWhiteBalanceHoldCurrent();
				cam.setExposureHoldCurrent();
				cam.setResolution(320, 240);
				sink = CameraServer.getInstance().getVideo();
				outputStream = CameraServer.getInstance().putVideo("Post Porkcessing", 320, 240);
				secondaryOutput = CameraServer.getInstance().putVideo("Post Porkcessing 2: Electric Boogaloo", 320,
						240);
				// tertiaryOutput = CameraServer.getInstance().putVideo("Post
				// Porkcessing 3: Finalmente", 320, 240);
				// When using a Mat object, you must first create an empty
				// object of it before doing anything.
				currentPureFrame = new Mat();
				postProcessingFrame = new Mat();
				ArrayList<MatOfPoint> contours = new ArrayList<>();

				while (true) {
					// This is where the image processing code goes.
					if (process) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						// Acquires the current frame from the camera
						sink.grabFrame(currentPureFrame);
						double[] color = currentPureFrame.get(120, 160);
						if (OI.mainJoy.getRawButton(1))
							System.out.println(color[0] + " : " + color[1] + " : " + color[2]);
						postProcessingFrame = currentPureFrame;
						// cam.setExposureManual(20);
						// cam.setBrightness(80);
						// cam.setWhiteBalanceManual(50);

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
							cam.setBrightness(20);
							cam.setWhiteBalanceHoldCurrent();
							cam.setExposureHoldCurrent();
							// Imgproc.blur(postProcessingFrame,
							// postProcessingFrame, new Size(1, 1)); //:100:
							// :+1: :100:

							// Core.inRange(postProcessingFrame, new Scalar(70,
							// 150, 0), new Scalar(390, 360, 360),
							// postProcessingFrame);
							// cam.setExposureHoldCurrent();
							// cam.setWhiteBalanceHoldCurrent();
							Mat copy = postProcessingFrame.clone();

							Imgproc.cvtColor(copy, copy, Imgproc.COLOR_BGR2GRAY);
							Imgproc.cvtColor(postProcessingFrame, postProcessingFrame, Imgproc.COLOR_BGR2RGB);
							Core.inRange(postProcessingFrame, new Scalar(0, 80, 0), new Scalar(180, 180, 180),
									postProcessingFrame);
							Imgproc.blur(copy, copy, new Size(2, 2));
							Imgproc.threshold(copy, copy, 40, 255, Imgproc.THRESH_BINARY);

							Mat and = copy.clone();
							Core.bitwise_and(copy, postProcessingFrame, and);
							Imgproc.cvtColor(postProcessingFrame, postProcessingFrame, Imgproc.COLOR_GRAY2BGR);

							// SmartDashboard.putNumber("Contours",
							// contours.size());
							secondaryOutput.putFrame(postProcessingFrame);
							Imgproc.findContours(and, contours, new Mat(), Imgproc.RETR_LIST,
									Imgproc.CHAIN_APPROX_SIMPLE);
							int largestID = 0;
							double xs[] = new double[] { 0, 0 };
							if (contours.size() > 0) {
								largestID = findLargestContour(contours);
								double[] XY = findXY(contours.get(largestID));
								xs[0] = XY[0];

								Imgproc.cvtColor(and, and, Imgproc.COLOR_GRAY2BGR);
								Imgproc.drawContours(and, contours, findLargestContour(contours),
										new Scalar(200, 100, 200));

								Imgproc.drawMarker(and, new Point(XY[0] + 320 / 2, XY[1]),

										new Scalar(Math.random() * 255, Math.random() * 255, Math.random() * 255));
								// contours.clear();
								SmartDashboard.putNumber("Largest width",
										Imgproc.boundingRect(contours.get(largestID)).width);
								if (contours.size() > 1) {
									contours.remove(largestID);
									largestID = findLargestContour(contours);
									SmartDashboard.putNumber("Second Largest Width",
											Imgproc.boundingRect(contours.get(largestID)).width);
									XY = findXY(contours.get(largestID));
									xs[1] = XY[0];
									Imgproc.drawContours(and, contours, findLargestContour(contours),
											new Scalar(200, 100, 200));

									Imgproc.drawMarker(and, new Point(XY[0] + 320 / 2, XY[1]),
											new Scalar(Math.random() * 255, Math.random() * 255, Math.random() * 255));
									setAngleOff(((xs[0] + xs[1]) / 2) * degreesPerPixel);
									SmartDashboard.putNumber("X degrees off", ((xs[0] + xs[1]) / 2) * degreesPerPixel);
									SmartDashboard.putNumber("Distance Relative", Math.abs(xs[0] - xs[1]));
								} else {
									setAngleOff(0);
									SmartDashboard.putNumber("X degrees off", 0);
								}
							} else {
								setAngleOff(0);
								SmartDashboard.putNumber("X degrees off", 0);
							}

							outputStream.putFrame(and);
							contours.clear();
							and.release();
							copy.release();
							postProcessingFrame.release();
							currentPureFrame.release();

						}
					}
				}
			}
		}).start();

	}

	private double angleOff = 0;

	void setAngleOff(double newAngle) {
		synchronized (this) {
			this.angleOff = newAngle;
		}
	}

	public double getAngleOff() {
		double d;
		synchronized (this) {
			d = angleOff;
		}
		return d;

	}

	/**
	 * @param m
	 * @return The id of the largest contour
	 */
	int findLargestContour(ArrayList<MatOfPoint> m) {
		double largestArea = 0;
		int id = 0;
		for (int i = 0; i < m.size(); i++) {
			if (Imgproc.contourArea(m.get(i)) > largestArea) {
				largestArea = Imgproc.contourArea(m.get(i));
				id = i;
			}
		}
		return id;
	}

	/**
	 * does stuff
	 * 
	 * @param m
	 * @return no probs
	 */
	double[] findXY(MatOfPoint m) {
		Rect r = Imgproc.boundingRect(m);
		double x = r.x + r.width / 2;
		double y = r.y + r.height / 2;
		x -= 320 / 2;
		y -= 240 / 120;
		return new double[] { x, y };
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
