package com.github.stilvergp.model.dao;

import com.github.stilvergp.model.connection.MySQLConnection;
import com.github.stilvergp.model.entity.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDAO {
    private static final String INSERT = "INSERT into client(name,username,password,email,phone,isAdmin) VALUES(?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE client SET username=? WHERE id=?";
    private static final String FINDBYID = "SELECT id, name, username, password, email, phone, isAdmin FROM client WHERE username=?";
    private static final String DELETE = "DELETE FROM client WHERE id=?";
    private Connection conn;

    public ClientDAO() {
        conn = MySQLConnection.getConnection();
    }

    public void add(Client entity) {
        if (entity != null) {
            String username = entity.getUsername();
            if (username != null) {
                Client isInDatabase = findById(username);
                if (isInDatabase == null) {
                    try (PreparedStatement pst = conn.prepareStatement(INSERT)) {
                        pst.setString(1, entity.getName());
                        pst.setString(2, entity.getUsername());
                        pst.setString(3, entity.getPassword());
                        pst.setString(4, entity.getEmail());
                        pst.setString(5, entity.getPhone());
                        pst.setBoolean(6, entity.isAdmin());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void update(Client entity) {
        if (entity != null) {
            String username = entity.getUsername();
            if (username != null) {
                Client isInDatabase = findById(username);
                if (isInDatabase == null) {
                    try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
                        pst.setString(1, entity.getUsername());
                        pst.setInt(2, entity.getId());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Client findById(String id) {
        Client result = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYID)) {
            pst.setString(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Client client = new Client();
                    client.setId(rs.getInt("id"));
                    client.setName(rs.getString("name"));
                    client.setUsername(rs.getString("username"));
                    client.setPassword(rs.getString("password"));
                    client.setEmail(rs.getString("email"));
                    client.setPhone(rs.getString("phone"));
                    client.setAdmin(rs.getBoolean("isAdmin"));
                    result = client;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void delete(Client entity) {
        if (entity != null) {
            try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
                pst.setInt(1, entity.getId());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
