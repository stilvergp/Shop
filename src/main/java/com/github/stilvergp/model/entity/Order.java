package com.github.stilvergp.model.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private int id;
    private Client client;
    private String address;
    private LocalDate orderDate;
    private boolean isCompleted;
    private double price;
    private List<OrderDetails> details;

    public Order(){}

    public Order(Client client, String address, LocalDate orderDate) {
        this.client = client;
        this.address = address;
        this.orderDate = orderDate;
        this.isCompleted = false;
        this.price = calculateTotalPrice();
        this.details = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<OrderDetails> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetails> details) {
        this.details = details;
    }

    public double calculateTotalPrice() {
        this.price = 0.0;
        for (OrderDetails detail : details) {
            this.price += detail.getTotalPrice();
        }
        return this.price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", client=" + client +
                ", address='" + address + '\'' +
                ", orderDate=" + orderDate +
                ", isCompleted=" + isCompleted +
                ", price=" + price +
                ", details=" + details +
                '}';
    }
}
