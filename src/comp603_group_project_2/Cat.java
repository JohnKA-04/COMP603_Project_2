/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comp603_group_project_2;

/**
 *
 * @author johnk
 */
class Cat extends VirtualPet {
    public Cat(String petName) { super(petName); }

    @Override
    public void play() {
        updateStat("happiness", 30);
        updateStat("energy", -20);
        updateStat("hunger", -8);
        increaseHealth(5);
        return getName() + " claws at a dangling toy.";
    }

    @Override
    public void eat() {
        updateStat("hunger", 30);
        updateStat("energy", 3);
        increaseHealth(10);
        return getName() + " delicately eats its fishy treat.";
    }

    @Override
    public void sleep() {
        updateStat("energy", 35);
        updateStat("hunger", -5);
        increaseHealth(5);
        return getName() + " naps in a sunbeam.";
    }

    @Override
    public void makeNoise() {
        return getName() + " says: Meow! A soft purr.";
    }

    @Override
    public void clean() {
        updateStat("happiness", 5);
        increaseHealth(2);
        return getName() + " meticulously grooms itself.";
    }
}
