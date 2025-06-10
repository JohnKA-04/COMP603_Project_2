/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comp603_group_project_2;
import java.sql.Connection;
import javax.swing.SwingUtilities;
/**
 *
 * @author johnk
 */
public class Main {
    public static void main(String[] args) {
        Connection dbConn = PetDB_Manager.getConnection();
        if (dbConn == null) {
            System.err.println("No database connection. Exiting game.");
            return;
        }

        SwingUtilities.invokeLater(() -> {//chatgpt assisted code
            PetAccesData petAD = new PetAccesData(dbConn);
            GameManager gameManager = new GameManager(petAD);
            new GUIManager(gameManager);
        });
    }
}