import color_detection.Blue;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;

import color_detection.*;

//No need to import Camera as it is in default package

public class Main {
    private static VideoCapture CAM;

    public static void main(String[] args) {
	// insert below

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //Initialize camera object
        Camera camera = new Camera(0);
        CAM = camera.start();

        if (!CAM.isOpened()) {
            System.out.println("Unable to connect to camera");
        }

        else {
            System.out.println("Successfully connected to: " + camera.toString());
        }

        //Create matrix to read image
        Mat frame = new Mat();
        Mat copy = new Mat();

        //Indefinite loop to process feed
        while (true) {
            CAM.read(frame);
            frame.copyTo(copy);

            Red red = new Red(copy);
            Blue blue = new Blue(copy);
            Green green = new Green(copy);

            green.detectGreen();
            blue.detectBlue();
            red.detectRed();

            HighGui.imshow("frame", copy);
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
