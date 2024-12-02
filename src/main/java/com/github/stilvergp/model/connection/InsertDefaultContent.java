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

        Product product1 = new Product("80c4362b", ImageIO.read(App.class.getResourceAsStream("images/patatas.jpg")), "Patatas", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product1);
        Product product2 = new Product("cf5423ff", ImageIO.read(App.class.getResourceAsStream("images/tomates.jpg")), "Tomates", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product2);
        Product product3 = new Product("f2bb99c8", ImageIO.read(App.class.getResourceAsStream("images/cebollas.jpg")), "Cebollas", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product3);
        Product product4 = new Product("89a437ad", ImageIO.read(App.class.getResourceAsStream("images/sandias.jpg")), "Sandias", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product4);
        Product product5 = new Product("7b6c05af", ImageIO.read(App.class.getResourceAsStream("images/jamones.jpg")), "Jamones", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product5);
        Product product6 = new Product("503aa05b", ImageIO.read(App.class.getResourceAsStream("images/albondigas.jpg")), "Albondigas", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product6);
        Product product7 = new Product("d49ef99e", ImageIO.read(App.class.getResourceAsStream("images/barraPan.jpg")), "BarraPan", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product7);
        Product product8 = new Product("87876e34", ImageIO.read(App.class.getResourceAsStream("images/quesos.jpg")), "Quesos", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product8);
        Product product9 = new Product("59d2b9d8", ImageIO.read(App.class.getResourceAsStream("images/cerezas.jpg")), "Cerezas", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product9);
        Product product10 = new Product("d68ec4ae", ImageIO.read(App.class.getResourceAsStream("images/manzanas.jpg")), "Manzanas", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
        products.add(product10);
//        Product product11 = new Product("5274af4b", ImageIO.read(App.class.getResourceAsStream("images/lechugas.jpg")), "Lechugas", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
//        products.add(product11);
//        Product product12 = new Product("32b6210c", ImageIO.read(App.class.getResourceAsStream("images/berenjenas.jpg")), "Berenjenas", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
//        products.add(product12);
//        Product product13 = new Product("ccd19aa4", ImageIO.read(App.class.getResourceAsStream("images/naranjas.jpg")), "Naranjas", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
//        products.add(product13);
//        Product product14 = new Product("400f367f", ImageIO.read(App.class.getResourceAsStream("images/ciruelas.jpg")), "Ciruelas", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
//        products.add(product14);
//        Product product15 = new Product("19e88c92", ImageIO.read(App.class.getResourceAsStream("images/donuts.jpg")), "Donuts", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
//        products.add(product15);
//        Product product16 = new Product("e7c8f821", ImageIO.read(App.class.getResourceAsStream("images/salchichas.jpg")), "Salchichas", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
//        products.add(product16);
//        Product product17 = new Product("410b1001", ImageIO.read(App.class.getResourceAsStream("images/yogures.jpg")), "Yogures", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
//        products.add(product17);
//        Product product18 = new Product("c97a7806", ImageIO.read(App.class.getResourceAsStream("images/granadas.jpg")), "Granadas", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
//        products.add(product18);
//        Product product19 = new Product("f91069d9", ImageIO.read(App.class.getResourceAsStream("images/ketchup.jpg")), "Ketchup", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
//        products.add(product19);
//        Product product20 = new Product("cb06c017", ImageIO.read(App.class.getResourceAsStream("images/mayonesa.jpg")), "Mayonesa", Math.round(((5 + (Math.random() * 95)) * 100.0) / 100.0), (int) (5 + (Math.random() * 50)));
//        products.add(product20);
        for (Product product : products) {
            pDAO.add(product);
        }
    }
}
