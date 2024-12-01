package com.github.stilvergp.controller;

import com.github.stilvergp.App;
import com.github.stilvergp.model.Session;
import com.github.stilvergp.model.dao.ClientDAO;
import com.github.stilvergp.model.entity.Client;
import com.github.stilvergp.utils.Alerts;
import com.github.stilvergp.utils.Security;
import com.github.stilvergp.view.Scenes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Controller implements Initializable {
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    /**
     * Logs in the client using the session instance.
     *
     * @param client the client to be logged in.
     */
    public void login(Client client) {
        Session.getInstance().login(client);
    }

    @Override
    public void onOpen(Object input) {

    }

    @Override
    public void onClose(Object output) {

    }

    /**
     * Navigates to the main scene if the client credentials are valid.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void goToMain() throws IOException {
        Client client = new ClientDAO().findByUsername(username.getText());
        if (client != null && client.isMyPassword(Security.hashPassword(password.getText()))) {
            login(client);
            App.currentController.changeScene(Scenes.MAIN, null);
        } else {
            Alerts.showErrorAlert("Error de inicio de sesión",
                    "Usuario o contraseña incorrectos, " +
                            "por favor intente nuevamente");
        }
    }

    /**
     * Opens the sign-in form to add a new client.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void signIn() throws IOException {
        username.clear();
        password.clear();
        App.currentController.openModal(Scenes.FORMSIGNIN, "Agregando usuario...", this, null);
    }

    /**
     * Saves a new client and adds it to the database.
     *
     * @param client the client to be saved.
     */
    public void saveClient(Client client) {
        ClientDAO clientDAO = new ClientDAO();
        clientDAO.add(client);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
