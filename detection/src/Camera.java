
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class Camera extends VideoCapture {
    private VideoCapture CAMERA;
    private int WIDTH, HEIGHT, SOURCE;


    public Camera() {
        this.SOURCE = 0;
        this.WIDTH = 640;
        this.HEIGHT = 480;
    }

    public Camera(int source) {
        this.SOURCE = source;
        this.WIDTH = 640;
        this.HEIGHT = 480;

    }

    public Camera(int source, int height, int width) {
        this.SOURCE = source;
        this.HEIGHT = height;
        this.WIDTH = width;
    }


    public VideoCapture start() {
        this.CAMERA = new VideoCapture(SOURCE, Videoio.CAP_DSHOW);
        this.CAMERA.set(Videoio.CAP_PROP_FRAME_WIDTH, WIDTH);
        this.CAMERA.set(Videoio.CAP_PROP_FRAME_HEIGHT, HEIGHT);
        return CAMERA;

    }

}
