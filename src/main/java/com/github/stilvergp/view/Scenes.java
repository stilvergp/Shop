package com.github.stilvergp.view;

public enum Scenes {
    ROOT("view/layout.fxml"),
    MAIN("view/main.fxml"),
    LOGIN("view/login.fxml"),
    FORMSIGNIN("view/formSignIn.fxml"),
    MYORDERS("view/myOrders.fxml"),
    MYORDERPRODUCTS("view/myOrderProducts.fxml"),
    BUYPRODUCTS("view/buyProducts.fxml"),
    ADDPRODUCT("view/addProduct.fxml"),
    DELETEPRODUCT("view/deleteProduct.fxml");

    private String url;

    Scenes(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
