package com.github.stilvergp.model.connection;

import com.github.stilvergp.App;
import com.github.stilvergp.model.dao.ClientDAO;
import com.github.stilvergp.model.dao.ProductDAO;
import com.github.stilvergp.model.entity.Client;
import com.github.stilvergp.model.entity.Product;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InsertDefaultContent {

    public static void addDefaultClient() {
        ClientDAO cDAO = new ClientDAO();

        Client admin = new Client("admin", "admin", "admin", "admin@admin.admin", "123456789");
        admin.setAdmin(true);
        if (!admin.equals(cDAO.findByUsername(admin.getUsername()))) {
            cDAO.add(admin);
        }
    }

    public static void addDefaultProducts() throws IOException {
        ProductDAO pDAO = new ProductDAO();
        List<Product> products = new ArrayList<>();
        Product product1 = new Product(ImageIO.read(App.class.getResourceAsStream("productImages/patatas.jpg")), "patatas", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product1);
        Product product2 = new Product(ImageIO.read(App.class.getResourceAsStream("productImages/tomates.jpg")), "tomates", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product2);
        Product product3 = new Product(ImageIO.read(App.class.getResourceAsStream("productImages/cebollas.jpg")), "cebollas", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product3);
        Product product4 = new Product(ImageIO.read(App.class.getResourceAsStream("productImages/sandias.jpg")), "sandias", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product4);
        Product product5 = new Product(ImageIO.read(App.class.getResourceAsStream("productImages/jamones.jpg")), "jamones", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product5);
        Product product6 = new Product(ImageIO.read(App.class.getResourceAsStream("productImages/albondigas.jpg")), "albondigas", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product6);
        Product product7 = new Product(ImageIO.read(App.class.getResourceAsStream("productImages/barraPan.jpg")), "barraPan", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product7);
        Product product8 = new Product(ImageIO.read(App.class.getResourceAsStream("productImages/quesos.jpg")), "quesos", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product8);
        Product product9 = new Product(ImageIO.read(App.class.getResourceAsStream("productImages/cerezas.jpg")), "cerezas", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product9);
        Product product10 = new Product(ImageIO.read(App.class.getResourceAsStream("productImages/manzanas.jpg")), "manzanas", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product10);
        Product product11 = new Product(ImageIO.read(App.class.getResourceAsStream("productImages/lechugas.jpg")), "lechugas", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product11);
        Product product12 = new Product(ImageIO.read(App.class.getResourceAsStream("productImages/berenjenas.jpg")), "berenjenas", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product12);
        Product product13 = new Product(ImageIO.read(App.class.getResourceAsStream("productImages/naranjas.jpg")), "naranjas", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product13);
        Product product14 = new Product(ImageIO.read(App.class.getResourceAsStream("productImages/ciruelas.jpg")), "ciruelas", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product14);
        Product product15 = new Product(ImageIO.read(App.class.getResourceAsStream("productImages/donuts.jpg")), "donuts", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product15);
        Product product16 = new Product(ImageIO.read(App.class.getResourceAsStream("productImages/salchichas.jpg")), "salchichas", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product16);
        Product product17 = new Product(ImageIO.read(App.class.getResourceAsStream("productImages/yogures.jpg")), "yogures", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product17);
        Product product18 = new Product(ImageIO.read(App.class.getResourceAsStream("productImages/granadas.jpg")), "granadas", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product18);
        Product product19 = new Product(ImageIO.read(App.class.getResourceAsStream("productImages/ketchup.jpg")), "ketchup", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product19);
        Product product20 = new Product(ImageIO.read(App.class.getResourceAsStream("productImages/mayonesa.jpg")), "mayonesa", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product20);
        for (Product product : products) {
            pDAO.add(product);
        }
    }
}
