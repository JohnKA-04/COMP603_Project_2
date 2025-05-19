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
    
    private String name;
    private int hunger, energy, happiness, cleanliness,health;
    
    public VirtualPet(String name){
        this.name = name;
        this.hunger = 50;
        this.energy = 50;
        this.happiness = 50;
        this.cleanliness = 50;
        this.health = 100;
    }
    
    public String getName(){
    return name;
}
    public int getHunger(){
        return hunger;
    }
    public int getEnergy(){
        return energy;
    }
    public int getHappiness(){
        return happiness;
    }
    public int getClean(){
        return cleanliness;
    }
    public int getHealth(){
        return health;
    }
    
    protected void decreaseHunger(int value){
        this.hunger = Math.max(0, Math.min(100, this.hunger + value));//These decrease method were created with SOME assistance from Ai.
        if (this.hunger <= 20) {
            decreaseHealth(10);
        }
    }
     protected void decreaseEnergy(int value){
         this.energy = Math.max(0, Math.min(100, this.hunger + value));
        if (energy <= 20) {
            decreaseHealth(10);
        }
    }
      protected void decreaseHappiness(int value){
         this.happiness = Math.max(0, Math.min(100, this.hunger + value));
        if (this.happiness <= 20) {
            decreaseHealth(10);
        }
    }
       protected void decreaseClean(int value){
       this.cleanliness = Math.max(0, Math.min(100, this.hunger + value));
        if (this.cleanliness <= 20) {
            System.out.println("PLEASE CLEAN ME!!!");
        }  
    }
        protected void decreaseHealth(int value){
        this.health = Math.max(0, Math.min(100, this.health - value));
         
    }
    
}
