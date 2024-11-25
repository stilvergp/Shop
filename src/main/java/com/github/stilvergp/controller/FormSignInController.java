package com.github.stilvergp.controller;

import com.github.stilvergp.model.dao.ClientDAO;
import com.github.stilvergp.model.entity.Client;
import com.github.stilvergp.utils.Alerts;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FormSignInController extends Controller implements Initializable {

    @FXML
    private TextField name;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private TextField email;

    @FXML
    private TextField telephone;


    private LoginController controller;

    @Override
    public void onOpen(Object input) {
        this.controller = (LoginController) input;
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void addUser(Event event) {
        Client clientExists = new ClientDAO().findByUsername(username.getText());
        if (clientExists == null) {
            if (password.getText().equals(confirmPassword.getText())) {
                Client client = new Client(name.getText(), username.getText(), password.getText(), email.getText(), telephone.getText());
                this.controller.saveClient(client);
                ((Node) (event.getSource())).getScene().getWindow().hide();
            } else {
                Alerts.showErrorAlert("Error al crear el usuario", "Las contraseñas no coinciden");
            }
        } else {
            Alerts.showErrorAlert("Error al crear el usuario", "El usuario ya existe");
        }
    }
}
