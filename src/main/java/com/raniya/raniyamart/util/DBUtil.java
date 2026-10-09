package com.raniya.raniyamart.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.File;
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

        // Ensure data directory exists for persistent file storage
        File dataDir = new File("./data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        Properties props = loadConfigProperties();

        HikariConfig config = new HikariConfig();
        String driver = System.getenv("DB_DRIVER") != null ? System.getenv("DB_DRIVER") : props.getProperty("db.driver", "org.h2.Driver");
        String url = System.getenv("DB_URL") != null ? System.getenv("DB_URL") : props.getProperty("db.url", "jdbc:h2:file:./data/raniyamartdb;DB_CLOSE_DELAY=-1;MODE=MySQL;AUTO_SERVER=TRUE");
        String user = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : (System.getenv("DB_USERNAME") != null ? System.getenv("DB_USERNAME") : props.getProperty("db.user", props.getProperty("db.username", "sa")));
        String pass = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : props.getProperty("db.password", "");

        config.setDriverClassName(driver);
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(pass);
        config.setMaximumPoolSize(Integer.parseInt(props.getProperty("db.pool.maxSize", "10")));
        config.setMinimumIdle(Integer.parseInt(props.getProperty("db.pool.minIdle", "2")));
        config.setIdleTimeout(Long.parseLong(props.getProperty("db.pool.idleTimeout", "300000")));
        config.setPoolName("RaniyaMartHikariPool");

        dataSource = new HikariDataSource(config);
        LOGGER.info("HikariCP Persistent DataSource initialized successfully with URL: " + url);

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
            LOGGER.log(Level.FINE, "config.properties not found, using persistent file H2 defaults", e);
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
                LOGGER.info("seed.sql executed successfully for initial data seeding!");
            } else {
                LOGGER.info("Database users table already populated. Persistent data loaded cleanly.");
            }

            // Auto-recover sunscreen / skincare products or legacy broken image URLs
            try {
                stmt.executeUpdate("UPDATE products SET image_url = 'https://images.unsplash.com/photo-1598440947619-2c35fc9aa908?auto=format&fit=crop&w=600&q=80' WHERE LOWER(name) LIKE '%sunscreen%' OR LOWER(name) LIKE '%skincare%' OR LOWER(name) LIKE '%lotion%' OR LOWER(name) LIKE '%cream%' OR image_url LIKE '%photo-1560343090-f0409e92791a%'");
            } catch (Exception ex) {
                LOGGER.log(Level.FINE, "Image recovery query executed", ex);
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
