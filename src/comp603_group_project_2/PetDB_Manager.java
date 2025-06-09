/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comp603_group_project_2;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author johnk
 */
public class PetDB_Manager {
    private static final String JDBC_URL = "jdbc:derby:Pet;create=true";
    private static final String USER = "John";
    private static final String PASSWORD = "Jacee";
    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                System.out.println("Connected to DB at " + JDBC_URL);
                createPetTable();
            } catch (SQLException e) {
                if ("XJ040".equals(e.getSQLState()) || "XSDB6".equals(e.getSQLState())) {
                    System.err.println("Database already booted or in use. Ensure no other instances are running.");
                    JOptionPane.showMessageDialog(null, "Database is in use or failed to start. Please close conflicting apps.", "Database Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    System.err.println("Database connection error: " + e.getMessage());
                    JOptionPane.showMessageDialog(null, "DB connection error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
                conn = null;
            }
        }
        return conn;
    }

    
}
