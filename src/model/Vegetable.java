package model;

public class Vegetable {
    private String name;
   // private double price;
    //private double sellPrice;
    //private long plantTime;
    //private long growthTime;
    private int quantity;


  //  public Vegetable(String name, double price, double sellPrice, long growthTime ) {
    public Vegetable(String name, int quantity) {

      this.name = name;
        //this.price = price;
        //this.sellPrice = sellPrice;
        //this.plantTime = System.currentTimeMillis();
        //this.growthTime = growthTime;
        this.quantity = quantity;

    }
    public Vegetable(String name) {

        this(name, 0);

    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity() {
        this.quantity = quantity + 1;
    }

    public void lowerQuantity() {
        if (quantity < 1) {
            this.quantity -= quantity;

        }
    }

//    public boolean isReady() {
//        if (System.currentTimeMillis()-plantTime >= growthTime) {
//            return true;
//        }
//        return false;
//    }




}