package com.github.stilvergp.controller;

import com.github.stilvergp.model.dao.ProductDAO;
import com.github.stilvergp.model.entity.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController extends Controller implements Initializable {
    private List<Product> products;
    private List<Product> selectedProducts = new ArrayList<>();

    @FXML
    private GridPane productsGrid;

    @Override
    public void onOpen(Object input) throws IOException {
        populateProductsGrid();
    }

    @Override
    public void onClose(Object output) {

    }

    private void populateProductsGrid() {
        int col = 0, row = 0;

        for (Product product : products) {
            VBox productBox = new VBox(5);
            productBox.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-border-radius: 5;");
            productBox.setAlignment(Pos.CENTER);

            // Imagen del producto.
            ImageView imageView = new ImageView(convertToJavaFXImage(product.getImage()));
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);

            // Nombre y precio del producto.
            Label nameLabel = new Label(product.getName());
            Label priceLabel = new Label(product.getPrice() + "€");

            // Checkbox para seleccionar el producto.
            CheckBox checkBox = new CheckBox("Comprar");
            checkBox.setOnAction(e -> {
                if (checkBox.isSelected()) {
                    selectedProducts.add(product);
                } else {
                    selectedProducts.remove(product);
                }
            });
            productBox.getChildren().addAll(imageView, nameLabel, priceLabel, checkBox);

            productsGrid.add(productBox, col, row);

            // Gestionar la posición en el GridPane.
            col++;
            if (col == 2) { // Cambia 2 por el número deseado de columnas.
                col = 0;
                row++;
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.products = new ProductDAO().findAll();
    }

    public Image convertToJavaFXImage(BufferedImage roomImage) {
        Image image = null;
        if (roomImage != null) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(roomImage, "png", baos);
                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                image = new Image(bais);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return image;
    }
}
