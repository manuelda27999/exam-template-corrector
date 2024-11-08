package utilities;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class AllUtilities {

    public static void PrintImage(Mat rectangle) {
        Random random = new Random();

        String userHome = System.getProperty("user-home");

        String filePath = userHome + "/Downloads/" + random.nextInt(1000) + ".png";
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

    public static void SetImageLabel(JLabel label, String root) {
        ImageIcon image = new ImageIcon(root);
        Icon icon = new ImageIcon(image.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));
        label.setIcon(icon);
        label.repaint();
    }

    public static String CreateImage(Mat rectangle) {
        Random random = new Random();
        String userHome = System.getProperty("user.home");

        String filePath = userHome + File.separator + "Downloads" + File.separator + random.nextInt(1000) + ".png";
        Imgcodecs.imwrite(filePath, rectangle);
        File file = new File(filePath);

        return filePath;
    }
    
}
