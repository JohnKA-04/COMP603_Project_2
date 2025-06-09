/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comp603_group_project_2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author johnk
 */
class GameManager {
    private VirtualPet currentPet;
    private Map<String, VirtualPet> loadedPets = new HashMap<>();
    private PetAccesData petDAO;

    public GameManager(PetAccesData petDAO) {
        this.petDAO = petDAO;
    }
    public VirtualPet getCurrentPet() { return currentPet; }

    public void setCurrentPet(VirtualPet pet) {
        this.currentPet = pet;
        if (pet != null) {
            loadedPets.put(pet.getName(), pet);
        }
    }
    public Map<String, VirtualPet> getLoadedPets() { return loadedPets; }

    public void saveCurrentPet() {
        if (currentPet != null) {
            petDAO.savePet(currentPet);
        }
    }
    public VirtualPet loadPet(String name) {
        VirtualPet pet = petDAO.loadPet(name);
        if (pet != null) {
            setCurrentPet(pet);
        }
        return pet;
    }
    public List<VirtualPet> loadAllPets() {
        List<VirtualPet> allPets = petDAO.loadAllPets();
        loadedPets.clear();
        for (VirtualPet pet : allPets) {
            loadedPets.put(pet.getName(), pet);
        }
        return allPets;
    }

    public void addNewPet(VirtualPet pet) {
        loadedPets.put(pet.getName(), pet);
        setCurrentPet(pet);
        petDAO.savePet(pet);
    }
}
