package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class UtilityCSV {
    static String path = "C:\\Users\\loco8\\workspace\\practicas-universae-202409\\exam-template-corrector\\src\\database\\data.csv";

    public static String[] getCorrectAnswersFromDataBase(int examCode) {
        String[] result = new String[40];

        try (BufferedReader brFile = new BufferedReader(new FileReader(path))) {
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
    
    public static void saveCorrectExamTemplate(String examCode, String[] correctAnswers) {
        int examCodeInt = Integer.parseInt(examCode);
        
        try (BufferedWriter brFile = new BufferedWriter(new FileWriter(path))) {
            String line;
            
            for (int i = 1; i < examCodeInt; i++) {
                
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
