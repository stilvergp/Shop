CREATE DATABASE if not exists Shop;
USE Shop;

CREATE TABLE if not exists Client (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    isAdmin BOOLEAN
);

CREATE TABLE if not exists `Order` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    client_id INT NOT NULL,
    address VARCHAR(255) NOT NULL,
    orderDate DATE NOT NULL,
    deliveryDate DATE NOT NULL,
    isCompleted BOOLEAN,
    totalPrice DECIMAL(5, 2),
    FOREIGN KEY (client_id) REFERENCES Client(id) ON DELETE CASCADE
);

CREATE TABLE if not exists Product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    image LONGBLOB,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL
);

CREATE TABLE if not exists Order_Product (
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES `Order`(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Product(id) ON DELETE CASCADE
);