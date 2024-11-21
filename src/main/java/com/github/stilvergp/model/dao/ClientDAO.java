package com.github.stilvergp.model.dao;

import com.github.stilvergp.model.connection.MySQLConnection;
import com.github.stilvergp.model.entity.Client;
import com.github.stilvergp.model.entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ClientDAO {
    private static final String INSERT = "INSERT into client(name,username,password,email,phone,isAdmin) VALUES(?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE client SET username=? WHERE id=?";
    private static final String FINDBYID = "SELECT id, name, username, password, email, phone, isAdmin FROM client WHERE username=?";
    private static final String DELETE = "DELETE FROM client WHERE id=?";
    private final Connection conn;

    public ClientDAO() {
        conn = MySQLConnection.getConnection();
    }

    public void add(Client client) {
        if (client != null) {
            String username = client.getUsername();
            if (username != null) {
                Client isInDatabase = findById(username);
                if (isInDatabase == null) {
                    try (PreparedStatement pst = conn.prepareStatement(INSERT)) {
                        pst.setString(1, client.getName());
                        pst.setString(2, client.getUsername());
                        pst.setString(3, client.getPassword());
                        pst.setString(4, client.getEmail());
                        pst.setString(5, client.getPhone());
                        pst.setBoolean(6, client.isAdmin());
                        pst.executeUpdate();
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
                Client isInDatabase = findById(username);
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
class ClientLazy extends Client {
    @Override
    public List<Order> getOrders() {
        if (super.getOrders() == null) {
            OrderDAO dao = new OrderDAO();
            setOrders(dao.findByClient(this));
        }
        return super.getOrders();
    }
}