package trackbars;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.Videoio;

import javax.swing.*;
import java.awt.*;


public class Trackbar {

    private static Scalar lower, upper;
    private static Mat hsv, mask;
    private static JFrame slider_frame_low, slider_frame_high;
    private static JSlider hue_low, val_low, sat_low, hue_high, val_high, sat_high;


    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //Initialize camera object
        VideoCapture camera = new VideoCapture(0, Videoio.CAP_DSHOW);

        if (!camera.isOpened()) {
            System.out.println("Unable to connect to camera");
        }

        else {
            System.out.println("Successfully connected to: " + camera.toString());
        }

        //Create matrix to read image. Images are stored in 3 by 3 matrices
        Mat frame = new Mat();

        hsv = new Mat();
        mask = new Mat();

        //Need copy as you cannot modify original frame
        Mat copy = new Mat();

        //Trackbar sliders
        hue_low = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        sat_low = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        val_low = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);

        hue_high = new JSlider(JSlider.HORIZONTAL, 0, 255, 255);
        sat_high = new JSlider(JSlider.HORIZONTAL, 0, 255, 255);
        val_high = new JSlider(JSlider.HORIZONTAL, 0, 255, 255);

        slider_frame_low = new JFrame("trackbar for low HSV");
        slider_frame_low.setPreferredSize(new Dimension(640, 480));
        slider_frame_low.add(hue_low, BorderLayout.BEFORE_FIRST_LINE);
        slider_frame_low.add(sat_low, BorderLayout.CENTER);
        slider_frame_low.add(val_low, BorderLayout.AFTER_LAST_LINE);

        slider_frame_high = new JFrame("trackbar for high HSV");
        slider_frame_high.setPreferredSize(new Dimension(640, 480));
        slider_frame_high.add(hue_high, BorderLayout.BEFORE_FIRST_LINE);
        slider_frame_high.add(sat_high, BorderLayout.CENTER);
        slider_frame_high.add(val_high, BorderLayout.AFTER_LAST_LINE);


        slider_frame_low.pack();
        slider_frame_low.setVisible(true);

        slider_frame_high.pack();
        slider_frame_high.setVisible(true);

        //Indefinite loop to process feed
        while (true) {
            camera.read(frame);
            frame.copyTo(copy);

            lower = new Scalar(hue_low.getValue(), sat_low.getValue(), val_low.getValue());
            upper = new Scalar(hue_high.getValue(), sat_high.getValue(), val_high.getValue());
            Imgproc.cvtColor(frame, hsv, Imgproc.COLOR_BGR2HSV);
            Core.inRange(hsv, lower, upper, mask);

            HighGui.imshow("mask", mask);



            int key = HighGui.waitKey(1);
            if (key == 27) {
                System.out.println("Camera released");
                break;
            }

        }

        camera.release();
        HighGui.destroyAllWindows();

    }

}
