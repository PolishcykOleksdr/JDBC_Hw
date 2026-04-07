package com.oleksdr.hw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * author: user,
 * date: 07.04.2026
 */

public class DatabaseConnector {
    private final Connection connection;

    private final String username;
    private final String password;
    private final String url;

    private final static Logger logger = LoggerFactory.getLogger(DatabaseConnector.class);

    public DatabaseConnector(String url, String username, String password) {
        this.username = username;
        this.password = password;
        this.url = url;
        this.connection = establishConnection();
    }

    public Connection getConnectionObject(){
        return connection;
    }

    private Connection establishConnection(){
        try {
            logger.info("Connecting to database...");
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            logger.error("Connection Failed - {}", e.getMessage());
        }
        logger.error("Failed to connect to database.");
        return null;
    }

    public void closeConnection(){
        if (connection == null) {
            logger.warn("Connection is null, nothing to close.");
            return;
        }

        try {
            logger.info("Closing database connection...");
            connection.close();
            logger.info("Closed database connection...");
        } catch (SQLException e) {
            logger.error("Closing connection failed - {}", e.getMessage());
        }
    }
}