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
    public String play() {
        updateStat("happiness", 25);
        updateStat("energy", -15);
        updateStat("hunger", -10);
        increaseHealth(5);
        return getName() + " fetches the frisbee!";
    }

    @Override
    public String eat() {
        updateStat("hunger", 35);
        updateStat("energy", 5);
        increaseHealth(10);
        return getName() + " crunches on some kibble.";
    }

    @Override
    public String sleep() {
        updateStat("energy", 45);
        updateStat("hunger", -5);
        increaseHealth(5);
        return getName() + " snores peacefully"; 
    }

    @Override
    public String makeNoise() {
        return getName() + " says: Woof woof! A happy bark!";
    }

    @Override
    public String clean() {
        updateStat("happiness", 10);
        increaseHealth(3);
        return getName() + " gets a good brush down.";
    }
}
