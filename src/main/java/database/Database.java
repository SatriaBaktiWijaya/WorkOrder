/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author acer
 */
public class Database implements IDatabase {

    private static final String URL = "jdbc:mysql://localhost:3306/db_work_order?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Connection connection;

    @Override
    public java.sql.Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC MySQL tidak ditemukan: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Gagal koneksi ke database: " + e.getMessage());
        }
        return connection;
    }

    @Override
    public void Connection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Gagal menutup koneksi: " + e.getMessage());
        }
    }

}
