package com.dao;


import com.entity.*;
import com.exception.*;
import com.util.DBConnUtil;

import java.sql.*;
import java.util.*;

public class OrderProcessor implements IOrderManagementRepository {

    private Connection conn;

    public OrderProcessor() {
        conn = DBConnUtil.getDBConn();
    }
    public void createTable() {

        // SQL statements for creating tables
        String createProductTable = "CREATE TABLE products ("
        		+ "    productId INT PRIMARY KEY,"
        		+ "    productName VARCHAR(100) NOT NULL,"
        		+ "    description VARCHAR(255),"
        		+ "    price DOUBLE,"
        		+ "    quantityInStock INT,"
        		+ "    type VARCHAR(50),"
        		+ "    brand VARCHAR(100),"
        		+ "    warrantyPeriod INT,"
        		+ "    size VARCHAR(10),"
        		+ "    color VARCHAR(30)"
        		+ ")";
        String createUserTable = "CREATE TABLE users ("
        		+ "    userId INT PRIMARY KEY,"
        		+ "    username VARCHAR(100) NOT NULL,"
        		+ "    password VARCHAR(100) NOT NULL,"
        		+ "    role VARCHAR(50) NOT NULL"
        		+ ")";
        String createOrderTable = "CREATE TABLE orders ("
        		+ "    orderId INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,"
        		+ "    userId INT,"
        		+ "    productId INT,"
        		+ "    quantity INT,"
        		+ "    FOREIGN KEY (userId) REFERENCES users(userId),"
        		+ "    FOREIGN KEY (productId) REFERENCES products(productId)"
        		+ ")";
        // Establishing the connection and executing the SQL statements
        try (Statement statement = conn.createStatement()) {
             
            // Execute table creation statements
            statement.executeUpdate(createProductTable);
            statement.executeUpdate(createUserTable);
            statement.executeUpdate(createOrderTable);
            System.out.println("Tables created successfully.");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createUser(User user) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users VALUES (?, ?, ?, ?)");
            ps.setInt(1, user.getUserId());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.executeUpdate();
            System.out.println("User created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createProduct(User user, Product product) throws Exception {
        if (!user.getRole().equalsIgnoreCase("Admin")) {
            throw new ProductCreationException("Only admin can create products.");
        }

        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO products VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, product.getProductId());
            ps.setString(2, product.getProductName());
            ps.setString(3, product.getDescription());
            ps.setDouble(4, product.getPrice());
            ps.setInt(5, product.getQuantityInStock());
            ps.setString(6, product.getType());

            if (product instanceof Electronics) {
                Electronics e = (Electronics) product;
                ps.setString(7, e.getBrand());
                ps.setInt(8, e.getWarrantyPeriod());
                ps.setNull(9, Types.VARCHAR);
                ps.setNull(10, Types.VARCHAR);
            } else if (product instanceof Clothing) {
                Clothing c = (Clothing) product;
                ps.setNull(7, Types.VARCHAR);
                ps.setNull(8, Types.INTEGER);
                ps.setString(9, c.getSize());
                ps.setString(10, c.getColor());
            } else {
                ps.setNull(7, Types.VARCHAR);
                ps.setNull(8, Types.INTEGER);
                ps.setNull(9, Types.VARCHAR);
                ps.setNull(10, Types.VARCHAR);
            }

            ps.executeUpdate();
            System.out.println("Product created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createOrder(User user, List<Order> products) throws Exception {
        try {
            // Check if user exists
            PreparedStatement checkUser = conn.prepareStatement("SELECT * FROM users WHERE userId=?");
            checkUser.setInt(1, user.getUserId());
            ResultSet rs = checkUser.executeQuery();
            if (!rs.next()) {
                createUser(user); // if not exists, create user
            }

            for (Order p : products) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO orders (userId, productId, quantity) VALUES (?, ?, ?)");
                ps.setInt(1, user.getUserId());
                ps.setInt(2, p.getProductId());
                ps.setInt(3, p.getQuantity()); // Default quantity 1
                ps.executeUpdate();
            }

            System.out.println("Order created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancelOrder(int userId, int orderId) throws Exception {
        try {
            // Check if userId exists
            PreparedStatement checkUser = conn.prepareStatement("SELECT * FROM users WHERE userId=?");
            checkUser.setInt(1, userId);
            ResultSet userRs = checkUser.executeQuery();
            if (!userRs.next()) {
                throw new UserNotFoundException("User not found.");
            }

            // Check if orderId exists
            PreparedStatement checkOrder = conn.prepareStatement("SELECT * FROM orders WHERE orderId=?");
            checkOrder.setInt(1, orderId);
            ResultSet orderRs = checkOrder.executeQuery();
            if (!orderRs.next()) {
                throw new OrderNotFoundException("Order not found.");
            }

            // Cancel order
            PreparedStatement ps = conn.prepareStatement("DELETE FROM orders WHERE orderId=?");
            ps.setInt(1, orderId);
            ps.executeUpdate();
            System.out.println("Order cancelled successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM products");

            while (rs.next()) {
                String type = rs.getString("type");

                if (type.equalsIgnoreCase("Electronics")) {
                    Electronics e = new Electronics(
                            rs.getInt("productId"),
                            rs.getString("productName"),
                            rs.getString("description"),
                            rs.getDouble("price"),
                            rs.getInt("quantityInStock"),
                            type,
                            rs.getString("brand"),
                            rs.getInt("warrantyPeriod")
                    );
                    productList.add(e);
                } else if (type.equalsIgnoreCase("Clothing")) {
                    Clothing c = new Clothing(
                            rs.getInt("productId"),
                            rs.getString("productName"),
                            rs.getString("description"),
                            rs.getDouble("price"),
                            rs.getInt("quantityInStock"),
                            type,
                            rs.getString("size"),
                            rs.getString("color")
                    );
                    productList.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public List<Product> getOrderByUser(User user) {
        List<Product> orderList = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT p.* FROM products p JOIN orders o ON p.productId = o.productId WHERE o.userId=?");
            ps.setInt(1, user.getUserId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String type = rs.getString("type");

                if (type.equalsIgnoreCase("Electronics")) {
                    Electronics e = new Electronics(
                            rs.getInt("productId"),
                            rs.getString("productName"),
                            rs.getString("description"),
                            rs.getDouble("price"),
                            rs.getInt("quantityInStock"),
                            type,
                            rs.getString("brand"),
                            rs.getInt("warrantyPeriod")
                    );
                    orderList.add(e);
                } else if (type.equalsIgnoreCase("Clothing")) {
                    Clothing c = new Clothing(
                            rs.getInt("productId"),
                            rs.getString("productName"),
                            rs.getString("description"),
                            rs.getDouble("price"),
                            rs.getInt("quantityInStock"),
                            type,
                            rs.getString("size"),
                            rs.getString("color")
                    );
                    orderList.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }
}
