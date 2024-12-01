package com.github.stilvergp.model.connection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InitializeDatabase {
    private Connection conn;

    public InitializeDatabase() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeSqlFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sqlBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sqlBuilder.append(line).append("\n");
            }
            String[] sqlCommands = sqlBuilder.toString().split(";");
            for (String command : sqlCommands) {
                if (!command.trim().isEmpty()) {
                    executeCommand(command);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void executeCommand(String sql) {
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertDefaultData() {
        try {
            InsertDefaultContent.addDefaultClient();
            InsertDefaultContent.addDefaultProducts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                File file = new File("initialized.txt");
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isDatabaseInitialized() {
        File file = new File("initialized.txt");
        return file.exists();
    }
}
