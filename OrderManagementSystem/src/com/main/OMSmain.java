package com.main;

import com.dao.OrderProcessor;
import com.entity.User;
import com.entity.Product;
import com.entity.Electronics;
import com.entity.Order;
import com.entity.Clothing;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OMSmain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        OrderProcessor processor = new OrderProcessor();

        while (true) {
            System.out.println("\n*************************************************");
            System.out.println("            Order Management System");
            System.out.println("**************************************************\n");
            System.out.println("\n1. Create User\n2. Create Product\n3. Create Order\n4. Cancel Order\n5. Get All Products\n6. Get Orders by User\n7. Exit\nEnter Choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    // Create User
                    System.out.print("Enter Username: ");
                    String username = sc.nextLine();
                    System.out.print("Enter Password: ");
                    String password = sc.nextLine();
                    System.out.print("Enter Role (admin/customer): ");
                    String role = sc.nextLine();
                    User user = new User(0, username, password, role);
                    processor.createUser(user);
                    break;

                case 2:
                    // Create Product
                	System.out.print("Enter Username: ");
                    String username1 = sc.nextLine();
                    System.out.print("Enter Password: ");
                    String password1 = sc.nextLine();
                    System.out.print("Enter Role (admin/customer): ");
                    String role1 = sc.nextLine();
                    User user1 = new User(0, username1, password1, role1);
                	sc.nextLine();
                    System.out.print("Enter Product Type (Electronics/Clothing): ");
                    String type = sc.nextLine().toLowerCase();
                    
                    System.out.print("Enter Product Name: ");
                    String productName = sc.nextLine();
                    System.out.print("Enter Description: ");
                    String description = sc.nextLine();
                    System.out.print("Enter Price: ");
                    double price = sc.nextDouble();
                    System.out.print("Enter Quantity in Stock: ");
                    int quantity = sc.nextInt();
                    sc.nextLine(); // consume newline

                    if (type.equals("electronics")) {
                        System.out.print("Enter Brand: ");
                        String brand = sc.nextLine();
                        System.out.print("Enter Warranty Period (months): ");
                        int warranty = sc.nextInt();
                        Electronics electronics = new Electronics(0, productName, description, price, quantity, "Electronics", brand, warranty);
                        try {
							processor.createProduct(user1,electronics);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    } else if (type.equals("clothing")) {
                        System.out.print("Enter Size: ");
                        String size = sc.nextLine();
                        System.out.print("Enter Color: ");
                        String color = sc.nextLine();
                        Clothing clothing = new Clothing(0, productName, description, price, quantity, "Clothing", size, color);
                        try {
							processor.createProduct(user1, clothing);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    } else {
                        System.out.println("Invalid Product Type! Only Electronics or Clothing allowed.");
                    }
                    break;

                case 3: // Create Order
                    sc.nextLine(); // consume leftover newline
                    System.out.print("Enter UserId: ");
                    int userId2 = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Username: ");
                    String username2 = sc.nextLine();
                    
                    System.out.print("Enter Password: ");
                    String password2 = sc.nextLine();
                    
                    System.out.print("Enter Role: ");
                    String role2 = sc.nextLine();
                    
                    User user2 = new User();
                    user2.setUserId(userId2);
                    user2.setUsername(username2);
                    user2.setPassword(password2);
                    user2.setRole(role2);

                    List<Order> productList = new ArrayList<>();
                    int cnt=0;
                    while (true) {
                        System.out.print("Enter Product ID to add to order (or -1 to finish): ");
                        int productId = sc.nextInt();
                        if (productId == -1) {
                            break;
                        }else {
                        	cnt++;
                        }

                        Order orders = new Order();
                        orders.setProductId(productId);
                        orders.setQuantity(cnt);
                        productList.add(orders);
                    }

				try {
					processor.createOrder(user2, productList);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    break;

                case 4:
                    // Cancel Order
                	System.out.print("Enter User ID to cancel: ");
                	int userId = sc.nextInt();
                    System.out.print("Enter Order ID to cancel: ");
                    int orderId = sc.nextInt();
				try {
					processor.cancelOrder(userId,orderId);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                    break;

                case 5:
                    // Get All Products
                    List<Product> prod = processor.getAllProducts();
                    if (prod != null && !prod.isEmpty()) {
                        for (Product product : prod) {
                            System.out.println(product); // This will call the toString() method of Product class
                        }
                    } else {
                        System.out.println("No Orders found.");
                    }
                    break;

                case 6:
                    // Get Orders by User
                	System.out.print("Enter Username: ");
                    String username3 = sc.nextLine();
                    System.out.print("Enter Password: ");
                    String password3 = sc.nextLine();
                    System.out.print("Enter Role (admin/customer): ");
                    String role3 = sc.nextLine();
                    User user3 = new User(0, username3, password3, role3);
                	sc.nextLine();
                    System.out.print("Enter User ID to view orders: ");
                    int uid = sc.nextInt();
                    List<Product> arr = processor.getOrderByUser(user3);
                    if (arr != null && !arr.isEmpty()) {
                        for (Product product : arr) {
                            System.out.println(product); // This will call the toString() method of Product class
                        }
                    } else {
                        System.out.println("No products found.");
                    }
                    break;

                case 7:
                    System.out.println("Exiting... Thank you!");
                    System.exit(0);
                
                case 8:
                	processor.createTable();
                	break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
