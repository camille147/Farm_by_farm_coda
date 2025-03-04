package model;

import java.util.ArrayList;

public class Animal {
    private String name;
    private double price;
    private double sellPrice;
    private ArrayList<Vegetable> food = new ArrayList<Vegetable>();
    //private ;

    public Animal(String name, double price, double sellPrice, long growthTime ) {
        this.name = name;
        this.price = price;
        this.sellPrice = sellPrice;
    }



}