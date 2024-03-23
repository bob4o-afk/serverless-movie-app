package com.function;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CalculateAverageRatingFunction {

    @FunctionName("calculateAverageRating")
    public void run(
            @TimerTrigger(name = "timerInfo", schedule = "0 30 11 * * *") String timerInfo,
            final ExecutionContext context) {
        context.getLogger().info("Java Timer trigger function executed at: " + java.time.LocalDateTime.now());

        try {
            updateAverageRatings();
        } catch (SQLException e) {
            context.getLogger().warning("Error updating average ratings: " + e.getMessage());
        }
    }

    private void updateAverageRatings() throws SQLException {
        String jdbcUrl = "jdbc:sqlserver://{name}.database.windows.net:1433;database={db_name}";
        String username = "######";
        String password = "#######";

        String selectSql = "SELECT Title, AVG(Rating) AS AverageRating FROM Ratings GROUP BY Title";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(selectSql);
             ResultSet resultSet = pstmt.executeQuery()) {

            while (resultSet.next()) {
                String title = resultSet.getString("Title");
                double averageRating = resultSet.getDouble("AverageRating");

                updateMovieWithAverageRating(conn, title, averageRating);
            }
        }
    }

    private void updateMovieWithAverageRating(Connection conn, String title, double averageRating) throws SQLException {
        String updateSql = "UPDATE Movies SET AverageRating = ? WHERE Title = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            pstmt.setDouble(1, averageRating);
            pstmt.setString(2, title);

            pstmt.executeUpdate();
        }
    }
}
