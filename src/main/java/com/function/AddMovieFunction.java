package com.function;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class AddMovieFunction {

    @FunctionName("addMovie")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            @BindingName("title") String title,
            @BindingName("ReleaseYear") int ReleaseYear,
            @BindingName("genre") String genre,
            @BindingName("description") String description,
            @BindingName("director") String director,
            @BindingName("actors") String actors,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        if (title == null || genre == null || description == null || director == null || actors == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass all movie information as query parameters.").build();
        }

        try {
            Movie movie = new Movie(title, ReleaseYear, genre, description, director, actors);
            insertMovieIntoDatabase(movie);

            return request.createResponseBuilder(HttpStatus.OK).body("Movie added successfully.").build();
        } catch (SQLException e) {
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inserting movie into database: " + e.getMessage()).build();
        }
    }

    private void insertMovieIntoDatabase(Movie movie) throws SQLException {
        String jdbcUrl = "jdbc:sqlserver://movie-serverless.database.windows.net:1433;database=Movies";
        String username = "CloudSA0a345777";
        String password = "BobinkataQk69!";

        // SQL statement to insert a new movie
        String sql = "INSERT INTO Movies (Title, ReleaseYear, Genre, Description, Director, Actors) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, movie.getTitle());
            pstmt.setInt(2, movie.getReleaseYear());
            pstmt.setString(3, movie.getGenre());
            pstmt.setString(4, movie.getDescription());
            pstmt.setString(5, movie.getDirector());
            pstmt.setString(6, movie.getActors());

            pstmt.executeUpdate();
        }
    }
}
