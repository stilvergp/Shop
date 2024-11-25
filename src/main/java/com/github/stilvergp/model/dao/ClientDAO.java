package com.github.stilvergp.model.dao;

import com.github.stilvergp.model.connection.MySQLConnection;
import com.github.stilvergp.model.entity.Client;

import java.sql.*;

public class ClientDAO {
    private static final String INSERT = "INSERT into Client(name,username,password,email,phone,isAdmin) VALUES(?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE Client SET username=? WHERE id=?";
    private static final String FINDBYID = "SELECT id, name, username, password, email, phone, isAdmin FROM client WHERE id=?";
    private static final String FINDBYUSERNAME = "SELECT id, name, username, password, email, phone, isAdmin FROM client WHERE username=?";
    private static final String DELETE = "DELETE FROM Client WHERE id=?";
    private final Connection conn;

    public ClientDAO() {
        conn = MySQLConnection.getConnection();
    }

    public void add(Client client) {
        if (client != null) {
            String username = client.getUsername();
            if (username != null) {
                Client isInDatabase = findByUsername(username);
                if (isInDatabase == null) {
                    try (PreparedStatement pst = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                        pst.setString(1, client.getName());
                        pst.setString(2, client.getUsername());
                        pst.setString(3, client.getPassword());
                        pst.setString(4, client.getEmail());
                        pst.setString(5, client.getPhone());
                        pst.setBoolean(6, client.isAdmin());
                        pst.executeUpdate();
                        try (ResultSet rs = pst.getGeneratedKeys()) {
                            if (rs.next()) {
                                client.setId(rs.getInt(1));
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void update(Client client) {
        if (client != null) {
            String username = client.getUsername();
            if (username != null) {
                Client isInDatabase = findByUsername(username);
                if (isInDatabase == null) {
                    try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
                        pst.setString(1, client.getUsername());
                        pst.setInt(2, client.getId());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Client findById(int id) {
        Client result = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYID)) {
            pst.setInt(1, id);
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
                    OrderDAO orderDAO = new OrderDAO();
                    client.setOrders(orderDAO.findByClient(result));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Client findByUsername(String id) {
        Client result = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYUSERNAME)) {
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
                    OrderDAO orderDAO = new OrderDAO();
                    client.setOrders(orderDAO.findByClient(result));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void delete(Client client) {
        if (client != null) {
            try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
                pst.setInt(1, client.getId());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
