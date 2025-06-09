/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comp603_group_project_2;

/**
 *
 * @author johnk
 */
public class Dog extends VirtualPet {
    public Dog(String petName) { super(petName); }

    @Override
    public void play() {
        System.out.println(getName() + " fetches the frisbee!");
        updateStat("happiness", 25);
        updateStat("energy", -15);
        updateStat("hunger", -10);
        increaseHealth(5);
    }

    @Override
    public void eat() {
        System.out.println(getName() + " crunches on some kibble.");
        updateStat("hunger", 35);
        updateStat("energy", 5);
        increaseHealth(10);
    }

    @Override
    public void sleep() {
        System.out.println(getName() + " snores peacefully.");
        updateStat("energy", 45);
        updateStat("hunger", -5);
        increaseHealth(5);
    }

    @Override
    public void makeNoise() {
        System.out.println("Woof woof! A happy bark!");
    }

    @Override
    public void clean() {
        System.out.println(getName() + " gets a good brush down.");
        updateStat("happiness", 10);
        increaseHealth(3);
    }
}
