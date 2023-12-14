package A1.ThirdTask;

import com.sun.jdi.connect.spi.Connection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String authorizedDelivery = "Yes";
        String startDate = "2022-01-01";
        String endDate = "2022-03-31";

        List<String[]> postingsData = getPostingsData(authorizedDelivery, startDate, endDate);

        for (String[] row : postingsData) {
            System.out.println(String.join(",", row));
        }
    }

    public static List<String[]> getPostingsData(String authorizedDelivery, String startDate, String endDate) {
        String filePath = "postings.csv";
        List<String[]> data = loadData(filePath);
        List<String[]> filteredData = filterByAuthorizedDelivery(data, authorizedDelivery);
        List<String[]> finalFilteredData = filterByPeriod(filteredData, startDate, endDate);
        return finalFilteredData;
    }

    public static List<String[]> loadData(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip header row
            while ((line = reader.readLine()) != null) {
                String[] row = line.split("\\n");
                data.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static List<String[]> filterByAuthorizedDelivery(List<String[]> data, String authorizedDelivery) {
        List<String[]> filteredData = new ArrayList<>();
        for (String[] row : data) {
            if (row[3].equals(authorizedDelivery)) {
                filteredData.add(row);
            }
        }
        return filteredData;
    }

    public static List<String[]> filterByPeriod(List<String[]> data, String startDate, String endDate) {
        List<String[]> filteredData = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);
            for (String[] row : data) {
                Date date = dateFormat.parse(row[1]);
                if (date.after(start) && date.before(end)) {
                    filteredData.add(row);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return filteredData;
    }
}