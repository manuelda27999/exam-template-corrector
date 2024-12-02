package app.logic;

import static app.logic.OrderRectanglesAndCircles.orderCirclesHorizontal;
import static app.logic.OrderRectanglesAndCircles.orderCirclesHorizontalRespectingVertical;
import static app.logic.OrderRectanglesAndCircles.orderCirclesVertical;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class WorkWithCircles {
    //En esta clase está toda la lógica que trabaja con círculos, es decir identificación del DNI, del códig y corrección de los rectángulos con 4 opciones

    //Obtiene la letra del DNI o del NIE
    public static String getLetter(Mat rectangle) {
        String result = "Empty";

        Mat grayRectangle = new Mat();
        Imgproc.cvtColor(rectangle, grayRectangle, Imgproc.COLOR_BGR2GRAY);

        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(grayRectangle, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        Rect boundingRect = null;
        if (!contours.isEmpty()) {
            boundingRect = Imgproc.boundingRect(contours.get(0));
        }

        int minDist = rectangle.width() / 12;
        int minRadius = rectangle.width() / 11;
        int maxRadius = rectangle.width() / 8;

        Mat circles = new Mat();
        Imgproc.HoughCircles(grayRectangle, circles, Imgproc.CV_HOUGH_GRADIENT, 1, minDist, 100, 20, minRadius, maxRadius);

        List<double[]> circlesList = new ArrayList<>();

        for (int i = 0; i < circles.cols(); i++) {
            double[] circle = circles.get(0, i);
            Point center = new Point(circle[0], circle[1]); // (x, y)
            int radius = (int) Math.round(circle[2]); // radio

            circlesList.add(circle);

            Imgproc.circle(rectangle, center, 3, new Scalar(255, 0, 0), -1);
            Imgproc.circle(rectangle, center, radius, new Scalar(0, 255, 0), 2);
        }
        
        //PrintImage(rectangle); 
        
        circlesList = orderCirclesVertical(circlesList);
        circlesList = orderCirclesHorizontalRespectingVertical(circlesList);

        String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        for (double[] circle : circlesList) {
            Point center = new Point(circle[0] + boundingRect.x, circle[1] + boundingRect.y);
            int radius = (int) circle[2];

            Rect regionCircle = new Rect((int) (center.x - radius), (int) (center.y - radius), radius * 2, radius * 2);
            Mat circleSeparate = new Mat(grayRectangle, regionCircle);
                        
            int darkPixels = countBlackPixels(circleSeparate, 85);
            int totalPixels = circleSeparate.rows() * circleSeparate.cols();

            if (darkPixels > totalPixels * 0.5) {
                int index = circlesList.indexOf(circle);
                result = letters[index];
            }
        }

        return result;
    }

    //Obtiene los números del DNI o del NIE
    public static String getNumbersFromDNI(Mat rectangle) {
        String result = "";

        Mat grayRectangle = new Mat();
        Imgproc.cvtColor(rectangle, grayRectangle, Imgproc.COLOR_BGR2GRAY);

        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(grayRectangle, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        Rect boundingRect = null;
        if (!contours.isEmpty()) {
            boundingRect = Imgproc.boundingRect(contours.get(0));
        }

        int minDist = rectangle.width() / 16;
        int minRadius = rectangle.width() / 24;
        int maxRadius = rectangle.width() / 16;

        Mat circles = new Mat();
        Imgproc.HoughCircles(grayRectangle, circles, Imgproc.CV_HOUGH_GRADIENT, 1, minDist, 100, 20, minRadius, maxRadius);

        List<double[]> circlesList = new ArrayList<>();

        for (int i = 0; i < circles.cols(); i++) {
            double[] circle = circles.get(0, i);
            Point center = new Point(circle[0], circle[1]); // (x, y)
            int radius = (int) Math.round(circle[2]); // radio

            circlesList.add(circle);

            Imgproc.circle(rectangle, center, 3, new Scalar(255, 0, 0), -1);
            Imgproc.circle(rectangle, center, radius, new Scalar(0, 255, 0), 2);
        }
        
        circlesList = orderCirclesHorizontal(circlesList);

        List<List<double[]>> allColumns = new ArrayList<>();
        List<double[]> circlesColumn = new ArrayList<double[]>();

        int amount = 0;
        for (int i = 0; i < circlesList.size(); i++) {
            double[] circle = circlesList.get(i);

            if (amount < 10) {
                circlesColumn.add(circle);
                amount++;
            } else {
                circlesColumn = orderCirclesVertical(circlesColumn);
                allColumns.add(circlesColumn);

                circlesColumn = new ArrayList<>();
                circlesColumn.add(circle);
                amount = 1;
            }

            if (i == circlesList.size() - 1) {
                circlesColumn = orderCirclesVertical(circlesColumn);
                allColumns.add(circlesColumn);
            }
        }

        String[] numbersArray = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

        for (List<double[]> column : allColumns) {
            for (int i = 0; i < 10; i++) {
                double[] circle = column.get(i);

                Point center = new Point(circle[0] + boundingRect.x, circle[1] + boundingRect.y);
                int radius = (int) circle[2];

                Rect regionOfCircle = new Rect((int) (center.x - radius), (int) (center.y - radius), radius * 2, radius * 2);
                Mat circleSeparate = new Mat(grayRectangle, regionOfCircle);

                int darkPixels = countBlackPixels(circleSeparate, 85);
                int totalPixels = circleSeparate.rows() * circleSeparate.cols();

                if (darkPixels > totalPixels * 0.5) {
                    int index = column.indexOf(circle);
                    result = result.concat(numbersArray[index]);
                }
            }
        }

        return result;
    }

    //Obtiene el código de exámen
    public static String getNumbersFromExamCode(Mat rectangle) {
        String result = "";

        Mat grayRectangle = new Mat();
        Imgproc.cvtColor(rectangle, grayRectangle, Imgproc.COLOR_BGR2GRAY);

        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(grayRectangle, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        Rect boundingRect = null;
        if (!contours.isEmpty()) {
            boundingRect = Imgproc.boundingRect(contours.get(0));
        }

        int minRadius = rectangle.width() / 11;
        int maxRadius = rectangle.width() / 8;
        int minDist = rectangle.width() / 7;

        Mat circles = new Mat();
        Imgproc.HoughCircles(grayRectangle, circles, Imgproc.CV_HOUGH_GRADIENT, 1, minDist, 100, 20, minRadius, maxRadius);

        List<double[]> circlesList = new ArrayList<>();

        for (int i = 0; i < circles.cols(); i++) {
            double[] circle = circles.get(0, i);
            Point center = new Point(circle[0], circle[1]); // (x, y)
            int radius = (int) Math.round(circle[2]); // radio

            circlesList.add(circle);

            Imgproc.circle(rectangle, center, 3, new Scalar(255, 0, 0), -1);
            Imgproc.circle(rectangle, center, radius, new Scalar(0, 255, 0), 2);
        }

        circlesList = orderCirclesHorizontal(circlesList);

        List<List<double[]>> allColumns = new ArrayList<>();
        List<double[]> circlesColumn = new ArrayList<double[]>();

        int amount = 0;
        for (int i = 0; i < circlesList.size(); i++) {
            double[] circle = circlesList.get(i);

            if (amount < 10) {
                circlesColumn.add(circle);
                amount++;
            } else {
                circlesColumn = orderCirclesVertical(circlesColumn);
                allColumns.add(circlesColumn);

                circlesColumn = new ArrayList<>();
                circlesColumn.add(circle);
                amount = 1;
            }

            if (i == circlesList.size() - 1) {
                circlesColumn = orderCirclesVertical(circlesColumn);
                allColumns.add(circlesColumn);
            }
        }

        String[] numbersArray = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

        for (List<double[]> column : allColumns) {
            for (int i = 0; i < 10; i++) {
                double[] circle = column.get(i);

                Point center = new Point(circle[0] + boundingRect.x, circle[1] + boundingRect.y);
                int radius = (int) circle[2];

                Rect regionOfCircle = new Rect((int) (center.x - radius), (int) (center.y - radius), radius * 2, radius * 2);
                Mat circleSeparate = new Mat(grayRectangle, regionOfCircle);

                int darkPixels = countBlackPixels(circleSeparate, 95);
                int totalPixels = circleSeparate.rows() * circleSeparate.cols();

                if (darkPixels > totalPixels * 0.4) {
                    int index = column.indexOf(circle);
                    result = result.concat(numbersArray[index]);
                }
            }
        }

        return result;
    }

    //Obtiene la respuesta correcta en los rectángulos pequeños
    public static String getCorrectAnswer(Mat rectangle) {
        String result = "Empty";

        //Pasar la imagen a escala de grises para utilizarla luego
        Mat grayRectangle = new Mat();
        Imgproc.cvtColor(rectangle, grayRectangle, Imgproc.COLOR_BGR2GRAY);

        //Buscamos los contornos del rectángulo
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(grayRectangle, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        Rect boundingRect = null;
        if (!contours.isEmpty()) {
            boundingRect = Imgproc.boundingRect(contours.get(0));
        }

        //Establecer dimensiones del radio mínimo y máximo y de la distancia mínima a partir de las dimensiones del rectángulo
        int minRadius = rectangle.width() / 12;
        int maxRadius = rectangle.width() / 9;
        int minDist = rectangle.width() / 6;

        //Detectar los círculos en el área recortada
        Mat circles = new Mat();
        Imgproc.HoughCircles(grayRectangle, circles, Imgproc.CV_HOUGH_GRADIENT, 1, minDist, 80, 15, minRadius, maxRadius);

        //Creamos una lista donde almacenar los círculos que vamos a ordenar
        List<double[]> circlesList = new ArrayList<>();

        //Almacenamos los valores de la "x", "y" y radio en el array de double
        for (int i = 0; i < circles.cols(); i++) {
            double[] circle = circles.get(0, i);
            Point center = new Point(circle[0] + boundingRect.x, circle[1] + boundingRect.y);
            int radius = (int) circle[2];

            circlesList.add(circle);

            //Imgproc.circle(rectangle, center, radius, new Scalar(0, 255, 0), 2);  // Color verde y grosor 2
        }
        
        //Ordenamos los círculos en función de su posición en el eje de la x
        circlesList = orderCirclesHorizontal(circlesList);

        //Volvemos a asignar al Mat de circles los círculos pero ordenados
        circles = new Mat(1, circlesList.size(), CvType.CV_32FC3);
        for (int i = 0; i < circlesList.size(); i++) {
            circles.put(0, i, circlesList.get(i));
        }

        //Crear un array para guardar el valor de los círculos
        boolean[] markedCircles = new boolean[4];

        boolean findMarkedCircle = false;
        //Analizar los círculos detectados
        for (int i = 0; i < circles.cols(); i++) {
            double[] circle = circles.get(0, i);
            Point center = new Point(circle[0] + boundingRect.x, circle[1] + boundingRect.y);
            int radius = (int) circle[2];

            //Extraer la región del círculo
            Rect regionOfCircle = new Rect((int) (center.x - radius), (int) (center.y - radius), radius * 2, radius * 2);
            Mat circleSeparate = new Mat(grayRectangle, regionOfCircle);

            //Contar los píxeles en negro
            int darkPixels = countBlackPixels(circleSeparate, 85);
            int totalPixels = circleSeparate.rows() * circleSeparate.cols();
            
            if (darkPixels > totalPixels * 0.4) {
                if (findMarkedCircle) {
                    return "Empty";
                }
                
                markedCircles[i] = true;
                findMarkedCircle= true;
            } else {
                markedCircles[i] = false;
            }
        }

        //Vamos a ver que elemento del array es verdadero
        if (markedCircles[0]) {
            result = "A";
        }
        if (markedCircles[1]) {
            result = "B";
        }
        if (markedCircles[2]) {
            result = "C";
        }
        if (markedCircles[3]) {
            result = "D";
        }
        
        return result;
    }

    //Cuenta los píxeles oscuros dentro del círculos
    public static int countBlackPixels(Mat area, int darkThreshold) {
        int blackPixelCount = 0;
        for (int y = 0; y < area.rows(); y++) {
            for (int x = 0; x < area.cols(); x++) {
                double[] pixel = area.get(y, x);
                if (pixel[0] < darkThreshold) { // Si el valor es cercano a 0, lo consideramos negro
                    blackPixelCount++;
                }
            }
        }
        return blackPixelCount;
    }
}
