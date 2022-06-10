package color_detection;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Green {
    Mat FRAME;
    private static Scalar lower, upper;
    private static Mat hsv, mask;

    public Green(Mat frame) {
        this.FRAME = frame;
    }

    public void detectGreen() {
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
        Imgproc.putText(frame, "Green", rect.tl(), Imgproc.FONT_HERSHEY_SCRIPT_COMPLEX, 1, new Scalar(0, 255, 0));
    }

    private ArrayList<MatOfPoint> extractContours(Mat mask) {
        Mat hierarchy = new Mat();
        ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();

        Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        return contours;
    }

    private Mat createMask(Mat frame) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject file = (JSONObject) parser.parse(new FileReader("E:\\sathya\\detection\\src\\color_detection\\constants.json"));
            hsv = new Mat();
            mask = new Mat();

            //lower = new Scalar(53, 51, 0);
            //upper = new Scalar(98, 255, 107);

            HashMap data_green = (HashMap) file.get("hsv_green");

            lower = new Scalar(Double.valueOf(String.valueOf(data_green.get("low_h"))), Double.valueOf(String.valueOf(data_green.get("low_s"))), Double.valueOf(String.valueOf(data_green.get("low_v"))));
            upper = new Scalar(Double.valueOf(String.valueOf(data_green.get("high_h"))), Double.valueOf(String.valueOf(data_green.get("high_s"))), Double.valueOf(String.valueOf(data_green.get("high_v"))));


            Imgproc.cvtColor(frame, hsv, Imgproc.COLOR_BGR2HSV);
            Core.inRange(hsv, lower, upper, mask);
        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();

        }

        return mask;
    }

}
