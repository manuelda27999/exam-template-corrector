package examTemplateCorrector;

import static examTemplateCorrector.OrderRectanglesAndCircles.*;
import static examTemplateCorrector.WorkWithCircles.*;
import static examTemplateCorrector.WorkWithRectangles.*;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class MainController {

    private static final int TOTAL_QUESTIONS = 40;
    private static final double CORRECT_SCORE = 1.0;
    private static final double WRONG_SCORE = -0.333333333333333333333333333333;

    private String dniOrNieResult;
    private String examCodeResult;
    private String examMarkResult;
    private String[] results;

    public MainController(String path) {
        System.load("C:/opencv/build/java/x64/opencv_java4100.dll");
        results = new String[TOTAL_QUESTIONS];
        processImage(path);
    }

    public String getDniOrNieResult() {
        return dniOrNieResult;
    }

    public String getExamCodeResult() {
        return examCodeResult;
    }

    public String getExamMarkResult() {
        return examMarkResult;
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
        processDNINumber(image, rectangles.get(0));
        processExamCode(image, rectangles.get(1));
        calculateExamScore(image, rectangles.subList(2, 6));
    }
    
    private void processDNINumber(Mat image, Rect rectangleDNI) {
        List<Mat> smallRects = getSmallRectanglesFromDNI(new Mat(image, rectangleDNI));
        String nieLetter = getLetter(smallRects.get(0));
        String dniLetter = getLetter(smallRects.get(2));
        String numbers = getNumbersFromDNI(smallRects.get(1));
        
        dniOrNieResult = (nieLetter.equals("Empty") && numbers.length() == 8) 
                            ? numbers + dniLetter 
                            : nieLetter + numbers + dniLetter;
    }
    
    private void processExamCode(Mat image, Rect rectangleTestCode) {
        Mat testCodeMat = new Mat(image, rectangleTestCode);
        Mat bigTestCodeRectangle = getBigRectangleFromTestCode(testCodeMat);
        examCodeResult = getNumbersFromExamCode(bigTestCodeRectangle);
    }

    private void calculateExamScore(Mat image, List<Rect> answerRectangles) {
        String[] correctResults = initializeCorrectResults();
        List<Mat> allSmallRects = new ArrayList<>();
        
        for (Rect rect : answerRectangles) {
            allSmallRects.addAll(getSmallRectangles(new Mat(image, rect)));
        }

        for (int i = 0; i < TOTAL_QUESTIONS; i++) {
            results[i] = getCorrectAnswer(allSmallRects.get(i));
        }

        examMarkResult = String.valueOf(getMark(results, correctResults));
    }

    private static double getMark(String[] results, String[] correctResults) {
        double score = 0;
        for (int i = 0; i < TOTAL_QUESTIONS; i++) {
            if (results[i] != "Empty") {
                score += results[i].equals(correctResults[i]) ? CORRECT_SCORE : WRONG_SCORE;
            }
        }

        return (score / TOTAL_QUESTIONS) * 10;
    }
    
    private String[] initializeCorrectResults() {
        return new String[] {
            "A", "B", "C", "D", "D", "C", "B", "A", "A", "B",
            "B", "B", "B", "C", "C", "C", "D", "D", "D", "B",
            "C", "A", "A", "B", "B", "D", "D", "C", "C", "D",
            "D", "D", "A", "A", "B", "B", "C", "C", "D", "D"
        };
    }
}
