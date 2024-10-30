package database;

import java.io.BufferedReader;
import java.io.FileReader;

public class UtilityCSV {
    static String ruta = "C:\\Users\\loco8\\workspace\\practicas-universae-202409\\exam-template-corrector\\src\\database\\data.csv";

    public static String[] getCorrectAnswersFromDataBase(int examCode) {
        String[] result = new String[40];

        try (BufferedReader brFile = new BufferedReader(new FileReader(ruta))) {
            String line;
            int currentLine = 1;

            while ((line = brFile.readLine()) != null) {
                if (currentLine == examCode) {                    
                    result = line.split(",");
                }
                
                currentLine++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
