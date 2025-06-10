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
    private static final String JDBC_URL = "jdbc:derby:Pet_DataBase;create=true";
    private static final String USER = "Group_2";
    private static final String PASSWORD = "JohnJacee";
    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {//we check if no connection, so we can connect to one
            try {
                conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                System.out.println("Connected to DB at " + JDBC_URL);
                createPetTable();
            } catch (SQLException e) {
                System.out.println("Error");
            }
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
                System.out.println("Derby DB shut down normally.");
                
            } catch (SQLException se) { //any errors during shutting down this will print error
                System.out.println("Error");
            } finally { //this will always run and try close any connection
                try {
                    if (conn != null) {
                        conn.close(); //closes connection
                        conn = null;//we set null as it will prevent it from being used again
                        System.out.println("DB connection closed.");
                    }
                } catch (SQLException e) {
                    System.err.println("Error closing Database");
                }
            }
        }
    }

    private static void createPetTable() {
        try (Statement stmt = conn.createStatement()) {
            DatabaseMetaData metadta = conn.getMetaData();
            ResultSet tables = metadta.getTables(null, null, "PETS", new String[]{"TABLE"});//we check for pets table to avoid creating duplicate tables
            if (!tables.next()) {
                String sql = "CREATE TABLE PETS (" +
                        "NAME VARCHAR(255) PRIMARY KEY, " +
                        "TYPE VARCHAR(255), " +
                        "HUNGER INT, " +
                        "HAPPINESS INT, " +
                        "ENERGY INT, " +
                        "HEALTH INT)";
                stmt.executeUpdate(sql);
                System.out.println("Created PETS table.");
            } else {
                System.out.println("PETS table exists");
            }
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }
}
