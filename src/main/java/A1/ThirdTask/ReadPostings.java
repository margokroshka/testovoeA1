package A1.ThirdTask;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadPostings {
    String csvFilePath = "postings.csv";
    BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
    String line;

    public ReadPostings() throws FileNotFoundException {
    }
    public void readPost() throws IOException {
        boolean headerSkipped = false;
        while ((line = br.readLine()) != null) {
            if (!headerSkipped) {
                // Пропускаем заголовок
                headerSkipped = true;
                continue;
            }
            String[] data = line.split("\\n");
            String matDoc = data[0].trim();
            System.out.println("Mat. Doc: " + matDoc);
        }
    }
}
