/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comp603_group_project_2;
/**
 *
 * @author johnk
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

class GUIManager {
    private JFrame frame;
    private JTextArea displayArea;
    private JButton eatButton, playButton, sleepButton, makeNoiseButton, exitButton, cleanButton;
    private JTextField nameField;
    private JComboBox<String> petTypeComboBox;
    private JButton createPetButton;
    private JPanel controlPanel;
    private JPanel createPetPanel;
    private JPanel mainPanel;
    private JComboBox<String> existingPetsComboBox; //this for drop down menu of saved pets

    private PetCare petCare;
    private GameStatManager gameStateManager;
    private ScheduledExecutorService scheduler;

    public GUIManager(PetCare petCare, GameStatManager gameStateManager) {
        this.petCare = petCare;
        this.gameStateManager = gameStateManager;
        this.scheduler = Executors.newScheduledThreadPool(1);
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Virtual Pet Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 450);
        frame.setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 

        displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Improve readability
        mainPanel.add(new JScrollPane(displayArea), BorderLayout.NORTH);

        controlPanel = new JPanel(new GridLayout(2, 3, 10, 10)); // Added gaps
        createPetPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Added gaps

        eatButton = new JButton("Eat");
        playButton = new JButton("Play");
        cleanButton = new JButton("Clean");
        sleepButton = new JButton("Sleep");
        makeNoiseButton = new JButton("Make Noise");
        exitButton = new JButton("Exit");

        // Styling buttons
        JButton[] actionButtons = {eatButton, playButton, cleanButton, sleepButton, makeNoiseButton, exitButton};
        for (JButton btn : actionButtons) {
            btn.setFont(new Font("Arial", Font.BOLD, 12));
            btn.setBackground(new Color(100, 180, 250)); // Light blue
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(new Color(50, 150, 200), 2));
            btn.setRolloverEnabled(true);
            btn.addActionListener(e -> {
                // Simple hover effect for all buttons
                btn.setBackground(new Color(70, 150, 220)); // Darker on hover
            });
            btn.addChangeListener(e -> {
                ButtonModel model = btn.getModel();
                if (model.isRollover()) {
                    btn.setBackground(new Color(70, 150, 220));
                } else {
                    btn.setBackground(new Color(100, 180, 250));
                }
            });
        }


        nameField = new JTextField(15);
        petTypeComboBox = new JComboBox<>(new String[]{"Dog", "Cat"}); // Capitalized for display consistency
        createPetButton = new JButton("Create New Pet"); // Changed button text

        // Add new JComboBox for existing pets
        existingPetsComboBox = new JComboBox<>();
        existingPetsComboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        existingPetsComboBox.addActionListener(e -> {
            String selectedPetName = (String) existingPetsComboBox.getSelectedItem();
            if (selectedPetName != null && gameStateManager.getPetMap().containsKey(selectedPetName)) {
                gameStateManager.setCurrentPet(gameStateManager.getPetMap().get(selectedPetName));
                updateDisplay();
                enablePetInteractionButtons(true);
                toggleCreatePetControls(false); // Disable create controls when selecting an existing pet
            } else {
                enablePetInteractionButtons(false);
                // Optionally re-enable create controls if no pet is selected or current pet is null
                // toggleCreatePetControls(true);
            }
        });


        controlPanel.add(eatButton);
        controlPanel.add(playButton);
        controlPanel.add(cleanButton);
        controlPanel.add(sleepButton);
        controlPanel.add(makeNoiseButton);
        controlPanel.add(exitButton);

        createPetPanel.add(new JLabel("Pet Name:"));
        createPetPanel.add(nameField);
        createPetPanel.add(new JLabel("Type:"));
        createPetPanel.add(petTypeComboBox);
        createPetPanel.add(createPetButton);
        
        JPanel petSelectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        petSelectionPanel.add(new JLabel("Select Pet:"));
        petSelectionPanel.add(existingPetsComboBox);


        mainPanel.add(controlPanel, BorderLayout.CENTER);
        JPanel southPanel = new JPanel(new BorderLayout()); // Container for create and select
        southPanel.add(createPetPanel, BorderLayout.NORTH);
        southPanel.add(petSelectionPanel, BorderLayout.SOUTH); // Add selection below creation
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);

        setButtonActions();
        startStatDecreaseTimer();
        
        // Initial game flow: prompt user
        promptInitialGameChoice();
    }

    /**
     * Enables or disables interaction buttons based on whether a pet is active.
     */
    private void enablePetInteractionButtons(boolean enable) {
        eatButton.setEnabled(enable);
        playButton.setEnabled(enable);
        cleanButton.setEnabled(enable);
        sleepButton.setEnabled(enable);
        makeNoiseButton.setEnabled(enable);
        // exitButton always enabled
    }

    /**
     * Toggles the visibility/usability of the pet creation controls.
     */
    private void toggleCreatePetControls(boolean enable) {
        nameField.setEnabled(enable);
        petTypeComboBox.setEnabled(enable);
        createPetButton.setEnabled(enable);
    }


    /**
     * Sets the action listeners for the GUI buttons.
     */
    private void setButtonActions() {
        eatButton.addActionListener(e -> performPetAction(p -> petCare.feedPet(p), gameStateManager.getCurrentPet().getName() + " has eaten something delicious!"));
    playButton.addActionListener(e -> performPetAction(p -> petCare.playWithPet(p), gameStateManager.getCurrentPet().getName() + " had fun playing!"));
    cleanButton.addActionListener(e -> performPetAction(p -> petCare.cleanPet(p), gameStateManager.getCurrentPet().getName() + " is now clean and happy!"));
    sleepButton.addActionListener(e -> performPetAction(p -> petCare.letPetSleep(p), gameStateManager.getCurrentPet().getName() + " feels rested now!"));
    makeNoiseButton.addActionListener(e -> performPetAction(p -> petCare.petMakesNoise(p), gameStateManager.getCurrentPet().getName() + " made a noise!"));

        exitButton.addActionListener(e -> {
            int saveChoice = JOptionPane.showConfirmDialog(frame,
                "Do you want to save your current pet's progress before exiting?",
                "Save Game?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

            if (saveChoice == JOptionPane.YES_OPTION) {
                gameStateManager.savePet(); // Save if user confirms
                JOptionPane.showMessageDialog(frame, "Game saved!", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Game not saved.", "Save Skipped", JOptionPane.INFORMATION_MESSAGE);
            }
            
            gameStateManager.setRunning(false);
            scheduler.shutdown();
            DatabaseManager.closeConnection(); // Ensure connection is closed on exit
            System.exit(0);
        });

        createPetButton.addActionListener(e -> {
            String name = nameField.getText().trim(); // Trim whitespace
            String type = (String) petTypeComboBox.getSelectedItem();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a name for your pet.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (gameStateManager.getPetMap().containsKey(name)) {
                JOptionPane.showMessageDialog(frame, "A pet with this name already exists. Please choose a different name or select the existing pet from the dropdown.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            VirtualPet newPet;
            if ("Dog".equals(type)) { // Match with capitalized name in JComboBox
                newPet = new Dog(name);
            } else if ("Cat".equals(type)) { // Match with capitalized name in JComboBox
                newPet = new Cat(name);
            } else {
                newPet = new Dog(name); // Default, though typeComboBox should prevent this
            }
            
            gameStateManager.addPet(newPet); // Adds to map and sets as current
            gameStateManager.savePet(); // Save the new pet to DB
            updatePetComboBox(); // Update dropdown with new pet
            existingPetsComboBox.setSelectedItem(name); // Select the new pet in the dropdown
            updateDisplay();
            enablePetInteractionButtons(true); // Enable interaction buttons
            toggleCreatePetControls(false); // Disable create pet controls after creation
            nameField.setText(""); // Clear name field
        });
    }

    /**
     * Helper method to perform pet actions and update GUI.
     * @param action The Consumer action to apply to the current pet.
     */
    private void performPetAction(Consumer<VirtualPet> action, String caption) {
    VirtualPet pet = gameStateManager.getCurrentPet();
    if (pet != null) {
        action.accept(pet);
        gameStateManager.savePet();
        updateDisplay();
        displayArea.append("\n" + caption);  // Show the caption message
    } else {
        JOptionPane.showMessageDialog(frame, "No pet selected. Please create or load a pet.", "No Pet", JOptionPane.WARNING_MESSAGE);
    }
}

    /**
     * Updates the display area with the current pet's stats.
     */
    private void updateDisplay() {
        petCare.displayPetStats(gameStateManager.getCurrentPet(), displayArea);
    }

    /**
     * Starts a timer that periodically decreases the pet's stats.
     */
    private void startStatDecreaseTimer() {
        scheduler.scheduleAtFixedRate(() -> { //lets us automatically decrease the stats
            if (gameStateManager.isRunning() && gameStateManager.getCurrentPet() != null) {
                SwingUtilities.invokeLater(() -> { //This part was chatgpt assisted, it basically allows us to safely run GUI updates on the EDDT
                    VirtualPet currentPet = gameStateManager.getCurrentPet();
                    currentPet.adjustHunger(-5);
                    currentPet.adjustHappiness(-5);
                    currentPet.adjustEnergy(-5);
                    gameStateManager.savePet();// Save the updated stats to the database
                    updateDisplay();
                    if (currentPet.getHealth() <= 0) {
                        handlePetDeath(currentPet);
                    }
                });
            }
        }, 0, 30, TimeUnit.SECONDS); // Every 10 seconds
    }

    /**
     * Handles the death of the pet. Displays a message and exits the game.
     * @param deadPet The pet that has died.
     */
    private void handlePetDeath(VirtualPet deadPet) {
        scheduler.shutdownNow(); // Stop the timer immediately

        int saveChoice = JOptionPane.showConfirmDialog(frame,
            deadPet.getName() + " has died! Game Over. Do you want to save the final state of your pet?",
            "Pet Died - Save Game?",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (saveChoice == JOptionPane.YES_OPTION) {
            gameStateManager.savePet(); // Save final state of dead pet
            JOptionPane.showMessageDialog(frame, "Final pet state saved!", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Final pet state not saved.", "Save Skipped", JOptionPane.INFORMATION_MESSAGE);
        }

        gameStateManager.setRunning(false);
        DatabaseManager.closeConnection(); // Ensure connection is closed
        System.exit(0);
    }

    /**
     * Prompts the user at game startup to choose between loading a saved game or creating a new one.
     */
    private void promptInitialGameChoice() {
        java.util.List<VirtualPet> allPets = gameStateManager.loadAllPets(); // Load all pets first
        existingPetsComboBox.removeAllItems(); // Clear existing combo box items

        if (!allPets.isEmpty()) {
            // Populate combo box with existing pets
            for (VirtualPet pet : allPets) {
                existingPetsComboBox.addItem(pet.getName());
            }

            // Ask user if they want to load an existing game
            int loadChoice = JOptionPane.showConfirmDialog(frame,
                "Saved pets found. Do you want to load an existing pet?",
                "Load Game?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

            if (loadChoice == JOptionPane.YES_OPTION) {
                // User wants to load, prompt for pet name (via input dialog with dropdown)
                // Convert List of pet names to an array for JOptionPane input dialog options
                String[] petNamesArray = allPets.stream().map(VirtualPet::getName).toArray(String[]::new);

                String petNameToLoad = (String) JOptionPane.showInputDialog(
                    frame,
                    "Please select or type the name of the pet to load:",
                    "Load Pet",
                    JOptionPane.QUESTION_MESSAGE,
                    null, // icon
                    petNamesArray, // selectable options from loaded pets
                    existingPetsComboBox.getSelectedItem() // initial selection (if any)
                );

                if (petNameToLoad != null && !petNameToLoad.isEmpty()) {
                    VirtualPet loadedPet = gameStateManager.getPetMap().get(petNameToLoad);
                    if (loadedPet != null) {
                        gameStateManager.setCurrentPet(loadedPet);
                        existingPetsComboBox.setSelectedItem(petNameToLoad);
                        updateDisplay();
                        enablePetInteractionButtons(true);
                        toggleCreatePetControls(false); // Disable create controls as pet is loaded
                    } else {
                        // Pet name not found from input, fall back to creating new
                        JOptionPane.showMessageDialog(frame, "Pet '" + petNameToLoad + "' not found. Starting a new game.", "Pet Not Found", JOptionPane.WARNING_MESSAGE);
                        startNewGameFlow();
                    }
                } else {
                    // User cancelled input dialog or entered empty, fall back to creating new
                    JOptionPane.showMessageDialog(frame, "No pet selected. Starting a new game.", "New Game", JOptionPane.INFORMATION_MESSAGE);
                    startNewGameFlow();
                }
            } else {
                // User chose not to load, start new game flow
                startNewGameFlow();
            }
        } else {
            // No saved pets at all, directly prompt to create new
            JOptionPane.showMessageDialog(frame, "No saved pets found. Please create a new pet to begin.", "New Game", JOptionPane.INFORMATION_MESSAGE);
            startNewGameFlow();
        }
    }

    /**
     * Sets the GUI state for creating a new game/pet.
     */
    private void startNewGameFlow() {
        toggleCreatePetControls(true); // Enable create controls
        enablePetInteractionButtons(false); // Disable interaction buttons until pet is created
        displayArea.setText("Welcome! To start, please create your first pet.");
    }

    /**
     * Updates the existing pets combo box with pets from gameStateManager's map.
     */
    private void updatePetComboBox() {
        existingPetsComboBox.removeAllItems();
        gameStateManager.getPetMap().keySet().forEach(existingPetsComboBox::addItem);
    }
}
