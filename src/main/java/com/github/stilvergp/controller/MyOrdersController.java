package com.github.stilvergp.controller;

import com.github.stilvergp.App;
import com.github.stilvergp.model.Session;
import com.github.stilvergp.model.dao.OrderDAO;
import com.github.stilvergp.model.entity.Order;
import com.github.stilvergp.utils.Alerts;
import com.github.stilvergp.view.Scenes;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MyOrdersController extends Controller implements Initializable {
    @FXML
    private TableView<Order> tableView;

    @FXML
    private TableColumn<Order, String> code;

    @FXML
    private TableColumn<Order, String> address;

    @FXML
    private TableColumn<Order, LocalDate> orderDate;

    @FXML
    private TableColumn<Order, LocalDate> deliveryDate;

    @FXML
    private TableColumn<Order, Double> totalPrice;

    Order order = new Order();

    @Override
    public void onOpen(Object input) {
        reloadOrdersFromDatabase();
    }

    public Order getDoubleClickedOrder() {
        return order;
    }

    private void reloadOrdersFromDatabase() {
        ObservableList<Order> orders = FXCollections.observableArrayList(new OrderDAO().findByClient(Session.getInstance().getLoggedInClient()));
        tableView.setItems(orders);
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.setRowFactory(_ -> {
            TableRow<Order> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    this.order = tableView.getSelectionModel().getSelectedItem();
                    try {
                        App.currentController.openModal(Scenes.MYORDERPRODUCTS, "Lista de productos", this, null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
        code.setCellValueFactory(order -> new SimpleStringProperty(order.getValue().getCode()));
        address.setCellValueFactory(order -> new SimpleStringProperty(order.getValue().getAddress()));
        orderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        orderDate.setCellFactory(_ -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getDayOfMonth() + "/" + item.getMonthValue() + "/" + item.getYear());
                }
            }
        });
        deliveryDate.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        deliveryDate.setCellFactory(_ -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getDayOfMonth() + "/" + item.getMonthValue() + "/" + item.getYear());
                }
            }
        });
        totalPrice.setCellValueFactory(order -> new SimpleDoubleProperty(order.getValue().getTotalPrice()).asObject());
        totalPrice.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

    }

    public void cancelOrder() {
        Order order = tableView.getSelectionModel().getSelectedItem();
        if (order != null) {
            Alerts.showConfirmationAlert("Cancelación de pedido",
                    "Esta a punto de cancelar este pedido, " +
                            "¿Está totalmente seguro de esta acción?").showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    order.setCompleted(true);
                    new OrderDAO().cancel(order);
                    reloadOrdersFromDatabase();
                }
            });
        }
    }
}
