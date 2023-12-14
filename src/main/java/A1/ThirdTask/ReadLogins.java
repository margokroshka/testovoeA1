package A1.ThirdTask;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadLogins {
    String csvFilePath = "logins.csv";

     BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
     String line;

    public ReadLogins() throws FileNotFoundException {
    }

    public  void readL() throws IOException {
        while ((line = br.readLine()) != null) {
            String[] data = line.split("\\n");
            String application = data[0].trim();
            String appAccountName = data[1].trim();
            boolean isActive = Boolean.parseBoolean(data[2].trim());
            String jobTitle = data[3].trim();
            String department = data[4].trim();

            // Ваш код для обработки полученных данных

            System.out.println("Application: " + application);
            System.out.println("AppAccountName: " + appAccountName);
            System.out.println("IsActive: " + isActive);
            System.out.println("JobTitle: " + jobTitle);
            System.out.println("Department: " + department);
            System.out.println();
        }
    }
}
