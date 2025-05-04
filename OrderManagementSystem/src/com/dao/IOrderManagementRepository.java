package com.dao;

import com.entity.Order;
import com.entity.Product;
import com.entity.User;
import java.util.List;

public interface IOrderManagementRepository {
    void createUser(User user);
    void createProduct(User user, Product product) throws Exception;
    void createOrder(User user, List<Order> products) throws Exception;
    void cancelOrder(int userId, int orderId) throws Exception;
    List<Product> getAllProducts();
    List<Product> getOrderByUser(User user);
}

