package examTemplateCorrector;

import static examTemplateCorrector.OrderRectanglesAndCircles.orderRectanglesHorizontal;
import static examTemplateCorrector.OrderRectanglesAndCircles.orderRectanglesVertical;
import static examTemplateCorrector.WorkWithCircles.getCorrectAnswer;
import static examTemplateCorrector.WorkWithCircles.getLetter;
import static examTemplateCorrector.WorkWithCircles.getNumbersFromDNI;
import static examTemplateCorrector.WorkWithCircles.getNumbersFromExamCode;
import static examTemplateCorrector.WorkWithRectangles.getBigRectangleFromTestCode;
import static examTemplateCorrector.WorkWithRectangles.getBiggestRectangles;
import static examTemplateCorrector.WorkWithRectangles.getSmallRectangles;
import static examTemplateCorrector.WorkWithRectangles.getSmallRectanglesFromDNI;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class MainController {
    
    private String dniOrNieResult;
    private String examCodeResult;
    private String examMarkResult;
    private String[] results;
    
    public String getDniOrNieResult() {
        return dniOrNieResult;
    }
    
    public String getExamCodeResult() {
        return examCodeResult;
    }
    
    public String getExamMarkResult() {
        return examMarkResult;
    }

    public void setDniOrNieResult(String dniOrNieResult) {
        this.dniOrNieResult = dniOrNieResult;
    }

    public void setExamCodeResult(String examCodeResult) {
        this.examCodeResult = examCodeResult;
    }

    public void setExamMarkResult(String examMarkResult) {
        this.examMarkResult = examMarkResult;
    }
    
    
    public MainController(String path) {
        results = new String[3];
        
        System.load("C:/opencv/build/java/x64/opencv_java4100.dll");
        
        processImage(path);
    }
    
    private void processImage(String path) {
        //Plantilla de resultados correctos
        String[] correctResults = {
            "A", "B", "C", "D", "D", "C", "B", "A", "A", "B",
            "B", "B", "B", "C", "C", "C", "D", "D", "D", "B",
            "C", "A", "A", "B", "B", "D", "D", "C", "C", "D",
            "D", "D", "A", "A", "B", "B", "C", "C", "D", "D"
        };

        //Plantilla con algunos fallos
        String[] correctResults2 = {
            "A", "B", "C", "D", "A", "B", "C", "D", "A", "B",
            "B", "B", "B", "C", "C", "C", "D", "D", "D", "A",
            "C", "A", "A", "B", "B", "B", "B", "C", "C", "A",
            "D", "D", "A", "A", "B", "B", "A", "A", "D", "A"
        };

        //Plantilla con muchos fallos
        String[] correctResults3 = {
            "A", "B", "C", "D", "B", "C", "D", "A", "A", "B",
            "C", "D", "A", "B", "C", "A", "B", "C", "A", "D",
            "C", "A", "A", "B", "B", "B", "B", "C", "C", "A",
            "D", "D", "A", "A", "B", "B", "A", "A", "D", "A"
        };

        //Aquí vamos a guardar los resultados
        String[] results = new String[40];

        //Creamos la imagen a partir de la url en formato Mat
        Mat image = Imgcodecs.imread(path);

        //Imprimir imagen por pantalla
        /* HighGui.imshow("Imagen", image);
        HighGui.waitKey(0);
        HighGui.destroyAllWindows(); */
        
        //Pasamos la imagen a escala de grises para que sea más facil de interpretar
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        //Establecemos el borde de la imagen 
        Mat edges = new Mat();
        Imgproc.Canny(grayImage, edges, 200, 100);

        //Buscamos los contornos de la imagen
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        //Hacemos una lista de los rectángulos que encontremos
        List<Rect> rectangles = new ArrayList<>();
        for (MatOfPoint contour : contours) {
            MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
            RotatedRect rotatedRect = Imgproc.minAreaRect(contour2f);
            Rect rect = rotatedRect.boundingRect();

            if (rect.height < (image.height() / 2)
                    && rect.width < (image.width() * 0.5)
                    && rect.width > (image.width() * 0.1)) {

                rectangles.add(rect);
            }
        }

        //Guardamos los 6 rectángulos más grandes
        rectangles = getBiggestRectangles(rectangles, 6);

        //Dibujamos los rectángulos detectados 
        for (Rect rect : rectangles) {
            Imgproc.rectangle(image, rect, new Scalar(0, 255, 0), 2);
        }

        //Ordenamos los rectángulos en el eje de la y
        rectangles = orderRectanglesVertical(rectangles);

        //Guardamos los rectángulos de arriba por un lado y los de abajo por otro
        List<Rect> rectanglesUp = new ArrayList<>();
        List<Rect> rectanglesDown = new ArrayList<>();

        rectanglesUp.add(rectangles.get(0));
        rectanglesUp.add(rectangles.get(1));
        rectanglesDown.add(rectangles.get(2));
        rectanglesDown.add(rectangles.get(3));
        rectanglesDown.add(rectangles.get(4));
        rectanglesDown.add(rectangles.get(5));

        //Ordenamos los rectángulo de izquierda a derecha
        rectanglesUp = orderRectanglesHorizontal(rectanglesUp);
        rectanglesDown = orderRectanglesHorizontal(rectanglesDown);

        //Creamos una imagen por cada rectángulo grande
        Mat rectangleDNI = new Mat(image, rectanglesUp.get(0));
        Mat rectangleTestCode = new Mat(image, rectanglesUp.get(1));
        Mat rectangleAnswers1 = new Mat(image, rectanglesDown.get(0));
        Mat rectangleAnswers2 = new Mat(image, rectanglesDown.get(1));
        Mat rectangleAnswers3 = new Mat(image, rectanglesDown.get(2));
        Mat rectangleAnswers4 = new Mat(image, rectanglesDown.get(3));

        //Obtenemos los tres subrectángulos dentro de rectangleDNI
        List<Mat> smallRectanglesFromRectangleDNI = getSmallRectanglesFromDNI(rectangleDNI);

        //Obtener que letra esta marcada (NIE y DNI)
        String nieLetter = getLetter(smallRectanglesFromRectangleDNI.get(0));
        String dniLetter = getLetter(smallRectanglesFromRectangleDNI.get(2));

        //Obtenemos los números marcados en el NIE/DNI
        String numbers = getNumbersFromDNI(smallRectanglesFromRectangleDNI.get(1));
 
        //Revisamos NIE/DNI letra y lo unimos al número
        if (nieLetter == "Empty" && numbers.length() == 8) {
            this.setDniOrNieResult(numbers + dniLetter);
            
        }
        if (numbers.length() == 7) {
            this.setDniOrNieResult(nieLetter + numbers + dniLetter);
        }

        //Obtenemos los dos rectángulos de test code
        rectangleTestCode = getBigRectangleFromTestCode(rectangleTestCode);

        //Obtenemos los números del código de examen
        String codeTest = getNumbersFromExamCode(rectangleTestCode);
        this.setExamCodeResult(codeTest);

        //Obtenemos todos los pequeños rectángulos que contiene las respuestas y los unimos en una misma lista
        List<Mat> allSmallRectangles = new ArrayList<>();
        allSmallRectangles.addAll(getSmallRectangles(rectangleAnswers1));
        allSmallRectangles.addAll(getSmallRectangles(rectangleAnswers2));
        allSmallRectangles.addAll(getSmallRectangles(rectangleAnswers3));
        allSmallRectangles.addAll(getSmallRectangles(rectangleAnswers4));

        //Guardamos todos los resultados en el Array de resultados con un ciclo for
        for (int i = 0; i < 40; i++) {
            results[i] = getCorrectAnswer(allSmallRectangles.get(i));
        }

        //Obtenemos la nota de nuestro examen
        double mark = getMark(results, correctResults);
        this.setExamMarkResult(String.valueOf(mark));
    }

    private static double getMark(String[] results, String[] correctResults) {
        double mark = 0;
        for (int i = 0; i < 40; i++) {
            if (results[i] != "Empty") {
                if (results[i] == correctResults[i]) {
                    mark += 1.0;
                } else {
                    mark -= 0.3333333333333333333;
                }
            }
        }

        mark = mark / 40 * 10;

        return mark;
    }
}