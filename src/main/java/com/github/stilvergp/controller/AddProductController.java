package com.github.stilvergp.controller;

import com.github.stilvergp.model.dao.ProductDAO;
import com.github.stilvergp.model.entity.Product;
import com.github.stilvergp.utils.Alerts;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController extends Controller implements Initializable {

    @FXML
    private TextField code;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    @FXML
    private TextField stock;


    private BufferedImage roomImage;

    private MainController controller;

    @Override
    public void onOpen(Object input) {
        this.roomImage = null;
        this.controller = (MainController) input;
    }

    @Override
    public void onClose(Object output) {

    }

    public void addProduct(Event event) {
        if (areFieldsEmpty()) {
            Alerts.showErrorAlert("Error al añadir el producto", "debe rellenar todos los campos");
        } else if (code.getText().length() != 8) {
            Alerts.showErrorAlert("Error al añadir el producto", "el código debe tener una longitud de 8 caracteres");
        } else if (roomImage == null) {
            Alerts.showErrorAlert("Error al añadir el producto", "debe añadir una imagen al producto");
        } else {
            ProductDAO productDAO = new ProductDAO();
            Product productExists = productDAO.findById(code.getText());
            if (productExists == null) {
                Product product = new Product();
                product.setCode(code.getText());
                product.setImage(roomImage);
                product.setName(name.getText());
                product.setPrice(Double.parseDouble(price.getText()));
                product.setStock(Integer.parseInt(stock.getText()));
                saveAndCloseWindow(product, event);
            } else {
                Alerts.showErrorAlert("Error al añadir el producto", "El producto ya existe");
            }
        }
    }

    private boolean areFieldsEmpty() {
        return code.getText().trim().isEmpty() || name.getText().trim().isEmpty() || price.getText().trim().isEmpty() || stock.getText().trim().isEmpty();
    }

    public void getImage(Event event) throws IOException {
        Window window = ((Node) (event.getSource())).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona una imagen...");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Imágen", "*.jpg", "*.png", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(window);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            roomImage = ImageIO.read(file);
            imageView.setImage(image);
        }
        imageView.setFitHeight(85);
        imageView.setFitWidth(85);
    }

    public void saveAndCloseWindow(Product product, Event event) {
        this.controller.saveProduct(product);
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
