/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comp603_group_project_2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author johnk
 */
class PetAccesData {
    private Connection conn;

    public PetAccesData(Connection conn) {
        this.conn = conn;
    }

    public void savePet(VirtualPet pet) {
        String updateSql = "UPDATE PETS SET HUNGER = ?, HAPPINESS = ?, ENERGY = ?, HEALTH = ? WHERE NAME = ?";//for updating existing pet
        String insertSql = "INSERT INTO PETS (NAME, TYPE, HUNGER, HAPPINESS, ENERGY, HEALTH) VALUES (?, ?, ?, ?, ?, ?)";//inserting new pet
        try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            pstmt.setInt(1, pet.getHunger());
            pstmt.setInt(2, pet.getHappiness());
            pstmt.setInt(3, pet.getEnergy());
            pstmt.setInt(4, pet.getHealth());
            pstmt.setString(5, pet.getName());
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 0) {
                try (PreparedStatement insertPstmt = conn.prepareStatement(insertSql)) {
                    insertPstmt.setString(1, pet.getName());
                    insertPstmt.setString(2, pet.getClass().getSimpleName());
                    insertPstmt.setInt(3, pet.getHunger());
                    insertPstmt.setInt(4, pet.getHappiness());
                    insertPstmt.setInt(5, pet.getEnergy());
                    insertPstmt.setInt(6, pet.getHealth());
                    insertPstmt.executeUpdate();
                    System.out.println("Inserted new pet " + pet.getName() + ".");
                }
            } else {
                System.out.println("Updated pet " + pet.getName() + ".");
            }
        } catch (SQLException e) {
            System.out.println("Error");
        }
    }

    public VirtualPet loadPet(String name) {
        if (conn == null) {
            System.err.println("No DB connection for loading pet: " + name);
            return null;
        }
        String sql = "SELECT NAME, TYPE, HUNGER, HAPPINESS, ENERGY, HEALTH FROM PETS WHERE NAME = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {//We got some help from chatgpt with this try statement
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    VirtualPet pet = createPetFromResultSet(rs);
                    return pet;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error");
        }
        return null;
    }

    public List<VirtualPet> loadAllPets() {
        List<VirtualPet> petList = new ArrayList<>();
        if (conn == null) {
            System.err.println("Failed to load");
            return petList;
        }
        String sql = "SELECT NAME, TYPE, HUNGER, HAPPINESS, ENERGY, HEALTH FROM PETS";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                petList.add(createPetFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error");
        }
        return petList;
    }

    private VirtualPet createPetFromResultSet(ResultSet rs) throws SQLException {
        String petName = rs.getString("NAME");
        String type = rs.getString("TYPE");
        int hunger = rs.getInt("HUNGER");
        int happiness = rs.getInt("HAPPINESS");
        int energy = rs.getInt("ENERGY");
        int health = rs.getInt("HEALTH");

        VirtualPet pet;
        switch (type.toLowerCase()) {
            case "dog": pet = new Dog(petName);
            break;
            case "cat": pet = new Cat(petName); 
            break;
            case "hamster": pet = new Hamster(petName);
            break;
            case "turtle": pet = new Turtle(petName);
            break;
            default:
                System.err.println("Error!");
                pet = new Dog(petName);//defaulting to dog type
        }
        pet.setHunger(hunger);
        pet.setHappiness(happiness);
        pet.setEnergy(energy);
        pet.setHealth(health);
        return pet;
    }
}
