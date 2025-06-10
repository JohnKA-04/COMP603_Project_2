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
    public String play() {
        updateStat("happiness", 30);
        updateStat("energy", -20);
        updateStat("hunger", -15);
        increaseHealth(5);
        return getName() + " goes for a run in their hamster ball.";
    }
    
    @Override
    public String eat() {
        updateStat("energy", 10);
        updateStat("hunger", 20);
        increaseHealth(15);
        return getName() + " nibbles on some hamster pellets.";
        
    }
    
    @Override
    public String sleep() {
        updateStat("energy", 35);
        updateStat("hunger", 10);
        increaseHealth(10);
        return " huddles beneath some straw to doze off. zz";
    }
    
    @Override
    public String makeNoise() {
        return "squeak squeak..";
    }    
    
    @Override
    public String clean() {
        updateStat("happiness", 15);
        updateStat("energy", -3);
        increaseHealth(5);
        return " licks their belly fur.";
        
    }
    
}
