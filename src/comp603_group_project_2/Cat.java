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
        System.out.println(getName() + " bats at a dangling toy.");
        adjustStat("happiness", 30);
        adjustStat("energy", -20);
        adjustStat("hunger", -8);
        increaseHealth(5);
    }

    @Override
    public void eat() {
        System.out.println(getName() + " delicately eats its fishy treat.");
        adjustStat("hunger", 30);
        adjustStat("energy", 3);
        increaseHealth(10);
    }

    @Override
    public void sleep() {
        System.out.println(getName() + " naps in a sunbeam.");
        adjustStat("energy", 35);
        adjustStat("hunger", -5);
        increaseHealth(5);
    }

    @Override
    public void makeNoise() {
        System.out.println("Meow! A soft purr.");
    }

    @Override
    public void clean() {
        System.out.println(getName() + " meticulously grooms itself.");
        adjustStat("happiness", 5);
        increaseHealth(2);
    }
}
