package color_detection;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

public class Red {
    Mat FRAME;
    private static Scalar lower, upper;
    private static Mat hsv, mask;

    public Red(Mat frame) {
        this.FRAME = frame;
    }

    public void detectRed() {
        ArrayList<MatOfPoint> contours = extractContours(createMask(FRAME));
        for (MatOfPoint contour: contours) {
            if (Imgproc.contourArea(contour) > 200) {
                drawBox(FRAME, contour, new Scalar(0, 255, 0), 1);
            }
        }
    }

    private void drawBox(Mat frame, MatOfPoint contour, Scalar color, int thickness) {
        Rect rect = Imgproc.boundingRect(contour);
        Imgproc.rectangle(frame, rect.tl(), rect.br(), color, thickness);
        Imgproc.putText(frame, "Red", rect.tl(), Imgproc.FONT_HERSHEY_SCRIPT_COMPLEX, 1, new Scalar(0, 255, 0));
    }

    private ArrayList<MatOfPoint> extractContours(Mat mask) {
        Mat hierarchy = new Mat();
        ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();

        Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        return contours;
    }

    private Mat createMask(Mat frame) {
        hsv = new Mat();
        mask = new Mat();

        lower = new Scalar(158, 84, 128);
        upper = new Scalar(255, 255, 174);

        Imgproc.cvtColor(frame, hsv, Imgproc.COLOR_BGR2HSV);
        Core.inRange(hsv, lower, upper, mask);

        return mask;
    }


}
