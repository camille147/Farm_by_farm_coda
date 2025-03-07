package controller;

import model.Vegetable;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FarmController {
    private final Map<String, Vegetable> vegetableInventory = new HashMap<>();
    private double wallet = 500;

    public FarmController() {
        File fileBase = new File("harvest");
        if (!fileBase.exists()) {
            fileBase.mkdir();
            System.out.println("harvest crééer");
        } else {
            System.out.println("dossier exitant");
        }
        loadData();
        System.out.println(vegetableInventory);
    }

    public void plantVegetable(String vegetableName) {
        if (vegetableInventory.containsKey(vegetableName) && vegetableInventory.get(vegetableName).getQuantity() > 0) {
            vegetableInventory.get(vegetableName).lowerQuantity();
            System.out.println(vegetableName + " planté !");
            saveToFile();

        }else {
            System.out.println("Pas assez de " + vegetableName + " dans l'inventaire !");
        }

    }
    public void harvestVegetable(String vegetableName) {
        if (vegetableInventory.containsKey(vegetableName)) {
            Vegetable currentHarvestVegetable = vegetableInventory.get(vegetableName);

            System.out.println("Avant récolte : " + currentHarvestVegetable);
            currentHarvestVegetable.increaseQuantity();
            System.out.println("Après récolte : " + currentHarvestVegetable);

            System.out.println(vegetableName + " récolté !");
            saveToFile();
        } else {
            System.out.println("Erreur : " + vegetableName + " n'existe pas dans l'inventaire !");
        }
    }


    private void saveToFile() {
        try (FileWriter writer = new FileWriter("harvest.txt")) {
            writer.write("Wallet:" + wallet + "\n");
            for (Vegetable vegetable : vegetableInventory.values()) {
                writer.write(vegetable.getName() + " : " + vegetable.getQuantity() + "\n");
            }
            System.out.println("Inventaire sauvegardé !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map loadData() {
        try (BufferedReader reader = new BufferedReader((new FileReader("harvest.txt")))) {
            reader.lines().forEach(line -> {
                String[] parts = line.split(" : ");
                if (parts.length == 2) {
                    String name = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    vegetableInventory.put(name, new Vegetable(name, quantity));
                }

            });
        }catch(IOException e) {
            System.out.println("load");
            e.printStackTrace();
        }
        return vegetableInventory;
    }
}
