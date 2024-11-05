package examTemplateCorrector.logic;

import java.util.List;
import org.opencv.core.Rect;

public class OrderRectanglesAndCircles {

    public static List<double[]> orderCirclesVertical(List<double[]> circles) {
        for (int i = 0; i < (circles.size() - 1); i++) {
            if (circles.get(i)[1] > circles.get(i + 1)[1]) {
                double[] saveElement = circles.get(i);
                circles.set(i, circles.get(i + 1));
                circles.set(i + 1, saveElement);

                circles = orderCirclesVertical(circles);
            }
        }
        return circles;
    }

    public static List<double[]> orderCirclesHorizontalRespectingVertical(List<double[]> circles) {
        for (int i = 0; i < (circles.size() - 1); i++) {
            if (circles.get(i)[0] > circles.get(i + 1)[0]) {
                if ((circles.get(i)[1] - circles.get(i + 1)[1]) > -2
                        && (circles.get(i)[1] - circles.get(i + 1)[1]) < 2) {
                    double[] saveElement = circles.get(i);
                    circles.set(i, circles.get(i + 1));
                    circles.set(i + 1, saveElement);

                    circles = orderCirclesHorizontalRespectingVertical(circles);
                }
            }
        }
        return circles;
    }

    public static List<double[]> orderCirclesHorizontal(List<double[]> circles) {
        for (int i = 0; i < (circles.size() - 1); i++) {
            if (circles.get(i)[0] > circles.get(i + 1)[0]) {
                double[] saveElement = circles.get(i);
                circles.set(i, circles.get(i + 1));
                circles.set(i + 1, saveElement);

                circles = orderCirclesHorizontal(circles);
            }
        }
        return circles;
    }

    public static List<Rect> orderRectanglesVertical(List<Rect> rectangles) {
        for (int i = 0; i < (rectangles.size() - 1); i++) {
            if (rectangles.get(i).y > rectangles.get(i + 1).y) {
                Rect saveElement = rectangles.get(i);
                rectangles.set(i, rectangles.get(i + 1));
                rectangles.set(i + 1, saveElement);

                rectangles = orderRectanglesVertical(rectangles);
            }
        }
        return rectangles;
    }
    
    public static List<Rect> orderRectanglesHorizontalRespectingVertical(List<Rect> rectangles) {
        for (int i = 0; i < (rectangles.size() - 1); i++) {
            if (rectangles.get(i).x > rectangles.get(i + 1).x) {
                if ((rectangles.get(i).y - rectangles.get(i + 1).y) > -20
                        && (rectangles.get(i).y - rectangles.get(i + 1).y) < 20) {
                    Rect saveElement = rectangles.get(i);
                    rectangles.set(i, rectangles.get(i + 1));
                    rectangles.set(i + 1, saveElement);

                    rectangles = orderRectanglesHorizontalRespectingVertical(rectangles);
                }
            }
        }
        return rectangles;
    }

    public static List<Rect> orderRectanglesHorizontal(List<Rect> rectangles) {
        for (int i = 0; i < (rectangles.size() - 1); i++) {
            if (rectangles.get(i).x > rectangles.get(i + 1).x) {
                Rect saveElement = rectangles.get(i);
                rectangles.set(i, rectangles.get(i + 1));
                rectangles.set(i + 1, saveElement);

                rectangles = orderRectanglesHorizontal(rectangles);
            }
        }
        return rectangles;
    }

}
