package model;

import java.util.ArrayList;
import java.util.List;

public class Farm {
    private double money;
    private List<Vegetable> vegetables;
    private List<Animal> animals;

    public Farm(double money) {
        this.money = money;
        this.vegetables = new ArrayList<>();
        this.animals = new ArrayList<>();
    }

    public double getMoney() {
        return money;
    }

    public void addVegetable(Vegetable vegetable) {
        this.vegetables.add(vegetable);
    }

    public void addAnimal(Animal animal) {
        this.animals.add(animal);
    }



}