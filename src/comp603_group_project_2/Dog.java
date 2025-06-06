/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comp603_group_project_2;

/**
 *
 * @author johnk
 */
public class Dog extends VirtualPet{
    public Dog(String name){
        super(name);
    }
     @Override
    public void play() {
        System.out.println(getName() + " chases a ball!");
        decreaseHappiness(20);
        decreaseEnergy(-10);
        decreaseHunger(-5);
        decreaseClean(-5);
    }

    @Override
    public void eat() {
        System.out.println(getName() + " devours a tasty bone.");
        decreaseHunger(30);
        decreaseEnergy(5);
    }

    @Override
    public void sleep() {
        System.out.println(getName() + " is napping.");
        decreaseEnergy(40);
        decreaseHunger(-5);
    }
        @Override
    public void clean() {
        System.out.println(getName() + " Scrubb dubb dubb.");
        decreaseClean(30);
        decreaseHunger(-5);
    }

    @Override
    public void makeNoise() {
        System.out.println("Woof! Woof!");
    }
    
}
