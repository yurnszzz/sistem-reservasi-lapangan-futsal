package com.futsalku.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConnection — Singleton pattern untuk koneksi database
 * Mengelola koneksi ke MySQL database
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/futsalku_db";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Sesuaikan password MySQL

    private static Connection connection;

    /**
     * Mendapatkan koneksi database (Singleton)
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL JDBC Driver tidak ditemukan!", e);
            }
        }
        return connection;
    }

    /**
     * Menutup koneksi database
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
