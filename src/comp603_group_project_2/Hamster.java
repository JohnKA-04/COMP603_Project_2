/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comp603_group_project_2;

/**
 *
 * @author johnk
 */
public class Hamster extends VirtualPet {
    public Hamster(String name) {
        super(name);
    }
    
    @Override
    public void play() {
        System.out.println(getName() + " goes for a run in their hamster ball.");
        updateStat("happiness", 30);
        updateStat("energy", -20);
        updateStat("hunger", -15);
        increaseHealth(5);
    }
    
    @Override
    public void eat() {
        System.out.println(getName() + " nibbles on some hamster pellets.");
        updateStat("energy", 10);
        updateStat("hunger", 20);
        increaseHealth(15);
    }
    
    @Override
    public void sleep() {
        System.out.println(getName() + " huddles beneath some straw to doze off. zz");
        updateStat("energy", 35);
        updateStat("hunger", 10);
        increaseHealth(10);
    }
    
    @Override
    public void makeNoise() {
        System.out.println("squeak squeak..");
    }    
    
    @Override
    public void clean() {
        System.out.println(getName() + " licks their belly fur.");
        updateStat("happiness", 15);
        updateStat("energy", -3);
        increaseHealth(5);
    }
    
}
