package model;

public class Production {
    private String name;
    // private double price;
    //private double sellPrice;
    //private long plantTime;
    //private long growthTime;
    private int quantity;


    //  public Vegetable(String name, double price, double sellPrice, long growthTime ) {
    public Production(String name, int quantity) {

        this.name = name;
        //this.price = price;
        //this.sellPrice = sellPrice;
        //this.plantTime = System.currentTimeMillis();
        //this.growthTime = growthTime;
        this.quantity = quantity;

    }
    public Production(String name) {

        this(name, 0);

    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity() {
        this.quantity = quantity + 3;
    }


    public void lowerQuantity() {
        if (this.quantity > 0) {
            this.quantity -= 1;
        }
    }
}

//    public boolean isReady() {
//        if (System.currentTimeMillis()-plantTime >= growthTime) {
//            return true;
//        }
//        return false;
//    }




