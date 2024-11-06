package examTemplateCorrector.logic;

import static examTemplateCorrector.logic.WorkWithCircles.*;
import static examTemplateCorrector.logic.WorkWithRectangles.*;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import utilities.MyException;

public class SaveExamTemplateController {

    private static final int TOTAL_QUESTIONS = 40;

    private String examCodeResult;
    private String examMarkResult;
    private String[] results;

    public SaveExamTemplateController(String path) throws MyException {
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

    private void processImage(String path) throws MyException {
        //Imprimir imagen por pantalla
        /* HighGui.imshow("Imagen", image);
        HighGui.waitKey(0);
        HighGui.destroyAllWindows(); */

        Mat image = Imgcodecs.imread(path);
        
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
            allSmallRects.addAll(getSmallRectangles(new Mat(image, rect)));
        }

        if (allSmallRects.size() != 40) {
            throw new MyException("Rectangles of answers not found, try to take another picture");
        }

        for (int i = 0; i < TOTAL_QUESTIONS; i++) {
            String answer = getCorrectAnswer(allSmallRects.get(i));
            if (answer == "Empty") throw new MyException("Some of the answers are empty, to save a template use a complete exam");
            results[i] = answer;
        }
    }
}
