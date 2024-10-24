package examTemplateCorrector;

import static examTemplateCorrector.OrderRectanglesAndCircles.orderRectanglesHorizontal;
import static examTemplateCorrector.OrderRectanglesAndCircles.orderRectanglesVertical;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class WorkWithRectangles {

    public static List<Rect> getBiggestRectangles(List<Rect> allRectangles, int amount) {
        List<Rect> result = new ArrayList<>();

        //Obtengo el area de todos los rectángulos
        for (Rect newRectangle : allRectangles) {
            int areaNewRectangle = newRectangle.height * newRectangle.width;

            //Si la lista de resultados tiene 6 elementos, busco el más pequeño y lo comparo con el candidato
            if (result.size() == amount) {
                int indexOfSmallestRectangle = 0;
                int smallestAreaResultRectangle = Integer.MAX_VALUE;

                //Busco el rectángulo más pequeño
                for (Rect resultRectangle : result) {
                    if (smallestAreaResultRectangle > resultRectangle.height * resultRectangle.width) {
                        smallestAreaResultRectangle = resultRectangle.height * resultRectangle.width;
                        indexOfSmallestRectangle = result.indexOf(resultRectangle);
                    }
                }

                //Comparo el rectángulo más pequeño con el candidato
                if (smallestAreaResultRectangle < areaNewRectangle) {
                    result.remove(indexOfSmallestRectangle);
                    result.add(newRectangle);
                }
            } else {
                result.add(newRectangle);
            }
        }

        return result;
    }

    public static List<Mat> getSmallRectanglesFromDNI(Mat rectangle) {
        List<Mat> result = new ArrayList<>();

        Mat grayImage = new Mat();
        Imgproc.cvtColor(rectangle, grayImage, Imgproc.COLOR_BGR2GRAY);

        Mat edges = new Mat();
        Imgproc.Canny(grayImage, edges, 200, 100);

        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(edges, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

        List<Rect> rectangles = new ArrayList<>();
        for (MatOfPoint contour : contours) {
            MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
            RotatedRect rotatedRect = Imgproc.minAreaRect(contour2f);
            Rect rect = rotatedRect.boundingRect();

            if (rect.height > rect.width) {
                rectangles.add(rect);
            }
        }

        rectangles = getBiggestRectangles(rectangles, 3);

        rectangles = orderRectanglesHorizontal(rectangles);

        for (Rect rect : rectangles) {
            Imgproc.rectangle(rectangle, rect, new Scalar(255, 0, 0), 2);
            Mat smallRectangle = new Mat(rectangle, rect);
            result.add(smallRectangle);
        }

        return result;
    }

    public static List<Mat> getSmallRectangles(Mat rectangle) {
        Set<Rect> resultRectanglesSet = new HashSet<>();
        List<Mat> resultsMats = new ArrayList<>();

        Mat grayRectangle = new Mat();
        Imgproc.cvtColor(rectangle, grayRectangle, Imgproc.COLOR_BGR2GRAY);

        Mat edgesRectangle = new Mat();
        Imgproc.Canny(grayRectangle, edgesRectangle, 200, 100);

        List<MatOfPoint> contoursRectangle = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(edgesRectangle, contoursRectangle, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

        for (MatOfPoint contour : contoursRectangle) {
            MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
            RotatedRect rotatedRect = Imgproc.minAreaRect(contour2f);
            Rect rect = rotatedRect.boundingRect();

            //Filtramos los valores que cumplen con las condiciones adecuadas y añadimos solo una vez los valores una vez, obviando los repetidos
            Boolean exist = false;
            if (rect.height < rect.width) {
                for (Rect rectOfResult : resultRectanglesSet) {
                    if (rectOfResult.y == rect.y || rectOfResult.y == (rect.y + 1) || rectOfResult.y == (rect.y - 1)) {
                        exist = true;
                    }
                }
                if (exist) {
                    exist = false;
                } else {
                    resultRectanglesSet.add(rect);
                }
            }
        }

        //Seleccionamos solo los 10 más grandes
        List<Rect> resultRectanglesList = new ArrayList<>(resultRectanglesSet);
        resultRectanglesList = getBiggestRectangles(resultRectanglesList, 10);

        //Pinto los rectángulos en la imagen proporcionada
        for (Rect rect : resultRectanglesList) {
            Imgproc.rectangle(rectangle, rect, new Scalar(0, 255, 0), 2);
        }

        //Ordenamos los rectángulos en función del eje de la Y   
        resultRectanglesList = orderRectanglesVertical(resultRectanglesList);

        //Creamos a partir de cada rectángulo un Mat que vamos a meter en resultsMat
        for (Rect rect : resultRectanglesList) {
            Mat smallImage = new Mat(rectangle, rect);
            resultsMats.add(smallImage);
        }

        return resultsMats;
    }

}
