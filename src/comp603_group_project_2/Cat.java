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
        System.out.println(getName() + " claws at a dangling toy.");
        updateStat("happiness", 30);
        updateStat("energy", -20);
        updateStat("hunger", -8);
        increaseHealth(5);
    }

    @Override
    public void eat() {
        System.out.println(getName() + " delicately eats its fishy treat.");
        updateStat("hunger", 30);
        updateStat("energy", 3);
        increaseHealth(10);
    }

    @Override
    public void sleep() {
        System.out.println(getName() + " naps in a sunbeam.");
        updateStat("energy", 35);
        updateStat("hunger", -5);
        increaseHealth(5);
    }

    @Override
    public void makeNoise() {
        System.out.println("Meow! A soft purr.");
    }

    @Override
    public void clean() {
        System.out.println(getName() + " meticulously grooms itself.");
        updateStat("happiness", 5);
        increaseHealth(2);
    }
}
