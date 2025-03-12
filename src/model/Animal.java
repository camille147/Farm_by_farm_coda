package model;

import controller.FarmController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;

public class Animal {
    private String name;
    private int age;
    //private int productionTime;
    private int quantity;
    private double price;
    private boolean isAdult;
    //private double buyPrice;

    public Animal(String name, int quantity) {
        this.name = name;
        this.age = 0; // Bébé au début
        //this.productionTime = productionTime;
        this.quantity = quantity;
        this.isAdult = false;

        //startGrowth();
    }

    public Animal(String name) {

        this(name, 0);

    }

    public void startGrowth() {
        Timeline growthTimeline = new Timeline(
                new KeyFrame(Duration.seconds(30), e -> { // L’animal grandit en 30s
                    this.isAdult = true;
                    System.out.println(name + " est maintenant adulte !");
                })
        );
        growthTimeline.setCycleCount(1);
        growthTimeline.play();
    }

//    public void produce(FarmController farm) {
//        if (isAdult) {
//            Timeline productionTimeline = new Timeline(
//                    new KeyFrame(Duration.seconds(productionTime), e -> {
//                        System.out.println(name + " a produit !");
//                        farm.addResource(name, 1); // Ajoute 1 ressource (ex : œuf) dans FarmController
//                    })
//            );
//            productionTimeline.setCycleCount(Timeline.INDEFINITE);
//            productionTimeline.play();
//        }
//    }
    public void increaseQuantity() {
        this.quantity = quantity + 1;
    }
    public int getQuantity() {
            return quantity;
    }
    public void lowerQuantity() {
        if (this.quantity > 0) {
            this.quantity -= 1;
        }
    }



    public String getName() { return name; }
    public boolean isAdult() { return isAdult; }
    public int getAge() { return age; }

}