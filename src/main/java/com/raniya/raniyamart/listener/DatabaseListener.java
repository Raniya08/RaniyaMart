package com.raniya.raniyamart.listener;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.raniya.raniyamart.util.DBConnection;

@WebListener
public class DatabaseListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            DBConnection.initialize();

            runSqlFile("schema.sql");
            runSqlFile("seed.sql");

            System.out.println("RaniyaMart database initialized.");

        } catch (Exception e) {
            throw new RuntimeException(
                    "Database startup failed", e);
        }
    }

    private void runSqlFile(String fileName) throws Exception {
        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream(fileName)) {

            if (input == null) {
                throw new IllegalStateException(
                        fileName + " not found");
            }

            String sql = new String(
                    input.readAllBytes(),
                    java.nio.charset.StandardCharsets.UTF_8);

            try (Connection connection = DBConnection.getConnection();
                 Statement statement = connection.createStatement()) {

                for (String command : sql.split(";")) {
                    String trimmed = command.trim();

                    if (!trimmed.isEmpty()) {
                        statement.execute(trimmed);
                    }
                }
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        DBConnection.shutdown();
    }
}