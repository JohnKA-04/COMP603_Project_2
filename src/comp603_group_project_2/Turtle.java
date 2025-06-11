/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comp603_group_project_2;

/**
 *
 * @author johnk
 */
public class Turtle extends VirtualPet {
    public Turtle(String name) {
        super(name);
    }
    
    @Override
    public void play() {
        updateStat("happiness", 30);
        updateStat("energy", -25);
        updateStat("hunger", -15);
        increaseHealth(10);
        return getName() + " competes in the daily race!!";
    }
    
    @Override
    public void eat() {
        updateStat("energy", 5);
        updateStat("hunger", 15);
        increaseHealth(15);
        return getName() + " gnaws away at some fresh lettuce.";
    }
    
    @Override
    public void sleep() {
        updateStat("energy", 30);
        updateStat("hunger", -10);
        increaseHealth(10);
        return getName() + " rests in their shaded nest of leaves. zzzzzz";
    }
    
    @Override
    public void makeNoise() {
        return getName() + " says: zooooooooommmmmmmm!!!";
    }
    
    @Override
    public void clean() {
        updateStat("happiness", 10);
        updateStat("energy", -10);
        increaseHealth(15);
        return getName() + " rubs against a hard rock to shed skin.";
    }
    
}
