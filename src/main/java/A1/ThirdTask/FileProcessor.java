package A1.ThirdTask;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class FileProcessor {

        private static BufferedReader readCSVFile(String filePath) throws IOException {
            FileReader fileReader = new FileReader(filePath);
            return new BufferedReader(fileReader);
        }

        public static void addAuthorizedDeliveryField(String loginsFilePath, String postingsFilePath, String outputFilePath) throws IOException {
            BufferedReader loginsReader = readCSVFile(loginsFilePath);

            BufferedReader postingsReader = readCSVFile(postingsFilePath);

            FileWriter fileWriter = new FileWriter(outputFilePath);

            String loginsLine;
            while ((loginsLine = loginsReader.readLine()) != null) {
                String[] loginsFields = loginsLine.split(",");

                String postingsLine;
                while ((postingsLine = postingsReader.readLine()) != null) {
                    String[] postingsFields = postingsLine.split(",");
                    if (loginsFields[0].equals(postingsFields[0]) && loginsFields[2].equals("true")) {

                        fileWriter.append(postingsLine).append(",true\n");
                    } else {

                        fileWriter.append(postingsLine).append(",false\n");
                    }
                }
            }

            loginsReader.close();
            postingsReader.close();
            fileWriter.close();
        }

        public static void saveToPostgresSQL(String filePath) throws IOException, SQLException {

            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/A1Tests", "postgres", "postgres");

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO logins (application, appaccountname, isactive, jobtitle, department ) VALUES (?, ?, ?, ?, ?)");

            BufferedReader reader = readCSVFile(filePath);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                System.out.println(Arrays.toString(fields));
                preparedStatement.setString(1, fields[0]);
                preparedStatement.setString(2, fields[1]);
                preparedStatement.setString(3, fields[2]);
                preparedStatement.setString(4, fields[3]);
                preparedStatement.setString(5, fields[4]);

                preparedStatement.executeUpdate();
            }

            reader.close();
            preparedStatement.close();
            connection.close();
        }
    public static void saveToPostgresSQLPosting(String PostingPath) throws IOException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/A1Tests", "postgres", "postgres");

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO postings ( matdoc, item, docdate, postngdate, materialdescription, quantity, bun, amountlc, crcy, username ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        BufferedReader reader = readCSVFile(PostingPath);
        String line;
        while ((line = reader.readLine()) != null) {

            String[] fields = line.split(",");
            System.out.println(Arrays.toString(fields));

            preparedStatement.setString(1, fields[0]);
            preparedStatement.setString(2, fields[1]);
            preparedStatement.setString(3, fields[2]);
            preparedStatement.setString(4, fields[3]);
            preparedStatement.setString(5, fields[4]);
            preparedStatement.setString(6, fields[5]);
            preparedStatement.setString(7, fields[6]);
            preparedStatement.setString(8, fields[7]);
            preparedStatement.setString(9, fields[8]);
            preparedStatement.setString(10, fields[9]);

            preparedStatement.executeUpdate();
        }


        reader.close();
        preparedStatement.close();
        connection.close();
    }

        public static void main(String[] args) {
            try {

                String loginsFilePath = "C:\\Users\\NT\\OneDrive\\Desktop\\кулинар\\testovoeA1\\src\\main\\resources\\logins.csv";
                BufferedReader loginsReader = readCSVFile(loginsFilePath);

                String postingsFilePath = "C:\\Users\\NT\\OneDrive\\Desktop\\кулинар\\testovoeA1\\src\\main\\resources\\postings.csv";
                BufferedReader postingsReader = readCSVFile(postingsFilePath);

                String outputFilePath = "C:\\Users\\NT\\OneDrive\\Desktop\\кулинар\\testovoeA1\\src\\main\\resources\\output.csv";
                addAuthorizedDeliveryField(loginsFilePath, postingsFilePath, outputFilePath);

                saveToPostgresSQL(loginsFilePath);

                saveToPostgresSQLPosting(postingsFilePath);

                System.out.println("Data processing and saving completed successfully.");
            } catch (IOException e) {
                System.err.println("An error occurred while reading or writing the files: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("An error occurred while connecting to the database or executing the SQL statement: " + e.getMessage());
            }
        }

    }