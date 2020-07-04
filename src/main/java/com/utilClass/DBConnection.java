package com.utilClass;

import java.sql.*;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost/jdbctestdb";
    private static final String USER = "java";
    private static final String PASSWORD = "ZAQ!2wsx";

    public static Connection createConnection() {

        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e){
            System.out.println("ClassNotFoundException:" + e.getMessage());
        } catch (SQLException e){
            System.out.println("SQLException:" + e.getMessage());
        } catch (Exception e){
            System.out.println("Exception:" + e.getMessage());
        }

        return conn;
    }

}