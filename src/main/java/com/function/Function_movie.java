package com.function;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class Function {

    private static final String connectionString = System.getenv("AzureSqlConnection");

    @FunctionName("AddMovie")
    public HttpResponseMessage addMovie(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.POST},
                authLevel = AuthorizationLevel.ANONYMOUS)
                HttpRequestMessage<Movie> request,
            final ExecutionContext context) {

        Movie movie = request.getBody();
        try (Connection connection = getConnection()) {
            String query = "INSERT INTO Movies (Title, Year, Genre, Description, Director, Actors) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, movie.getTitle());
            statement.setInt(2, movie.getYear());
            statement.setString(3, movie.getGenre());
            statement.setString(4, movie.getDescription());
            statement.setString(5, movie.getDirector());
            statement.setString(6, movie.getActors());
            statement.executeUpdate();
        } catch (Exception e) {
            context.getLogger().severe("Error adding movie: " + e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding movie: " + e.getMessage()).build();
        }

        return request.createResponseBuilder(HttpStatus.CREATED)
                .body("Movie added: " + movie.getTitle()).build();
    }

    @FunctionName("AddRating")
    public HttpResponseMessage addRating(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.POST},
                authLevel = AuthorizationLevel.ANONYMOUS)
                HttpRequestMessage<Rating> request,
            final ExecutionContext context) {

        Rating rating = request.getBody();
        try (Connection connection = getConnection()) {
            String query = "INSERT INTO Ratings (Title, Opinion, Rating, DateTime, Author) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, rating.getTitle());
            statement.setString(2, rating.getOpinion());
            statement.setInt(3, rating.getRating());
            statement.setString(4, rating.getDateTime());
            statement.setString(5, rating.getAuthor());
            statement.executeUpdate();
        } catch (Exception e) {
            context.getLogger().severe("Error adding rating: " + e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding rating: " + e.getMessage()).build();
        }

        return request.createResponseBuilder(HttpStatus.CREATED)
                .body("Rating added for movie: " + rating.getTitle()).build();
    }

    @FunctionName("CalculateAverageRatings")
    public void calculateAverageRatings(
            @TimerTrigger(name = "timerInfo", schedule = "0 30 11 * * *") String timerInfo,
            final ExecutionContext context) {
        
        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT DISTINCT Title FROM Ratings";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = selectStatement.executeQuery();
            
            while (resultSet.next()) {
                String movieTitle = resultSet.getString("Title");
                String averageRatingQuery = "SELECT AVG(Rating) AS AverageRating FROM Ratings WHERE Title = ?";
                PreparedStatement averageRatingStatement = connection.prepareStatement(averageRatingQuery);
                averageRatingStatement.setString(1, movieTitle);
                ResultSet averageRatingResult = averageRatingStatement.executeQuery();
                
                if (averageRatingResult.next()) {
                    double averageRating = averageRatingResult.getDouble("AverageRating");
                    String updateQuery = "UPDATE Movies SET AverageRating = ? WHERE Title = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                    updateStatement.setDouble(1, averageRating);
                    updateStatement.setString(2, movieTitle);
                    updateStatement.executeUpdate();
                }
            }
        } catch (Exception e) {
            context.getLogger().severe("Error calculating average ratings: " + e.getMessage());
        }
    }


    @FunctionName("SearchMovies")
    public HttpResponseMessage searchMovies(
        @HttpTrigger(
            name = "req",
            methods = {HttpMethod.GET},
            authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<String>> request,
        final ExecutionContext context) {

    String searchString = request.getQueryParameters().getOrDefault("searchString", "");
    StringBuilder responseBuilder = new StringBuilder();

    try (Connection connection = getConnection()) {
        String query = "SELECT * FROM Movies WHERE Title LIKE ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "%" + searchString + "%");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String title = resultSet.getString("Title");
            int year = resultSet.getInt("Year");
            String genre = resultSet.getString("Genre");
            String description = resultSet.getString("Description");
            String director = resultSet.getString("Director");
            String actors = resultSet.getString("Actors");
            double averageRating = resultSet.getDouble("AverageRating");

            responseBuilder.append("Title: ").append(title).append("\n");
            responseBuilder.append("Year: ").append(year).append("\n");
            responseBuilder.append("Genre: ").append(genre).append("\n");
            responseBuilder.append("Description: ").append(description).append("\n");
            responseBuilder.append("Director: ").append(director).append("\n");
            responseBuilder.append("Actors: ").append(actors).append("\n");
            responseBuilder.append("Average Rating: ").append(averageRating).append("\n\n");
            
            // Fetch ratings and reviews for the movie and append to responseBuilder
        }
    } catch (Exception e) {
        context.getLogger().severe("Error searching movies: " + e.getMessage());
        return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error searching movies: " + e.getMessage()).build();
    }

    return request.createResponseBuilder(HttpStatus.OK).body(responseBuilder.toString()).build();
    }
}