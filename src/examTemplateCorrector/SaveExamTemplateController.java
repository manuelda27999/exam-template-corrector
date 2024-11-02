package examTemplateCorrector;

import database.UtilityCSV;
import static examTemplateCorrector.WorkWithCircles.*;
import static examTemplateCorrector.WorkWithRectangles.*;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class SaveExamTemplateController {

    private static final int TOTAL_QUESTIONS = 40;
    private static final double CORRECT_SCORE = 1.0;
    private static final double WRONG_SCORE = -0.333333333333333333333333333333;

    private String examCodeResult;
    private String examMarkResult;
    private String[] results;

    public SaveExamTemplateController(String path) {
        System.load("/opt/homebrew/Cellar/opencv/4.10.0_12/share/java/opencv4/libopencv_java4100.dylib");

        results = new String[TOTAL_QUESTIONS];
        processImage(path);
    }

    public String getExamCodeResult() {
        return examCodeResult;
    }

    public String getExamMarkResult() {
        return examMarkResult;
    }
    
    public String[] getArrayResult() {
        return results;
    }

    private void processImage(String path) {
        //Imprimir imagen por pantalla
        /* HighGui.imshow("Imagen", image);
        HighGui.waitKey(0);
        HighGui.destroyAllWindows(); */

        Mat image = Imgcodecs.imread(path);
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
        Mat edges = new Mat();
        Imgproc.Canny(grayImage, edges, 200, 100);

        List<Rect> rectangles = getMainRectangles(image, edges);
        processExamCode(image, rectangles.get(1));
        calculateExamScore(image, rectangles.subList(2, 6));
    }

    private void processExamCode(Mat image, Rect rectangleTestCode) {
        Mat testCodeMat = new Mat(image, rectangleTestCode);
        Mat bigTestCodeRectangle = getBigRectangleFromTestCode(testCodeMat);
        examCodeResult = getNumbersFromExamCode(bigTestCodeRectangle);
    }

    private void calculateExamScore(Mat image, List<Rect> answerRectangles) {
        List<Mat> allSmallRects = new ArrayList<>();

        for (Rect rect : answerRectangles) {
            allSmallRects.addAll(getSmallRectangles(new Mat(image, rect)));
        }

        for (int i = 0; i < TOTAL_QUESTIONS; i++) {
            results[i] = getCorrectAnswer(allSmallRects.get(i));
        }
    }
}
