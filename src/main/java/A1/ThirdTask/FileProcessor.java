package A1.ThirdTask;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * `FileProcessor` class provides methods to read CSV files from the local file system,
 * process the data, and save it to a PostgreSQL database.
 */

public class FileProcessor {
        /**
         * Reads a CSV file from the local file system.
         *
         * @param filePath The path of the CSV file to be read.
         * @return Returns a BufferedReader object containing the contents of the CSV file.
         * @throws IOException if an I/O error occurs while reading the file.
         */
        private static BufferedReader readCSVFile(String filePath) throws IOException {
            FileReader fileReader = new FileReader(filePath);
            return new BufferedReader(fileReader);
        }

        /**
         * Adds a boolean field "authorized_delivery" to the data from the postings.csv file,
         * indicating whether the "Username" from postings.csv is present in the "AppAccountName" list from logins.csv and IsActive.
         *
         * @param loginsFilePath    The path of the logins.csv file.
         * @param postingsFilePath  The path of the postings.csv file.
         * @param outputFilePath    The path of the output file to save the modified data.
         * @throws IOException if an I/O error occurs while reading or writing the files.
         */
        public static void addAuthorizedDeliveryField(String loginsFilePath, String postingsFilePath, String outputFilePath) throws IOException {
            // Read the logins.csv file
            BufferedReader loginsReader = readCSVFile(loginsFilePath);

            // Read the postings.csv file
            BufferedReader postingsReader = readCSVFile(postingsFilePath);

            // Create a FileWriter to write the modified data to the output file
            FileWriter fileWriter = new FileWriter(outputFilePath);

            // Read the logins.csv file line by line
            String loginsLine;
            while ((loginsLine = loginsReader.readLine()) != null) {
                // Split the line into fields using comma as the delimiter
                String[] loginsFields = loginsLine.split(",");

                // Read the postings.csv file line by line
                String postingsLine;
                while ((postingsLine = postingsReader.readLine()) != null) {
                    // Split the line into fields using comma as the delimiter
                    String[] postingsFields = postingsLine.split(",");

                    // Check if the "User Name" from postings.csv is present in the "AppAccountName" list from logins.csv and IsActive
                    if (loginsFields[0].equals(postingsFields[0]) && loginsFields[2].equals("true")) {
                        // Append the modified line to the output file
                        fileWriter.append(postingsLine + ",true\n");
                    } else {
                        // Append the original line to the output file
                        fileWriter.append(postingsLine + ",false\n");
                    }
                }
            }

            // Close the readers and writer
            loginsReader.close();
            postingsReader.close();
            fileWriter.close();
        }

        /**
         * Saves the data from a CSV file to a PostgreSQL database.
         *
         * @param filePath The path of the CSV file to be saved.
         * @throws IOException  if an I/O error occurs while reading the file.
         * @throws SQLException if a database access error occurs.
         */
        public static void saveToPostgresSQL(String filePath) throws IOException, SQLException {
            // Create a connection to the PostgresSQL database
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/A1Tests", "postgres", "postgres");

            // Create a prepared statement to insert data into the database
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO logins (application, appaccountname, isactive, jobtitle, department ) VALUES (?, ?, ?, ?, ?)");

            // Read the CSV file line by line
            BufferedReader reader = readCSVFile(filePath);
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into fields using comma as the delimiter
                String[] fields = line.split(",");

                // Set the values of the prepared statement parameters
                preparedStatement.setString(1, fields[0]);
                preparedStatement.setString(2, fields[1]);
                preparedStatement.setString(3, fields[2]);
                preparedStatement.setString(4, fields[3]);
                preparedStatement.setString(5, fields[4]);

                // Execute the prepared statement
                preparedStatement.executeUpdate();
            }

            // Close the reader, prepared statement, and connection
            reader.close();
            preparedStatement.close();
            connection.close();
        }

        /**
         * Main method to demonstrate the usage of the FileProcessor class.
         *
         * @param args The command line arguments.
         */
        public static void main(String[] args) {
            try {
                // Step 1: Read logins.csv file
                String loginsFilePath = "C:\\Users\\NT\\OneDrive\\Desktop\\кулинар\\testovoeA1\\src\\main\\resources\\logins.csv";
                BufferedReader loginsReader = readCSVFile(loginsFilePath);

                // Step 2: Read postings.csv file
                String postingsFilePath = "C:\\Users\\NT\\OneDrive\\Desktop\\кулинар\\testovoeA1\\src\\main\\resources\\postings.csv";
                BufferedReader postingsReader = readCSVFile(postingsFilePath);

                // Step 3: Add authorized_delivery field to postings.csv data
                String outputFilePath = "C:\\Users\\NT\\OneDrive\\Desktop\\кулинар\\testovoeA1\\src\\main\\resources\\postings.csv";
                addAuthorizedDeliveryField(loginsFilePath, postingsFilePath, outputFilePath);

                // Step 4: Save logins.csv data to PostgreSQL
                saveToPostgresSQL(loginsFilePath);

                // Step 5: Save postings.csv data (with additional field) to PostgreSQL
                saveToPostgresSQL(outputFilePath);

                // Step 6: Implement REST API to serve the data
                // ...

                // Print success message
                System.out.println("Data processing and saving completed successfully.");
            } catch (IOException e) {
                System.err.println("An error occurred while reading or writing the files: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("An error occurred while connecting to the database or executing the SQL statement: " + e.getMessage());
            }
        }
    }