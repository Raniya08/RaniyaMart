package com.raniya.raniyamart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.raniya.raniyamart.model.User;
import com.raniya.raniyamart.util.DBConnection;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean create(User user) {

        String sql = """
                INSERT INTO users
                (name, email, password_hash, role)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPasswordHash());
            statement.setString(4, user.getRole());

            int rows = statement.executeUpdate();

            if (rows == 0) {
                return false;
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setId(keys.getLong(1));
                }
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public User findByEmail(String email) {

        String sql = """
                SELECT id, name, email, password_hash, role
                FROM users
                WHERE email = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    return new User(
                            result.getLong("id"),
                            result.getString("name"),
                            result.getString("email"),
                            result.getString("password_hash"),
                            result.getString("role")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
