package A1.ThirdTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostingsDatabase {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/A1Tests";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "postgres";
    public List<Posting> getPostingsByPeriod(String startDate, String endDate, boolean authorizedOnly) throws SQLException {
        List<Posting> postings = new ArrayList<>();

        String query = "SELECT * FROM postings WHERE docdate >= ? AND docdate <= ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, startDate);
        statement.setString(2, endDate);

        try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String title = resultSet.getString("title");
        String content = resultSet.getString("content");
        boolean authorized = resultSet.getBoolean("authorized");
        String date = resultSet.getString("docdate");

        // Create a new Posting object and add it to the list
        Posting posting = new Posting(id, title, content, authorized, date);
        postings.add(posting);
        }
        }
        }
        return postings;
        }

public static class Posting {
    private int id;
    private String title;
    private String content;
    private boolean authorized;
    private String date;


    public Posting(int id, String title, String content, boolean authorized, String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorized = authorized;
        this.date = date;
    }

    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public String getDate() {
        return date;
    }
}
}


