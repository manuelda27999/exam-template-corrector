package api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MainCSV {

    public String[] getCorrectAnswersFromDataBase(int examCode, String path) {

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

    public void saveCorrectExamTemplate(String examCode, String[] correctAnswers, String path) {
        int examCodeInt = Integer.parseInt(examCode);
        String correctAnswerString = "";
        List<String> dataCSV = new ArrayList<>();

        for (int i = 0; i < correctAnswers.length; i++) {
            if (i == correctAnswers.length - 1) {
                correctAnswerString += correctAnswers[i];
            } else {
                correctAnswerString += correctAnswers[i] + ",";
            }
        }

        try (BufferedReader brFile = new BufferedReader(new FileReader(path))) {
            String line;
            int currentLine = 0;

            while (((line = brFile.readLine()) != null) || currentLine <= examCodeInt) {
                if (line == null) {
                    dataCSV.add("");
                    currentLine++;
                } else {
                    dataCSV.add(line);
                    currentLine++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        dataCSV.set(examCodeInt - 1, correctAnswerString);

        try (BufferedWriter bwFile = new BufferedWriter(new FileWriter(path))) {
            for (String line : dataCSV) {
                bwFile.write(line);
                bwFile.newLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
