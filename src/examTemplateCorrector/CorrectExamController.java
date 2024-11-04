package examTemplateCorrector;

import database.UtilityCSV;
import static examTemplateCorrector.WorkWithCircles.*;
import static examTemplateCorrector.WorkWithRectangles.*;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class CorrectExamController {

    private static final int TOTAL_QUESTIONS = 40;
    private static final double CORRECT_SCORE = 1.0;
    private static final double WRONG_SCORE = -0.333333333333333333333333333333;

    private String dniOrNieResult;
    private String examCodeResult;
    private String examMarkResult;
    private String[] results;

    public CorrectExamController(String path) {
        System.load("/opt/homebrew/Cellar/opencv/4.10.0_12/share/java/opencv4/libopencv_java4100.dylib");

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
    
    public String[] getArrayResult() {
        return results;
    }

    private void processImage(String path) {
        //Imprimir imagen por pantalla
        /* HighGui.imshow("Imagen", image);
        HighGui.waitKey(0);
        HighGui.destroyAllWindows(); */

        Mat image = Imgcodecs.imread(path);

        List<Rect> rectangles = getMainRectangles(image);
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

        String[] array = UtilityCSV.getCorrectAnswersFromDataBase(Integer.parseInt(examCodeResult));

        return array;
    }
}
