package app.logic;

import api.MainCSV;
import static app.logic.WorkWithCircles.*;
import static app.logic.WorkWithRectangles.*;
import app.utilities.MyException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class CorrectExamController {
    //Con este código vamos a crear una clase con la que corregir las plantillas y a través de la cual podemos acceder a los resultados

    private static final int TOTAL_QUESTIONS = 40;
    private static final double CORRECT_SCORE = 1.0;
    private static final double WRONG_SCORE = -0.333333333333333333333333333333;

    private Mat mainImage;
    private String dniOrNieResult;
    private String examCodeResult;
    private String examMarkResult;
    private int correctAnswers;
    private int wrongAnswers;
    private int emptyAnswers;
    private String[] results;
    private Mat answersRectangle1;
    private Mat answersRectangle2;
    private Mat answersRectangle3;
    private Mat answersRectangle4;
    private String pathCSV;

    public CorrectExamController(String pathImage, String pathLibrary, String pathCSV) throws MyException {
        System.load(pathLibrary);
        
        this.pathCSV = pathCSV;
        results = new String[TOTAL_QUESTIONS];
        processImage(pathImage);

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

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public int getEmptyAnswers() {
        return emptyAnswers;
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
        System.out.println(pathImage);
        mainImage = Imgcodecs.imread(pathImage);
        
        if (mainImage.empty() || mainImage == null) {
            throw new MyException("No se puede cargar la imagen");
        }

        Mat sheet = getSheet(mainImage);

        if (!sheet.empty()) {
            mainImage = sheet;
        }

        List<Rect> rectangles = getMainRectangles(mainImage);
        if (rectangles.size() != 6) {
            throw new MyException("Main rectangles(DNI/NIE rectangle, exam code rectangle, answers rectangles) not found, try to take another picture");
        }

        processDNINumber(rectangles.get(0));
        processExamCode(rectangles.get(1));
        calculateExamScore(rectangles.subList(2, 6));

        answersRectangle1 = new Mat(mainImage, rectangles.get(2));
        answersRectangle2 = new Mat(mainImage, rectangles.get(3));
        answersRectangle3 = new Mat(mainImage, rectangles.get(4));
        answersRectangle4 = new Mat(mainImage, rectangles.get(5));
    }

    private void processDNINumber(Rect rectangleDNI) throws MyException {
        List<Mat> smallRects = getSmallRectanglesFromDNI(new Mat(mainImage, rectangleDNI));

        String nieLetter = getLetter(smallRects.get(0));
        String dniLetter = getLetter(smallRects.get(2));
        String numbers = getNumbersFromDNI(smallRects.get(1));

        if (!nieLetter.equals("Empty") && numbers.length() == 8 && numbers.charAt(0) == '0') {
            numbers = numbers.substring(1);
        }

        if (numbers.length() != 7 && numbers.length() != 8) {
            throw new MyException("Numbers of the DNI or NIE not found, try to take another picture");
        }
        if (nieLetter.equals("Empty") && dniLetter.equals("Empty")) {
            throw new MyException("Letters of DNI and NIE not found, try to take another picture");
        }
        if (numbers.length() == 8 && !nieLetter.equals("Empty")) {
            throw new MyException("The numbers of the NIE isn't correct, try to take another picture");
        }
        if (numbers.length() != 8 && nieLetter.equals("Empty")) {
            throw new MyException("Numbers of DNI not correct or not found, try to take another picture");
        }

        dniOrNieResult = (nieLetter.equals("Empty") && numbers.length() == 8)
                ? numbers + dniLetter
                : nieLetter + numbers + dniLetter;
    }

    private void processExamCode(Rect rectangleTestCode) throws MyException {
        Mat testCodeMat = new Mat(mainImage, rectangleTestCode);
        Mat bigTestCodeRectangle = getBigRectangleFromTestCode(testCodeMat);
        String result = getNumbersFromExamCode(bigTestCodeRectangle);

        if (result.length() != 3) {
            throw new MyException("Numbers of exam code not found, try to take another picture");
        }

        examCodeResult = getNumbersFromExamCode(bigTestCodeRectangle);
    }

    private void calculateExamScore(List<Rect> answerRectangles) throws MyException {
        String[] correctResults = initializeCorrectResults();
        List<Mat> allSmallRectanglesMat = new ArrayList<>();
        List<Rect> allSmallRectanglesRect = new ArrayList<>();

        for (Rect rect : answerRectangles) {
            Object[] results = getSmallRectangles(new Mat(mainImage, rect));
            List<Mat> smallRectanglesMat = (List<Mat>) results[1];
            List<Rect> smallRectanglesRect = (List<Rect>) results[0];
            allSmallRectanglesMat.addAll(smallRectanglesMat);
            allSmallRectanglesRect.addAll(smallRectanglesRect);
        }
        if (allSmallRectanglesMat.size() != 40) {
            throw new MyException("Rectangles of answers not found, try to take another picture");
        }

        for (int i = 0; i < TOTAL_QUESTIONS; i++) {
            results[i] = getCorrectAnswer(allSmallRectanglesMat.get(i));
        }

        printRectangles(results, correctResults, answerRectangles, allSmallRectanglesRect);

        examMarkResult = String.valueOf(getMark(results, correctResults));
    }

    private void printRectangles(String[] results, String[] correctResults, List<Rect> answerRectangles, List<Rect> allSmallRectanglesRect) {
        for (int i = 0; i < results.length; i++) {
            if (i < 10) {
                if (results[i].equals("Empty")) {
                    Imgproc.rectangle(new Mat(mainImage, answerRectangles.get(0)), allSmallRectanglesRect.get(i), new Scalar(0, 165, 255), 3);
                } else if (results[i].equals(correctResults[i])) {
                    Imgproc.rectangle(new Mat(mainImage, answerRectangles.get(0)), allSmallRectanglesRect.get(i), new Scalar(0, 255, 0), 3);
                } else {
                    Imgproc.rectangle(new Mat(mainImage, answerRectangles.get(0)), allSmallRectanglesRect.get(i), new Scalar(0, 0, 255), 3);
                }
            } else if (i >= 10 && i < 20) {
                if (results[i].equals("Empty")) {
                    Imgproc.rectangle(new Mat(mainImage, answerRectangles.get(1)), allSmallRectanglesRect.get(i), new Scalar(0, 165, 255), 3);
                } else if (results[i].equals(correctResults[i])) {
                    Imgproc.rectangle(new Mat(mainImage, answerRectangles.get(1)), allSmallRectanglesRect.get(i), new Scalar(0, 255, 0), 3);
                } else {
                    Imgproc.rectangle(new Mat(mainImage, answerRectangles.get(1)), allSmallRectanglesRect.get(i), new Scalar(0, 0, 255), 3);
                }
            } else if (i >= 20 && i < 30) {
                if (results[i].equals("Empty")) {
                    Imgproc.rectangle(new Mat(mainImage, answerRectangles.get(2)), allSmallRectanglesRect.get(i), new Scalar(0, 165, 255), 3);
                } else if (results[i].equals(correctResults[i])) {
                    Imgproc.rectangle(new Mat(mainImage, answerRectangles.get(2)), allSmallRectanglesRect.get(i), new Scalar(0, 255, 0), 3);
                } else {
                    Imgproc.rectangle(new Mat(mainImage, answerRectangles.get(2)), allSmallRectanglesRect.get(i), new Scalar(0, 0, 255), 3);
                }
            } else if (i >= 30) {
                if (results[i].equals("Empty")) {
                    Imgproc.rectangle(new Mat(mainImage, answerRectangles.get(3)), allSmallRectanglesRect.get(i), new Scalar(0, 165, 255), 3);
                } else if (results[i].equals(correctResults[i])) {
                    Imgproc.rectangle(new Mat(mainImage, answerRectangles.get(3)), allSmallRectanglesRect.get(i), new Scalar(0, 255, 0), 3);
                } else {
                    Imgproc.rectangle(new Mat(mainImage, answerRectangles.get(3)), allSmallRectanglesRect.get(i), new Scalar(0, 0, 255), 3);
                }
            }
        }
    }

    private double getMark(String[] results, String[] correctResults) throws MyException {
        double score = 0;
        for (int i = 0; i < TOTAL_QUESTIONS; i++) {
            if (!"Empty".equals(results[i])) {
                if (results[i].equals(correctResults[i])) {
                    score += CORRECT_SCORE;
                    correctAnswers++;
                } else {
                    score += WRONG_SCORE;
                    wrongAnswers++;
                }
            } else {
                emptyAnswers++;
            }
        }

        double mark = (score / TOTAL_QUESTIONS) * 10;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        String markResult = df.format(mark);
        markResult = markResult.replace(",", ".");
        mark = Double.parseDouble(markResult);

        return mark;
    }

    private String[] initializeCorrectResults() throws MyException {

        MainCSV mainCSV = new MainCSV();
        
        String[] array = mainCSV.getCorrectAnswersFromDataBase(Integer.parseInt(examCodeResult), pathCSV);
        if (array == null || array.length != 40) {
            throw new MyException("Correct results of this exam not found, try to save the correct results again");
        }

        for (String letter : array) {
            if (letter == null) {
                throw new MyException("Correct results of this exam not found, try to save the correct results again");
            }
        }

        return array;
    }
}
