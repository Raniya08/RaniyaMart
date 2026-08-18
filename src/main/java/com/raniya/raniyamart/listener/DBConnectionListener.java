package com.raniya.raniyamart.listener;

import com.raniya.raniyamart.util.DBUtil;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.Logger;

@WebListener
public class DBConnectionListener implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(DBConnectionListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("RaniyaMart Application Context initializing... starting HikariCP pool.");
        DBUtil.initializeDataSource();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("RaniyaMart Application Context shutting down... closing HikariCP pool.");
        DBUtil.closeDataSource();
    }
}
