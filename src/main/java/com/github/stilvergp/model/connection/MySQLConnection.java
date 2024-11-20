package com.github.stilvergp.model.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/shop";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static MySQLConnection _instance;
    private static Connection conn;

    private MySQLConnection() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            conn = null;
        }
    }

    public static Connection getConnection() {
        if(_instance==null) {
            _instance = new MySQLConnection();
        }
        return conn;
    }
}
