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
        System.out.println(getName() + " competes in the daily race!!");
        updateStat("happiness", 30);
        updateStat("energy", -25);
        updateStat("hunger", -15);
        increaseHealth(10);
    }
    
    @Override
    public void eat() {
        System.out.println(getName() + " gnaws away at some fresh lettuce.");
        updateStat("energy", 5);
        updateStat("hunger", 15);
        increaseHealth(15);
    }
    
    @Override
    public void sleep() {
        System.out.println(getName() + " rests in their shaded nest of leaves. zzzzzz");
        updateStat("energy", 30);
        updateStat("hunger", -10);
        increaseHealth(10);
    }
    
    @Override
    public void makeNoise() {
        System.out.println("zooooooooommmmmmmm!!!");
    }
    
    @Override
    public void clean() {
        System.out.println(getName() + " rubs against a hard rock to shed skin.");
        updateStat("happiness", 10);
        updateStat("energy", -10);
        increaseHealth(15);
    }
    
}
