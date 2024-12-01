package com.github.stilvergp.model.dao;

import com.github.stilvergp.model.connection.MySQLConnection;
import com.github.stilvergp.model.entity.Client;
import com.github.stilvergp.model.entity.Order;
import com.github.stilvergp.model.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private static final String INSERT = "INSERT INTO `Order`(code, client_id, address, orderDate, deliveryDate, isCompleted, totalPrice) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE `Order` SET address = ? WHERE code = ?";
    private static final String FINDBYID = "SELECT * FROM `Order` WHERE code = ? AND isCompleted = false";
    private static final String FINDBYCLIENT = "SELECT * FROM `Order` WHERE client_id = ? AND isCompleted = false";
    private static final String CANCEL = "UPDATE `Order` SET isCompleted = ? WHERE code = ?";
    private static final String INSERTORDERPRODUCT = "INSERT INTO Order_Product(order_id, product_id) VALUES (?, ?)";
    private static final String FINDBYORDERPRODUCT = "SELECT * FROM Order_Product WHERE order_id = ? AND product_id = ?";
    private final Connection conn;

    public OrderDAO() {
        conn = MySQLConnection.getConnection();
    }

    public void add(Order order) {
        if (order != null) {
            String code = order.getCode();
            if (code != null) {
                Order isInDatabase = findById(code);
                if (isInDatabase == null) {
                    try (PreparedStatement pst = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                        pst.setString(1, order.getCode());
                        pst.setInt(2, order.getClient().getId());
                        pst.setString(3, order.getAddress());
                        pst.setDate(4, Date.valueOf(order.getOrderDate()));
                        pst.setDate(5, Date.valueOf(order.getDeliveryDate()));
                        pst.setBoolean(6, order.isCompleted());
                        pst.setDouble(7, order.getTotalPrice());
                        pst.executeUpdate();
                        try (ResultSet rs = pst.getGeneratedKeys()) {
                            if (rs.next()) {
                                order.setId(rs.getInt(1));
                            }
                        }
                        for (Product product : order.getProducts()) {
                            addOrderProduct(order, product);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void addOrderProduct(Order order, Product product) {
        if (order != null && product != null) {
            int orderId = order.getId();
            int productId = product.getId();
            if (orderId > 0 && productId > 0) {
                try (PreparedStatement checkPst = conn.prepareStatement(FINDBYORDERPRODUCT)) {
                    checkPst.setInt(1, orderId);
                    checkPst.setInt(2, productId);
                    try (ResultSet rs = checkPst.executeQuery()) {
                        if (!rs.next()) {
                            try (PreparedStatement pst = conn.prepareStatement(INSERTORDERPRODUCT)) {
                                pst.setInt(1, orderId);
                                pst.setInt(2, productId);
                                pst.executeUpdate();
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void update(Order order) {
        if (order != null) {
            String code = order.getCode();
            if (code != null) {
                Order isInDatabase = findById(code);
                if (isInDatabase == null) {
                    try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
                        pst.setString(1, order.getAddress());
                        pst.setString(2, order.getCode());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Order findById(String id) {
        Order result = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYID)) {
            pst.setString(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setCode(rs.getString("code"));
                    order.setClient(new ClientDAO().findById(rs.getInt("client_id")));
                    order.setAddress(rs.getString("address"));
                    order.setOrderDate((rs.getDate("orderDate").toLocalDate()));
                    order.setDeliveryDate((rs.getDate("deliveryDate").toLocalDate()));
                    order.setCompleted(rs.getBoolean("isCompleted"));
                    order.setTotalPrice(rs.getDouble("totalPrice"));
                    order.setProducts(new ProductDAO().findByOrder(order.getId()));
                    result = order;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Order> findByClient(Client client) {
        List<Order> result = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(FINDBYCLIENT)) {
            pst.setInt(1, client.getId());
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setCode(rs.getString("code"));
                    order.setClient(client);
                    order.setAddress(rs.getString("address"));
                    order.setOrderDate((rs.getDate("orderDate").toLocalDate()));
                    order.setDeliveryDate((rs.getDate("deliveryDate").toLocalDate()));
                    order.setCompleted(rs.getBoolean("isCompleted"));
                    order.setTotalPrice(rs.getDouble("totalPrice"));
                    order.setProducts(new ProductDAO().findByOrder(order.getId()));
                    result.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void cancel(Order order) {
        if (order != null) {
            try (PreparedStatement pst = conn.prepareStatement(CANCEL)) {
                pst.setBoolean(1, order.isCompleted());
                pst.setString(2, order.getCode());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
