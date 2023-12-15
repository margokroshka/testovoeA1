package A1.ThirdTask;

import java.sql.SQLException;
import java.util.List;

public class PostingsDatabaseExample {

    public static void main(String[] args) {
        // Example: Retrieve authorized postings for the month of January 2022
        String startDate = "2022-01-01";
        String endDate = "2022-01-31";
        boolean authorizedOnly = true;

        PostingsDatabase postingsDatabase = new PostingsDatabase();
        try {
            List<PostingsDatabase.Posting> postings = postingsDatabase.getPostingsByPeriod(startDate, endDate, authorizedOnly);

            // Print the retrieved postings
            for (PostingsDatabase.Posting posting : postings) {
                System.out.println("ID: " + posting.getId());
                System.out.println("Title: " + posting.getTitle());
                System.out.println("Content: " + posting.getContent());
                System.out.println("Authorized: " + posting.isAuthorized());
                System.out.println("Date: " + posting.getDate());
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
