package app.logic;

import static app.logic.WorkWithCircles.getCorrectAnswer;
import static app.logic.WorkWithCircles.getNumbersFromExamCode;
import static app.logic.WorkWithRectangles.getBigRectangleFromTestCode;
import static app.logic.WorkWithRectangles.getMainRectangles;
import static app.logic.WorkWithRectangles.getSheet;
import static app.logic.WorkWithRectangles.getSmallRectangles;
import app.utilities.MyException;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;



public class SaveExamTemplateController {

    private static final int TOTAL_QUESTIONS = 40;

    private String examCodeResult;
    private String examMarkResult;
    private String[] results;
    private Mat answersRectangle1;
    private Mat answersRectangle2;
    private Mat answersRectangle3;
    private Mat answersRectangle4;

    public SaveExamTemplateController(String pathImage, String pathLibrary) throws MyException {
        System.load(pathLibrary);
        
        results = new String[TOTAL_QUESTIONS];
        processImage(pathImage);
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

    public Mat getAnswersRectangle1() {
        return answersRectangle1;
    }

    public Mat getAnswersRectangle2() {
        return answersRectangle2;
    }

    public Mat getAnswersRectangle3() {
        return answersRectangle3;
    }

    public Mat getAnswersRectangle4() {
        return answersRectangle4;
    }

    private void processImage(String pathImage) throws MyException {
        //Imprimir imagen por pantalla
        /* HighGui.imshow("Imagen", image);
        HighGui.waitKey(0);
        HighGui.destroyAllWindows(); */

        Mat image = Imgcodecs.imread(pathImage);

        Mat sheet = getSheet(image);

        if (!sheet.empty()) {
            image = sheet;
        }

        List<Rect> rectangles = getMainRectangles(image);
        if (rectangles.size() != 6) {
            throw new MyException("Main rectangles(DNI/NIE rectangle, exam code rectangle, answers rectangles) not found, try to take another picture");
        }

        processExamCode(image, rectangles.get(1));
        calculateExamScore(image, rectangles.subList(2, 6));

        answersRectangle1 = new Mat(image, rectangles.get(2));
        answersRectangle2 = new Mat(image, rectangles.get(3));
        answersRectangle3 = new Mat(image, rectangles.get(4));
        answersRectangle4 = new Mat(image, rectangles.get(5));
    }

    private void processExamCode(Mat image, Rect rectangleTestCode) throws MyException {
        Mat testCodeMat = new Mat(image, rectangleTestCode);
        Mat bigTestCodeRectangle = getBigRectangleFromTestCode(testCodeMat);

        String result = getNumbersFromExamCode(bigTestCodeRectangle);
        if (result.length() != 3) {
            throw new MyException("Numbers of exam code not found, try to take another picture");
        }

        examCodeResult = getNumbersFromExamCode(bigTestCodeRectangle);
    }

    private void calculateExamScore(Mat image, List<Rect> answerRectangles) throws MyException {
        List<Mat> allSmallRects = new ArrayList<>();

        for (Rect rect : answerRectangles) {
            Object[] results = getSmallRectangles(new Mat(image, rect));
            List<Mat> smallRectanglesMat = (List<Mat>) results[1];
            allSmallRects.addAll(smallRectanglesMat);
        }

        if (allSmallRects.size() != 40) {
            throw new MyException("Rectangles of answers not found, try to take another picture");
        }

        for (int i = 0; i < TOTAL_QUESTIONS; i++) {
            String answer = getCorrectAnswer(allSmallRects.get(i));
            if (answer == "Empty") {
                throw new MyException("Some of the answers are empty, to save a template use a complete exam");
            }
            results[i] = answer;
        }
    }
}
