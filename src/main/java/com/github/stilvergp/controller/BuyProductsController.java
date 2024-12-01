package com.github.stilvergp.controller;

import com.github.stilvergp.model.Session;
import com.github.stilvergp.model.dao.ProductDAO;
import com.github.stilvergp.model.entity.Order;
import com.github.stilvergp.model.entity.Product;
import com.github.stilvergp.utils.Alerts;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class BuyProductsController extends Controller implements Initializable {

    @FXML
    private TextField addressLine;

    @FXML
    private TextField postalCode;

    @FXML
    private TextField city;

    @FXML
    private TextField province;

    @FXML
    private TextField country;

    @FXML
    private Label totalPriceLabel;

    private List<Product> products;

    private MainController controller;

    @Override
    public void onOpen(Object input) {
        this.controller = (MainController) input;
        this.products = this.controller.getSelectedProducts();
        calculateTotalPrice();
    }

    public void buyProducts(Event event) {
        if (areFieldsValid()) {
            Order order = new Order();
            order.setClient(Session.getInstance().getLoggedInClient());
            String address = addressLine.getText() + ", " + city.getText() + ", " + province.getText() + ", " + postalCode.getText() + ", " + country.getText();
            order.setAddress(address);
            order.setOrderDate(LocalDate.now());
            order.setDeliveryDate(order.getOrderDate().plusDays(new Random().nextInt(1, 10)));
            double totalPrice = 0;
            for (Product product : products) {
                totalPrice += product.getPrice();
            }
            order.setTotalPrice(totalPrice);
            order.setProducts(products);
            for (Product product : products) {
                ProductDAO productDAO = new ProductDAO();
                product.setStock(product.getStock() - 1);
                productDAO.updateStock(product);
            }
            saveAndCloseWindow(order, event);
        } else if (products.isEmpty()) {
            Alerts.showErrorAlert("Error al crear el pedido", "No hay productos seleccionados");
        } else {
            Alerts.showErrorAlert("Error al crear el pedido", "Debe rellenar todos los campos");
        }
    }

    private void saveAndCloseWindow(Order order, Event event) {
        this.controller.saveOrder(order);
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    private boolean areFieldsValid() {
        return !addressLine.getText().trim().isEmpty() && !postalCode.getText().trim().isEmpty()
                && !city.getText().trim().isEmpty() && !province.getText().trim().isEmpty() && !country.getText().trim().isEmpty();
    }

    @Override
    public void onClose(Object output) {

    }

    private void calculateTotalPrice() {
        double totalPrice = 0;
        for (Product product : products) {
            totalPrice += product.getPrice();
        }
        totalPriceLabel.setText("Precio total: " + totalPrice + "€");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
