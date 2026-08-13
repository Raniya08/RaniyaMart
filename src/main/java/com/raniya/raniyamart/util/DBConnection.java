package com.raniya.raniyamart.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public final class DBConnection {

    private static HikariDataSource dataSource;

    private DBConnection() {
    }

    public static void initialize() {
        if (dataSource != null) {
            return;
        }

        try {
            Properties properties = new Properties();

            try (InputStream input = DBConnection.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties")) {

                if (input == null) {
                    throw new IllegalStateException(
                            "config.properties not found");
                }

                properties.load(input);
            }

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(properties.getProperty("db.url"));
            config.setUsername(properties.getProperty("db.username"));
            config.setPassword(properties.getProperty("db.password"));
            config.setDriverClassName(properties.getProperty("db.driver"));
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);

            dataSource = new HikariDataSource(config);

        } catch (Exception e) {
            throw new RuntimeException("Database initialization failed", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            initialize();
        }

        return dataSource.getConnection();
    }

    public static void shutdown() {
        if (dataSource != null) {
            dataSource.close();
            dataSource = null;
        }
    }
}