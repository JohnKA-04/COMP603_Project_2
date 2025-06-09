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
        updateHappiness(30);
        updateEnergy(-25);
        updateHunger(-15);
        increaseHealth(10);
    }
    
    @Override
    public void eat() {
        System.out.println(getName() + " gnaws away at some fresh lettuce.");
        updateHunger(15);
        updateEnergy(5);
        increaseHealth(15);
    }
    
    @Override
    public void sleep() {
        System.out.println(getName() + " rests in their shaded nest of leaves. zzzzzz");
        updateEnergy(30);
        updateHunger(-10);
        increaseHealth(10);
    }
    
    @Override
    public void makeNoise() {
        System.out.println("zooooooooommmmmmmm!!!");
    }
    
    @Override
    public void clean() {
        System.out.println(getName() + " rubs against a hard rock to shed skin.");
        updateHappiness(10);
        updateEnergy(-10);
        increaseHealth(15);
    }
    
}
