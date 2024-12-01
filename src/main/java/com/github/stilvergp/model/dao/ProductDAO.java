package com.github.stilvergp.model.dao;

import com.github.stilvergp.model.connection.MySQLConnection;
import com.github.stilvergp.model.entity.Product;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static final String INSERT = "INSERT INTO Product(code, image, name, price, stock) VALUES(?,?,?,?,?)";
    private static final String UPDATEPRICE = "UPDATE Product SET price=? WHERE code=?";
    private static final String UPDATESTOCK = "UPDATE Product SET stock=? WHERE code=?";
    private static final String FINDALL = "SELECT * FROM Product WHERE stock > 0";
    private static final String FINDBYID = "SELECT * from Product WHERE code = ?";
    private static final String FINDPRODUCTSBYORDER = "SELECT p.id, p.code,p.image, p.name, p.price, p.stock " +
            "FROM Product p INNER JOIN Order_Product op ON p.id = op.product_id WHERE op.order_id = ?";
    private static final String DELETE = "DELETE FROM Product WHERE code = ?";
    private final Connection conn;

    public ProductDAO() {
        conn = MySQLConnection.getConnection();
    }

    public void add(Product product) {
        if (product != null) {
            String code = product.getCode();
            if (code != null) {
                Product isInDatabase = findById(code);
                if (isInDatabase == null) {
                    try (PreparedStatement pst = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                        pst.setString(1, product.getCode());
                        pst.setBinaryStream(2, imageToStream(product.getImage()));
                        pst.setString(3, product.getName());
                        pst.setDouble(4, product.getPrice());
                        pst.setInt(5, product.getStock());
                        pst.executeUpdate();
                        try (ResultSet rs = pst.getGeneratedKeys()) {
                            if (rs.next()) {
                                product.setId(rs.getInt(1));
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void updatePrice(Product product) {
        if (product != null) {
            String code = product.getCode();
            if (code != null) {
                Product isInDatabase = findById(code);
                if (isInDatabase == null) {
                    try (PreparedStatement pst = conn.prepareStatement(UPDATEPRICE)) {
                        pst.setDouble(1, product.getPrice());
                        pst.setString(2, product.getCode());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void updateStock(Product product) {
        if (product != null) {
            String code = product.getCode();
            if (code != null) {
                Product isInDatabase = findById(code);
                if (isInDatabase == null) {
                    try (PreparedStatement pst = conn.prepareStatement(UPDATESTOCK)) {
                        pst.setInt(1, product.getStock());
                        pst.setString(2, product.getCode());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public List<Product> findAllAvailable() {
        List<Product> products = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(FINDALL)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setCode(rs.getString("code"));
                    Blob blob = rs.getBlob("image");
                    if (blob != null) {
                        InputStream inputStream = blob.getBinaryStream();
                        BufferedImage image = ImageIO.read(inputStream);
                        product.setImage(image);
                    }
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));
                    product.setStock(rs.getInt("stock"));
                    products.add(product);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product findById(String id) {
        Product result = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYID)) {
            pst.setString(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setCode(rs.getString("code"));
                    Blob blob = rs.getBlob("image");
                    if (blob != null) {
                        InputStream inputStream = blob.getBinaryStream();
                        BufferedImage image = ImageIO.read(inputStream);
                        product.setImage(image);
                    }
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));
                    product.setStock(rs.getInt("stock"));
                    result = product;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Product> findByOrder(int order_id) {
        List<Product> products = new ArrayList<>();
        if (order_id > 0) {
            try (PreparedStatement pst = conn.prepareStatement(FINDPRODUCTSBYORDER)) {
                pst.setInt(1, order_id);
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        Product product = new Product();
                        product.setId(rs.getInt("id"));
                        product.setCode(rs.getString("code"));
                        Blob blob = rs.getBlob("image");
                        if (blob != null) {
                            InputStream inputStream = blob.getBinaryStream();
                            BufferedImage image = ImageIO.read(inputStream);
                            product.setImage(image);
                        }
                        product.setName(rs.getString("name"));
                        product.setPrice(rs.getDouble("price"));
                        product.setStock(rs.getInt("stock"));
                        products.add(product);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return products;
    }

    public void delete(Product product) {
        if (product != null) {
            try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
                pst.setString(1, product.getCode());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public InputStream imageToStream(BufferedImage image) {
        ByteArrayInputStream bais = null;
        if (image != null) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                bais = new ByteArrayInputStream(baos.toByteArray());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return bais;
    }

}
