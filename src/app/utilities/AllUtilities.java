package app.utilities;

import api.MainCSV;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

public class AllUtilities {

    //Pinta la imagen por pantalla, útil para ir controlando cual es el aspecto de las matrices durante la ejecución
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

    //Establece una imagen en un label
    public static void SetImageLabel(JLabel label, Image image) {
        ImageIcon imageIcon = new ImageIcon(image);
        Icon icon = new ImageIcon(imageIcon.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));
        label.setIcon(icon);
        label.repaint();
    }

    //Establece una imagen en un label con un mat
    public static void SetMatInLabel(JLabel jlabel, Mat mat) {
        try {
            MatOfByte buffer = new MatOfByte();
            Imgcodecs.imencode(".jpg", mat, buffer);

            ByteArrayInputStream bis = new ByteArrayInputStream(buffer.toArray());
            Image imagen = ImageIO.read(bis);

            SetImageLabel(jlabel, imagen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Busca el CSV para trabajar con el, sino lo encuentra lo crea
    public static String SearchCSV(String path) {
        String workPath = path;

        File csv = new File(workPath);

        if (csv.exists() && csv.isFile()) {
            System.out.println("CSV encontrado");
        } else {
            System.out.println("CSV no encontrado");
            try {
                String currentDirectory = new File(MainCSV.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();

                File newcsv = new File(currentDirectory + "/data.csv");
                workPath = newcsv.getAbsolutePath();

                if (newcsv.createNewFile()) {
                    System.out.println("He creado el CSV");
                } else {
                    System.out.println("Ya existe el CSV");
                };
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return workPath;
    }
}
