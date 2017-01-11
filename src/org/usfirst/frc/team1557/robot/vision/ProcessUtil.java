package org.usfirst.frc.team1557.robot.vision;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/*
 * I'm only going to be working in the Vision package. 
 * You should be free to edit anything in the other packages and we won't have any conflicts.
 */
/**
 * All of the methods should work like normal java methods. ie. They will return
 * a new object instead of overriding.
 * 
 * @author Levi
 *
 */
public class ProcessUtil {
	/**
	 * This is the method we used last year. We'll need to check that it works.
	 * 
	 * @param url
	 * @return
	 */
	public static Mat getImageInMat(URL url) {
		try {
			URLConnection con = url.openConnection();
			con.connect();
			int length = con.getContentLength();
			if (length == -1) {
				throw new IOException("COuld not read file length!");
			}
			int i = 0;
			int numRead;
			byte[] data = new byte[length];
			InputStream in = con.getInputStream();
			do {
				numRead = in.read(data, i, data.length - i);
				i += numRead;
			} while (numRead != -1);
			Mat image = Imgcodecs.imdecode(new MatOfByte(data), Imgcodecs.IMREAD_COLOR);
			return image;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * What I did last year doesn't make sense. I'm going to try and re do it.
	 * I'm expecting something to not work and to revert back to last year's.
	 * 
	 * The scalars should be input in HSV format. I believe cvtColor requires
	 * you to use HSV format.
	 * 
	 * @param start
	 * @param min
	 * @param max
	 * @return The returned image should be in CV_8U format. However, the output
	 *         will basically be binary. Pixel will be white if it met the
	 *         scalar conditons. black if it failed. black = 0,0,0 white =
	 *         255,255,255 (If I remember correctly)
	 */
	public static Mat filterColors(Mat start, Scalar min, Scalar max) {
		Mat m = new Mat();
		Imgproc.cvtColor(start, m, Imgproc.COLOR_BGR2HSV);
		Core.inRange(start, min, max, m);
		return m;

	}

	public static Mat invertBinaryImage(Mat start) {
		Mat m = new Mat();
		Core.bitwise_not(start, m);
		return m;
	}
}
