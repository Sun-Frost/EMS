package com.example.ems.controllers;

import com.example.ems.db.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class MoodController {

    public static boolean userVotedToday(int userId) {
        String query = "SELECT COUNT(*) AS voteCount FROM Mood WHERE userId = ? AND date = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setDate(2, Date.valueOf(LocalDate.now()));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("voteCount") > 0;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }

    public static boolean submitMood(int userId, int moodRating) {
        String query = "INSERT INTO Mood (userId, moodRating, date) VALUES (?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, moodRating);
            statement.setDate(3, Date.valueOf(LocalDate.now()));
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }
}