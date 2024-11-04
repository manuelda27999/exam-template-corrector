
package utilities;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Utilities {
    public static void PrintImage(Mat rectangle) {
        Random random = new Random();
        String filePath = String.valueOf(random.nextInt(1000)) + ".png";
        Imgcodecs.imwrite(filePath, rectangle);
        
        try {
            File file = new File(filePath);
            if (file.exists()) {
                Runtime.getRuntime().exec("open " + file.getAbsolutePath());
            
            } else {
                System.out.println("No se pudo crear la imagen.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
