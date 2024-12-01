package com.github.stilvergp.controller;

import com.github.stilvergp.App;
import com.github.stilvergp.model.Session;
import com.github.stilvergp.model.dao.OrderDAO;
import com.github.stilvergp.model.dao.ProductDAO;
import com.github.stilvergp.model.entity.Order;
import com.github.stilvergp.model.entity.Product;
import com.github.stilvergp.utils.Alerts;
import com.github.stilvergp.view.Scenes;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

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
    @FXML
    private TableView<Product> tableView;

    @FXML
    private TableColumn<Product, String> codeColumn;

    @FXML
    private TableColumn<Product, Image> imageColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, Double> priceColumn;

    @FXML
    private TableColumn<Product, Integer> stockColumn;

    private TableColumn<Product, Boolean> selectColumn = new TableColumn<>("Seleccionar");

    @FXML
    private HBox adminHBox;

    @FXML
    private HBox clientHBox;

    @FXML
    private Button clientButton;

    @FXML
    private TextField searchBox;

    ObservableList<Product> products;

    private List<Product> selectedProducts = new ArrayList<>();

    @Override
    public void onOpen(Object input) {
        if (Session.getInstance().getLoggedInClient().isAdmin()) {
            adminHBox.setDisable(false);
            adminHBox.setVisible(true);
        }
        reloadProductsFromDatabase();
    }

    private void searchProducts(String filter) {
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList(
                products.stream()
                        .filter(product -> product.getName().toLowerCase().contains(filter.toLowerCase()))
                        .toList()
        );
        tableView.setItems(filteredProducts);
    }

    @Override
    public void onClose(Object output) {

    }

    public void reloadProductsFromDatabase() {
        ProductDAO productDAO = new ProductDAO();
        this.products = FXCollections.observableArrayList(productDAO.findAllAvailable());
        tableView.setItems(this.products);
        selectedProducts.clear();
    }

    public void addProduct() throws IOException {
        App.currentController.openModal(Scenes.ADDPRODUCT, "Agregando habitación...", this, null);
    }

    public void saveProduct(Product product) {
        ProductDAO productDAO = new ProductDAO();
        productDAO.add(product);
        reloadProductsFromDatabase();
    }

    public void deleteProduct() {
        Product product = tableView.getSelectionModel().getSelectedItem();
        if (product != null) {
            Alerts.showConfirmationAlert("Eliminación de producto",
                    "Esta a punto de borrar este producto, " +
                            "¿Está totalmente seguro de esta acción?").showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    new ProductDAO().delete(product);
                    reloadProductsFromDatabase();
                }
            });
        }
    }

    public void buyProducts() throws IOException {
        if (selectedProducts == null || selectedProducts.isEmpty()) {
            Alerts.showErrorAlert("No se han seleccionado productos", "Por favor, seleccione al menos un producto.");
            return;
        }
        App.currentController.openModal(Scenes.BUYPRODUCTS, "Comprar productos", this, null);
    }

    public void saveOrder(Order order) {
        OrderDAO orderDAO = new OrderDAO();
        orderDAO.add(order);
        selectedProducts.clear();
        tableView.refresh();
    }

    public void myOrders() throws IOException {
        App.currentController.openModal(Scenes.MYORDERS, "Mis pedidos", this, null);
    }

    public List<Product> getSelectedProducts() {
        return selectedProducts;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Session.getInstance().getLoggedInClient().isAdmin()) {
            tableView.setEditable(true);
            codeColumn.setVisible(true);
            stockColumn.setVisible(true);
            selectColumn.setVisible(false);
            clientHBox.setVisible(false);
            clientButton.setVisible(false);
        }
        codeColumn.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getCode()));
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
        priceColumn.setOnEditCommit(event -> {
            if (event.getNewValue() == event.getOldValue()) return;
            if (event.getNewValue() < 100.00) {
                Product product = event.getRowValue();
                product.setPrice(event.getNewValue());
                ProductDAO productDAO = new ProductDAO();
                productDAO.updatePrice(product);
                reloadProductsFromDatabase();
            } else {
                Alerts.showErrorAlert("Error de cambio de precio de producto",
                        "El precio introducido es mayor al precio permitido");
            }
        });
        stockColumn.setCellValueFactory(product -> new SimpleIntegerProperty(product.getValue().getStock()).asObject());
        stockColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        stockColumn.setOnEditCommit(event -> {
            if (event.getNewValue() == event.getOldValue()) return;
            if (event.getNewValue() < 999.99) {
                Product product = event.getRowValue();
                product.setStock(event.getNewValue());
                ProductDAO productDAO = new ProductDAO();
                productDAO.updateStock(product);
                reloadProductsFromDatabase();
            } else {
                Alerts.showErrorAlert("Error de cambio de stock de producto",
                        "El numero introducido es mayor de lo permitido");
            }
        });
        selectColumn.setCellValueFactory(_ -> new SimpleBooleanProperty(false));
        selectColumn.setCellFactory(_ -> new TableCell<>() {
            private final CheckBox checkBox = new CheckBox();

            @Override
            protected void updateItem(Boolean selected, boolean empty) {
                super.updateItem(selected, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }
                Product product = getTableView().getItems().get(getIndex());
                checkBox.setSelected(selectedProducts.contains(product));

                checkBox.setOnAction(_ -> {
                    if (checkBox.isSelected()) {
                        selectedProducts.add(product);
                    } else {
                        selectedProducts.remove(product);
                    }
                });
                setGraphic(checkBox);
            }
        });
        tableView.getColumns().add(selectColumn);
        searchBox.textProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                searchProducts(newValue);
            }
        });
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
