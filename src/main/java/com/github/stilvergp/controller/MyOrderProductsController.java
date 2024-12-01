package com.github.stilvergp.controller;

import com.github.stilvergp.model.dao.ProductDAO;
import com.github.stilvergp.model.entity.Order;
import com.github.stilvergp.model.entity.Product;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.converter.DoubleStringConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyOrderProductsController extends Controller implements Initializable {

    @FXML
    private TableView<Product> tableView;

    @FXML
    private TableColumn<Product, Image> imageColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, Double> priceColumn;

    private MyOrdersController controller;
    private Order order;

    @Override
    public void onOpen(Object input) throws IOException {
        this.controller = (MyOrdersController) input;
        this.order = this.controller.getDoubleClickedOrder();
        reloadOrderProductsFromDatabase();
    }

    private void reloadOrderProductsFromDatabase() {
        ProductDAO productDAO = new ProductDAO();
        ObservableList<Product> products = FXCollections.observableArrayList(productDAO.findByOrder(order.getId()));
        this.tableView.setItems(products);
    }


    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageColumn.setCellValueFactory(product -> new ImageView(convertToJavaFXImage(product.getValue().getImage())).imageProperty());
        imageColumn.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Image image, boolean empty) {
                super.updateItem(image, empty);
                if (empty || image == null) {
                    imageView.setImage(null);
                    setGraphic(null);
                } else {
                    imageView.setImage(image);
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(150);
                    setGraphic(imageView);
                }
            }
        });
        nameColumn.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getName()));
        priceColumn.setCellValueFactory(product -> new SimpleDoubleProperty(product.getValue().getPrice()).asObject());
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
    }

    public Image convertToJavaFXImage(BufferedImage productImage) {
        Image image = null;
        if (productImage != null) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(productImage, "png", baos);
                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                image = new Image(bais);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return image;
    }
}
