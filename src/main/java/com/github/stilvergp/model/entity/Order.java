package com.github.stilvergp.model.entity;

import java.time.LocalDate;
import java.util.*;

public class Order {
    private int id;
    private String code;
    private Client client;
    private String address;
    private LocalDate orderDate;
    private boolean isCompleted;
    private double totalPrice;
    private List<Product> products;

    public Order() {
        this.code = UUID.randomUUID().toString().substring(0, 8);
        this.isCompleted = false;
        this.totalPrice = 0;
        this.products = new ArrayList<>();
    }

    public Order(Client client, String address, LocalDate orderDate) {
        this.code = UUID.randomUUID().toString().substring(0, 8);
        this.client = client;
        this.address = address;
        this.orderDate = orderDate;
        this.isCompleted = false;
        this.totalPrice = 0;
        this.products = new ArrayList<>();
    }

    public void calculateTotalPrice() {
        for (Product product : products) {
            totalPrice += product.getPrice();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(code, order.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", client=" + client +
                ", address='" + address + '\'' +
                ", orderDate=" + orderDate +
                ", isCompleted=" + isCompleted +
                ", totalPrice=" + totalPrice +
                ", products=" + products +
                '}';
    }
}
