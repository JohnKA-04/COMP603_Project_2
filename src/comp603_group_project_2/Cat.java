/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comp603_group_project_2;

/**
 *
 * @author johnk
 */
public class Cat extends VirtualPet {
    public Cat(String name){
        super(name);
    }
    
    @Override
    public void play() {
        System.out.println(getName() + " pounces on a laser pointer.");
        updateHappiness(25);
        updateEnergy(-15);
        updateHunger(-5);
        increaseHealth(5);
    }
    
    @Override
    public void eat() {
        System.out.println(getName() + " enjoys some fish.");
        updateHunger(25);
        updateEnergy(5);
        increaseHealth(10);
    }
    
    @Override
    public void sleep() {
        System.out.println(getName() + " curls up for a snooze. zzzz");
        updateEnergy(30);
        updateHunger(-5);
        increaseHealth(5);
    }
    
    @Override 
    public void makeNoise() {
        System.out.println("Meow Meowww!");
    }
    
    @Override
    public void clean() {
        System.out.println(getName() + " is grooming itself.");
        updateHappiness(10);
        updateEnergy(-3);
        increaseHealth(3);
    }
   
}
