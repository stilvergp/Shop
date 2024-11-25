package com.github.stilvergp.model.entity;

import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.UUID;

public class Product {
    private int id;
    private String code;
    private BufferedImage image;
    private String name;
    private double price;
    private int stock;

    public Product() {
        this.code = UUID.randomUUID().toString().substring(0, 8);
    }

    public Product(BufferedImage image, String name, double price,int stock) {
        this.code = UUID.randomUUID().toString().substring(0, 8);
        this.image = image;
        this.name = name;
        this.price = price;
        this.stock = stock;
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


    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(code, product.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", image=" + image +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
