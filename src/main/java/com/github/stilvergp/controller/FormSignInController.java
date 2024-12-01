package com.github.stilvergp.controller;

import com.github.stilvergp.model.dao.ClientDAO;
import com.github.stilvergp.model.entity.Client;
import com.github.stilvergp.utils.Alerts;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            if (isValidEmail(email.getText())) {
                if (isValidPhone(telephone.getText())) {
                    if (isValidPassword(password.getText())) {
                        if (password.getText().equals(confirmPassword.getText())) {
                            Client client = new Client(name.getText(), username.getText(), password.getText(), email.getText(), telephone.getText());
                            this.controller.saveClient(client);
                            ((Node) (event.getSource())).getScene().getWindow().hide();
                        } else {
                            Alerts.showErrorAlert("Error al crear el usuario", "Las contraseñas no coinciden");
                        }
                    } else {
                        Alerts.showErrorAlert("Error al crear el usuario",
                                "Formato de la contraseña incorrecto. La contraseña debe contener:",
                                "Al menos una mayúscula \n" +
                                        "Al menos un número \n" +
                                        "Al menos un carácter especial \n" +
                                        "Longitud mínima de 8 caracteres.");
                    }
                } else {
                    Alerts.showErrorAlert("Error al crear el usuario", "El número de teléfono debe tener una longitud de 9 dígitos");
                }
            } else {
                Alerts.showErrorAlert("Error al crear el usuario", "Formato del email incorrecto. (email@example.com)");
            }
        } else {
            Alerts.showErrorAlert("Error al crear el usuario", "El usuario ya existe");
        }
    }

    private boolean isValidEmail(String email) {
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean isValidPhone(String telephone) {
        String regex = "^[0-9]{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(telephone);
        return matcher.matches();
    }
}
