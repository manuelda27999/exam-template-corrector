package examTemplateCorrector.logic;

import database.UtilityCSV;
import static examTemplateCorrector.logic.WorkWithCircles.*;
import static examTemplateCorrector.logic.WorkWithRectangles.*;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import utilities.MyException;

public class CorrectExamController {

    private static final int TOTAL_QUESTIONS = 40;
    private static final double CORRECT_SCORE = 1.0;
    private static final double WRONG_SCORE = -0.333333333333333333333333333333;

    private String dniOrNieResult;
    private String examCodeResult;
    private String examMarkResult;
    private String[] results;

    public CorrectExamController(String path) throws MyException {
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

    private void processImage(String path) throws MyException {
        //Imprimir imagen por pantalla
        /* HighGui.imshow("Imagen", image);
        HighGui.waitKey(0);
        HighGui.destroyAllWindows(); */

        Mat image = Imgcodecs.imread(path);

        List<Rect> rectangles = getMainRectangles(image);
        if (rectangles.size() != 6) throw new MyException("Main rectangles(DNI/NIE rectangle, exam code rectangle, answers rectangles) not found, try to take another picture");
                
        processDNINumber(image, rectangles.get(0));
        processExamCode(image, rectangles.get(1));
        calculateExamScore(image, rectangles.subList(2, 6));
    }

    private void processDNINumber(Mat image, Rect rectangleDNI) throws MyException {
        List<Mat> smallRects = getSmallRectanglesFromDNI(new Mat(image, rectangleDNI));
        String nieLetter = getLetter(smallRects.get(0));
        String dniLetter = getLetter(smallRects.get(2));
        
        if (nieLetter == "Empty" && dniLetter == "Empty") throw new MyException("Letters of DNI and NIE not found, try to take another picture");
        
        String numbers = getNumbersFromDNI(smallRects.get(1));
        if (numbers.length() != 7 && numbers.length() != 8) throw new MyException("Numbers of DNI or NIE not found, try to take another picture");

        dniOrNieResult = (nieLetter.equals("Empty") && numbers.length() == 8)
                ? numbers + dniLetter
                : nieLetter + numbers + dniLetter;
    }

    private void processExamCode(Mat image, Rect rectangleTestCode) throws MyException {
            Mat testCodeMat = new Mat(image, rectangleTestCode);
            Mat bigTestCodeRectangle = getBigRectangleFromTestCode(testCodeMat);
            String result = getNumbersFromExamCode(bigTestCodeRectangle);
            
            if (result.length() != 3) throw new MyException("Numbers of exam code not found, try to take another picture");
            
            examCodeResult = getNumbersFromExamCode(bigTestCodeRectangle);
    }

    private void calculateExamScore(Mat image, List<Rect> answerRectangles) throws MyException {
        String[] correctResults = initializeCorrectResults();
        List<Mat> allSmallRects = new ArrayList<>();

        for (Rect rect : answerRectangles) {
            allSmallRects.addAll(getSmallRectangles(new Mat(image, rect)));
        }
        if (allSmallRects.size() != 40) throw new MyException("Rectangles of answers not found, try to take another picture");

        for (int i = 0; i < TOTAL_QUESTIONS; i++) {
            results[i] = getCorrectAnswer(allSmallRects.get(i));
        }

        examMarkResult = String.valueOf(getMark(results, correctResults));
    }

    private static double getMark(String[] results, String[] correctResults) throws MyException {
        double score = 0;
        for (int i = 0; i < TOTAL_QUESTIONS; i++) {
            if (results[i] != "Empty") {
                score += results[i].equals(correctResults[i]) ? CORRECT_SCORE : WRONG_SCORE;
            }
        }

        return (score / TOTAL_QUESTIONS) * 10;
    }

    private String[] initializeCorrectResults() throws MyException {

        String[] array = UtilityCSV.getCorrectAnswersFromDataBase(Integer.parseInt(examCodeResult));
        if (array == null || array.length != 40) throw new MyException("Correct results of this exam not found, try to save the correct results again");
        
        for (String letter : array) {
            if (letter == null) throw new MyException("Correct results of this exam not found, try to save the correct results again");
        }
        
        return array;
    }
}
