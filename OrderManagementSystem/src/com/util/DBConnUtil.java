package com.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {
//	 private static final String DB_URL = "jdbc:derby:/home/vishwajeet161/orderdb; create=true;";
	private static String DB_URL = DBPropertyUtil.getConnectionString("resources/db.properties");
	private static Connection con = null;
    public static Connection getDBConn() {
    	try{
        	Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        	con =  DriverManager.getConnection(DB_URL);
            System.out.println("Connection successful!");
            // You can now use the connection to execute SQL queries
        } catch ( ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}

