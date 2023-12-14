package A1.ThirdTask;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DataProcessing {
    public static void main(String[] args) {
        String loginsFile = "logins.csv";
        String postingsFile = "postings.csv";

        // Шаг 1: Чтение файла logins.csv
        Map<String, Boolean> loginMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(loginsFile))){
            String line;
            while ((line = reader.readLine()) != null) {
                // Обработка каждой строки в файле logins.csv
                String[] loginData = line.split("\\n");
                String login = loginData[0];
                String password = loginData[1];
                boolean isActive = Boolean.parseBoolean(loginData[2]);

                // Проверка, находится ли пользователь в списке активных пользователей
                boolean authorized = isActive && checkUserAuthorization(login, password);
                loginMap.put(login, authorized);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Шаг 2: Чтение файла postings.csv
        try (BufferedReader reader = new BufferedReader(new FileReader(postingsFile))){
            String line;
            while ((line = reader.readLine()) != null) {
                // Обработка каждой строки в файле postings.csv
                String[] postingData = line.split("\\n");
                String matDoc = postingData[0];
                String item = postingData[1];
                String docDate = postingData[2];
                double quantity = Double.parseDouble(postingData[3]);
                String unit = postingData[4];
                double amount = Double.parseDouble(postingData[5]);

                // Проверка, если User Name находится в списке авторизованных пользователей
                String userName = postingData[6];
                boolean authorizedSupply = loginMap.containsKey(userName) && loginMap.get(userName);

                // Дополнительные действия для авторизованной поставки
                if (authorizedSupply) {
                    // Выполнение нужных действий для каждой авторизованной поставки

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkUserAuthorization(String login, String password) {
        // Логика проверки авторизации пользователя
        // Вернуть true, если пользователь авторизован, иначе false
        // Например, можно проверять введенный логин и пароль с базой данных пользователей
        return true;
    }
}
