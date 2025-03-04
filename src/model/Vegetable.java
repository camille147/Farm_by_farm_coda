package model;

public class Vegetable {
    private String name;
    private double price;
    private double sellPrice;
    private long plantTime;
    private long growthTime;


    public Vegetable(String name, double price, double sellPrice, long growthTime ) {
        this.name = name;
        this.price = price;
        this.sellPrice = sellPrice;
        this.plantTime = System.currentTimeMillis();
        this.growthTime = growthTime;
    }

    public String getName() {
        return name;
    }

    public boolean isReady() {
        if (System.currentTimeMillis()-plantTime >= growthTime) {
            return true;
        }
        return false;
    }




}