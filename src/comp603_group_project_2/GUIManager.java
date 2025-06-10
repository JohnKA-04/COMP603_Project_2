package comp603_group_project_2;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author johnk
 */

/**
 * Graphical User Interface for the Virtual Pet game.
 */
public class GUIManager extends JFrame { 
    private JTextArea statsTextArea; // box displaying stats
    private JComboBox<String> selectPet; // drop-down menu to choose an existing pet created previously
    private JTextField newPetName;
    private JComboBox<String> newPetType;
    private JButton createNewPetBtn; // button to create new pet after choosing name and type
    private JPanel actionBtnsPanel; // box holding all action buttons (organizes on screen) 
    private GameManager gameManager; // stores pets, tracks current pet, handles saving/loading data
    private ScheduledExecutorService statsDecreaser; // decreases pet stats over time 
    
    public GUIManager(GameManager manager) {
        this.gameManager = manager;
        this.statsDecreaser = Executors.newSingleThreadScheduledExecutor();
        initUI();
        startStatDecay();
        promptForPet();
    }

    private void initUI() {
        setTitle("My Virtual Pet");
        setSize(700, 480);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        statsTextArea = new JTextArea(12, 35);
        statsTextArea.setEditable(false);
        statsTextArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
        statsTextArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(new JScrollPane(statsTextArea), BorderLayout.NORTH);

        actionBtnsPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        String[] buttonLabels = {"Eat", "Play", "Sleep", "Clean", "Make Noise", "Save & Exit"};
        Map<String, ActionListener> actions = new HashMap<>();
        actions.put("Eat", e -> performPetAction(p -> p.eat(), "nom nom nom!")); //chatgpt assisted, this allow captions to print on panel when eat is initiated
        actions.put("Play", e -> performPetAction(p -> p.play(), "yay fun!")); //chatgpt assisted, this allow captions to print on panel when eat is initiated
        actions.put("Sleep", e -> performPetAction(p -> p.sleep(), "zzzzzz...")); //chatgpt assisted, this allow captions to print on panel when eat is initiated
        actions.put("Clean", e -> performPetAction(p -> p.clean(), "sparkling!")); //chatgpt assisted, this allow captions to print on panel when eat is initiated
        actions.put("Make Noise", e -> performPetAction(p -> p.makeNoise(), "woooooooooooo")); //chatgpt assisted, this allow captions to print on panel when eat is initiated
        actions.put("Save & Exit", e -> handleExit());
        for (String label : buttonLabels) {//using for loop to create and add the buttons
            JButton btn = new JButton(label);
            styleButton(btn);
            btn.addActionListener(actions.get(label));
            actionBtnsPanel.add(btn);
        }
   
        add(actionBtnsPanel, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));//bottom panel
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));//10 pixle spaces on each side
        JPanel createPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));//the panel where you create a new pet
        newPetName = new JTextField(12);
        newPetType = new JComboBox<>(new String[]{"Dog", "Cat", "Hamster", "Turtle"});
        createNewPetBtn = new JButton("Create Pet");
        styleButton(createNewPetBtn);
        createNewPetBtn.addActionListener(e -> createNewPet());
        createPanel.add(new JLabel("Name:"));
        createPanel.add(newPetName);
        createPanel.add(new JLabel("Type:"));
        createPanel.add(newPetType);
        createPanel.add(createNewPetBtn);

        JPanel selectPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        selectPet = new JComboBox<>();
        selectPet.addActionListener(e -> selectExistingPet());
        selectPanel.add(new JLabel("Or choose existing:"));
        selectPanel.add(selectPet);

        bottomPanel.add(createPanel, BorderLayout.NORTH);//adding the createPanel to north of bottom panel
        bottomPanel.add(selectPanel, BorderLayout.SOUTH);//adding the selectPanel to south of bottom panel
        add(bottomPanel, BorderLayout.SOUTH);//adding the bottompanel to south of the main window

        addWindowListener(new java.awt.event.WindowAdapter() {//adding listener so user can close window allowing handleexit to handle it
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                handleExit();
            }
        });
        setVisible(true);
        updateActionBtns(false);
    }

    private void styleButton(JButton btn) {//styling button, assisted by chatgpt
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(new Color(90, 160, 230));//uses R(90),G(160) B(230), RGB. Gives it a blue shade 
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createRaisedBevelBorder());
        btn.addChangeListener(new ChangeListener() {//when a mouse hovers over button it changes the colour of the button
            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = btn.getModel();
                if (model.isRollover()) {//if the mouse is over button it changes it to a dark blue
                    btn.setBackground(new Color(60, 130, 200));//uses RGB
                } else {//stays the same colour
                    btn.setBackground(new Color(90, 160, 230));
                }
            }
        });
    }
    private void updateActionBtns(boolean enable) {
        for (Component comp : actionBtnsPanel.getComponents()) {
            if (comp instanceof JButton) {
                comp.setEnabled(enable);
            }
        }
        ((JButton) actionBtnsPanel.getComponent(5)).setEnabled(true);
    }
    private void promptForPet() {
        List<VirtualPet> pets = gameManager.loadAllPets();
        if (!pets.isEmpty()) {
            selectPet.removeAllItems();//we remove the lsit from the dropdown menu so we dont get any duplicate names come up and then loop through the saved epet to add on the dropdown menu.
            for (VirtualPet p : pets) {
                selectPet.addItem(p.getName());
            }
            int choice = JOptionPane.showConfirmDialog(this,
                    "Saved pets is found, Load an existing pet?", "Load Game?",JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                if (selectPet.getItemCount() > 0) {
                    selectPet.setSelectedIndex(0);
                }
            } else {
                startNewGameMode();
            }
        } else {
            startNewGameMode();
        }
    }
    private void startNewGameMode() {//this is the gui for a new game so user can create new pet
        updateActionBtns(false);//keep to false so user can use the buttons yet until they make a pet
        selectPet.setEnabled(false);//false so user can make new pet
        newPetName.setEnabled(true);//true to let user type name
        newPetType.setEnabled(true);
        createNewPetBtn.setEnabled(true);
        statsTextArea.setText("Enter a name for your pet and choose an animal type!");
    }
    private void selectExistingPet() {//choosing pet from dropdown menu
        String selectedName = (String) selectPet.getSelectedItem();//getting name of chosen pet
        if (selectedName != null && gameManager.getLoadedPets().containsKey(selectedName)) {//checking if name mataches a
            gameManager.setCurrentPet(gameManager.getLoadedPets().get(selectedName));
            refreshDisplay();//this refreshed the info on the GUI
            updateActionBtns(true);
            newPetName.setText("");
            newPetName.setEnabled(false);
            newPetType.setEnabled(false);
            createNewPetBtn.setEnabled(false);
        } else {
             updateActionBtns(false);
            statsTextArea.setText("Select a pet or create a new one.");
        }
    }
    private void createNewPet() {
        String petName = newPetName.getText().trim();
        String petType = (String) newPetType.getSelectedItem();

        if (petName.isEmpty()) {//making sure that your pet has a name
            JOptionPane.showMessageDialog(this, "Please give your pet a name!", "Hey!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (gameManager.getLoadedPets().containsKey(petName)) {//making sure that your pet's name is unique from the saved pets
            JOptionPane.showMessageDialog(this, "A pet with the same name already exists. Try another name", "Oops!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        VirtualPet newPet;
        switch(petType) {//creates a pet object based on what petType the user chooses
            case "Dog":
                newPet = new Dog(petName);
                break;
            case "Cat":
                newPet = new Cat(petName);
                break;
            case "Hamster":
                newPet = new Hamster(petName);
                break;
            case "Turtle":
                newPet = new Turtle(petName);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Invalid pet type", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        }
        gameManager.addNewPet(newPet);
        refreshSelectPet();
        selectPet.setSelectedItem(petName);
        refreshDisplay();
        updateActionBtns(true);
        newPetName.setText("");
        newPetName.setEnabled(false);
        newPetType.setEnabled(false);
        createNewPetBtn.setEnabled(false);
        selectPet.setEnabled(true);
    }

    private void refreshSelectPet() {
        selectPet.removeAllItems();
        for (String name : gameManager.getLoadedPets().keySet()) {
            selectPet.addItem(name);
        }
    }

    private void performPetAction(java.util.function.Consumer<VirtualPet> action, String message) {
        VirtualPet pet = gameManager.getCurrentPet();
        if (pet != null) {
            action.accept(pet);
            gameManager.saveCurrentPet();
            refreshDisplay();
            statsTextArea.append("\n" + pet.getName() + " says: " + message);
        } else {
            JOptionPane.showMessageDialog(this, "No pet active! Create or load one.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshDisplay() {
        if (gameManager.getCurrentPet() != null) {
            statsTextArea.setText(gameManager.getCurrentPet().toString());
            if (gameManager.getCurrentPet().getHealth() < 30) {
                statsTextArea.append("\n" + gameManager.getCurrentPet().getName() + " is looking unwell...");
            } else if (gameManager.getCurrentPet().getHealth() < 60) {
                statsTextArea.append("\n" + gameManager.getCurrentPet().getName() + " could use some care.");
            }
        } else {
            statsTextArea.setText("No pet selected. Create a new pet to start playing!");
        }
    }

    private void startStatDecay() {
        statsDecreaser.scheduleAtFixedRate(() -> {
            VirtualPet pet = gameManager.getCurrentPet();
            if (pet != null) {
                SwingUtilities.invokeLater(() -> {
                    pet.updateStat("hunger", -5);
                    pet.updateStat("happiness", -3);
                    pet.updateStat("energy", -5);
                    gameManager.saveCurrentPet();

                    if (pet.getHealth() <= 0) {
                        handlePetDeath(pet);
                    }
                    refreshDisplay();
                });
            }
        }, 30, 30, TimeUnit.SECONDS);
    }

    private void handlePetDeath(VirtualPet deadPet) {
        statsDecreaser.shutdownNow();
        JOptionPane.showMessageDialog(this, deadPet.getName() + " has passed away. Game Over!", "Oh no!", JOptionPane.ERROR_MESSAGE);
        gameManager.saveCurrentPet();
        PetDB_Manager.closeConnection();
        System.exit(0);
    }

    private void handleExit() {//chatgpt assisted
        int choice = JOptionPane.showConfirmDialog(this,
                "Save game before exiting?", "Exit Game?", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            gameManager.saveCurrentPet();
            JOptionPane.showMessageDialog(this, "Game saved. Cya!", "Saved", JOptionPane.INFORMATION_MESSAGE);
            statsDecreaser.shutdown();
            PetDB_Manager.closeConnection();
            System.exit(0);
        } else if (choice == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(this, "Exiting without saving.", "BYEBYE!", JOptionPane.INFORMATION_MESSAGE);
            statsDecreaser.shutdown();
            PetDB_Manager.closeConnection();
            System.exit(0);
        }
    }
}
