package com.github.stilvergp;

import com.github.stilvergp.model.connection.MySQLConnection;

import java.sql.SQLException;

public class Executable {
    public static void main(String[] args) {
        App.main(args);
        try {
            MySQLConnection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
