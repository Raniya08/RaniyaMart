package com.raniya.raniyamart.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.Level;

public class DBUtil {

    private static final Logger LOGGER = Logger.getLogger(DBUtil.class.getName());
    private static HikariDataSource dataSource;

    public static synchronized void initializeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            return;
        }

        Properties props = loadConfigProperties();

        HikariConfig config = new HikariConfig();
        String driver = props.getProperty("db.driver", "org.h2.Driver");
        String url = props.getProperty("db.url", "jdbc:h2:mem:raniyamartdb;DB_CLOSE_DELAY=-1;MODE=MySQL");
        String user = props.getProperty("db.user", "sa");
        String pass = props.getProperty("db.password", "");

        config.setDriverClassName(driver);
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(pass);
        config.setMaximumPoolSize(Integer.parseInt(props.getProperty("db.pool.maxSize", "10")));
        config.setMinimumIdle(Integer.parseInt(props.getProperty("db.pool.minIdle", "2")));
        config.setIdleTimeout(Long.parseLong(props.getProperty("db.pool.idleTimeout", "300000")));
        config.setPoolName("RaniyaMartHikariPool");

        dataSource = new HikariDataSource(config);
        LOGGER.info("HikariCP DataSource initialized successfully with URL: " + url);

        runSchemaAndSeedScripts();
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null || dataSource.isClosed()) {
            initializeDataSource();
        }
        return dataSource.getConnection();
    }

    public static synchronized void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            LOGGER.info("HikariCP DataSource closed.");
        }
    }

    private static Properties loadConfigProperties() {
        Properties props = new Properties();
        try (InputStream is = DBUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) {
                props.load(is);
            }
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "config.properties not found, using default embedded H2 parameters", e);
        }
        return props;
    }

    private static void runSchemaAndSeedScripts() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            executeScript(stmt, "schema.sql");
            
            // Check if users table is empty before seeding
            var rs = stmt.executeQuery("SELECT COUNT(*) FROM users");
            if (rs.next() && rs.getInt(1) == 0) {
                executeScript(stmt, "seed.sql");
                LOGGER.info("seed.sql executed successfully!");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error initializing database schema/seed", e);
        }
    }

    private static void executeScript(Statement stmt, String resourceName) throws Exception {
        InputStream is = DBUtil.class.getClassLoader().getResourceAsStream(resourceName);
        if (is == null) return;
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("--")) continue;
                sb.append(line).append(" ");
                if (line.endsWith(";")) {
                    stmt.execute(sb.toString());
                    sb.setLength(0);
                }
            }
        }
    }
}
