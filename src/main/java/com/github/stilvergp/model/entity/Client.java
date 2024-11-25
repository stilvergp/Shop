package com.github.stilvergp.model.entity;

import com.github.stilvergp.utils.Security;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client {
    private int id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String phone;
    private List<Order> orders;
    private boolean isAdmin;

    public Client() {
        this.isAdmin = false;
    }

    public Client(String name, String username, String password, String email, String phone) {
        this.name = name;
        this.username = username;
        setPassword(password);
        this.email = email;
        this.phone = phone;
        this.orders = new ArrayList<>();
        this.isAdmin = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Security.hashPassword(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public boolean isMyPassword(String password) {
        return this.password.equals(Security.hashPassword(password));
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) && Objects.equals(username, client.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", orders=" + orders +
                '}';
    }
}
