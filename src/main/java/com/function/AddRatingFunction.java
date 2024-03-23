package com.function;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class AddRatingFunction {

    @FunctionName("addRating")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            @BindingName("title") String title,
            @BindingName("opinion") String opinion,
            @BindingName("rating") int rating,
            @BindingName("datetime") String datetime,
            @BindingName("author") String author,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        if (title == null || opinion == null || datetime == null || author == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass all rating information as query parameters.").build();
        }

        if (rating < 1 || rating > 10) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Rating should be between 1 and 10.").build();
        }

        try {
            Rating ratingObj = new Rating(title, opinion, rating, datetime, author);
            insertRatingIntoDatabase(ratingObj);

            return request.createResponseBuilder(HttpStatus.OK).body("Rating added successfully.").build();
        } catch (SQLException e) {
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inserting rating into database: " + e.getMessage()).build();
        }
    }

    private void insertRatingIntoDatabase(Rating rating) throws SQLException {
        String jdbcUrl = "jdbc:sqlserver://{name}.database.windows.net:1433;database={db_name}";
        String username = "######";
        String password = "#######";

        // SQL statement to insert a new rating
        String sql = "INSERT INTO Ratings (Title, Opinion, Rating, DateTime, Author) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, rating.getTitle());
            pstmt.setString(2, rating.getOpinion());
            pstmt.setInt(3, rating.getRating());
            pstmt.setString(4, rating.getDateTime());
            pstmt.setString(5, rating.getAuthor());

            pstmt.executeUpdate();
        }
    }
}

