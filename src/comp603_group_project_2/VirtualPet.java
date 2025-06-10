/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package comp603_group_project_2;

/**
 *
 * @author johnk
 */
abstract class VirtualPet {
    private String petName;
    protected int hunger, happiness, energy, health;

    public VirtualPet(String petName) {//the initial stat values
        this.petName = petName;
        this.hunger = 50;
        this.happiness = 70;
        this.energy = 80;
        this.health = 100;
    }
    public String getName() { 
        return petName; }
    public int getHunger() { 
        return hunger; }
    public int getHappiness() { 
        return happiness; }
    public int getEnergy() { 
        return energy; }
    public int getHealth() { 
        return health; }

    public void setHunger(int hunger) { 
        this.hunger = statcap(hunger); 
    }
    public void setHappiness(int happiness) { 
        this.happiness = statcap(happiness); 
    }
    public void setEnergy(int energy) { 
        this.energy = statcap(energy); 
    }
    public void setHealth(int health) { 
        this.health = statcap(health, 0, 100); 
    }
    private int statcap(int value) {//just to make sure that 0 is lowest value and max is 100
    if (value < 0) {
        return 0; 
    }
    if (value > 100) {
        return 100; 
    }
    return value; 
}
    private int statcap(int value, int min, int max) {//make sure health stays within the range when hunger or happenis affects it
    if (value < min) {
        return min; 
    }
    if (value > max) {
        return max; 
    }
    return value; 
}
    protected void updateStat(String statName, int amount) {
        switch (statName) {
            case "hunger":
                hunger = statcap(hunger + amount);
                if (hunger <= 20 && hunger > 0) System.out.println(getName() + " needs some fooooood!");
                else if (hunger == 0) decreaseHealth(10);
                break;
            case "happiness":
                happiness = statcap(happiness + amount);
                if (happiness <= 20 && happiness > 0) System.out.println(getName() + " has depression");
                else if (happiness == 0) decreaseHealth(5);
                break;
            case "energy":
                energy = statcap(energy + amount);
                if (energy <= 20 && energy > 0) System.out.println(getName() + "'s eyes are closing.");
                else if (energy == 0) decreaseHealth(8);
                break;
            case "health":
                health = statcap(health + amount, 0, 100);
                break;
        }
    }

    protected void decreaseHealth(int amount) {
        updateStat("health", -amount);
    }
    protected void increaseHealth(int amount) {
        updateStat("health", amount);
    }

    public abstract String play();
    public abstract String eat();
    public abstract String sleep();
    public abstract String makeNoise();
    public abstract String clean();

    @Override
    public String toString() {
        return String.format("%s's Stats:\nHunger🍚: %d/100\nHappiness ✨: %d/100\nEnergy ⚡: %d/100\nHealth ❤️: %d/100",petName, hunger, happiness, energy, health);
    }
}
