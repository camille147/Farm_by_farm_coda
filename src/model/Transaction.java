package model;

public class Transaction {
    private String name;
    private double price;
    private String type;

    public Transaction(String name, double price, String type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    // ✅ Correctement nommés pour être compatibles avec PropertyValueFactory
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }
}
