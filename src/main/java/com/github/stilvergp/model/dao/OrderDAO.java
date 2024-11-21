package com.github.stilvergp.model.dao;

import com.github.stilvergp.model.connection.MySQLConnection;
import com.github.stilvergp.model.entity.Client;
import com.github.stilvergp.model.entity.Order;

import java.sql.Connection;
import java.util.List;

public class OrderDAO {
    private static final String INSERT = "";
    private static final String UPDATE = "";
    private static final String FINDBYID = "";
    private static final String FINDBYCLIENT = "";
    private static final String DELETE = "";
    private final Connection conn;

    public OrderDAO() {
        conn = MySQLConnection.getConnection();
    }

    public void add(Order order) {

    }

    public void update(Order order) {

    }

    public Order findById(String id) {

        return null;
    }

    public List<Order> findByClient(Client client) {
        
        return null;
    }

}
