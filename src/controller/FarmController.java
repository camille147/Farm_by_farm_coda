package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Vegetable;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FarmController {
    private final Map<String, Vegetable> vegetableInventory = new HashMap<>();
    private final Map<String, Integer> vegetablePrices = new HashMap<>();
    private final Map<String, Integer> vegetableSellPrices = new HashMap<>();

    @FXML
    private Label moneyLabel;

    @FXML
    private Label money;

    private double wallet;

    public FarmController() {
        File fileBase = new File("harvest");
        if (!fileBase.exists()) {
            fileBase.mkdir();
            System.out.println("harvest crééer");
        } else {
            System.out.println("dossier exitant");
        }
        initializeVegetablePrices();
        initializeVegetableSellPrices();
        loadData();

        System.out.println(vegetableInventory);
    }

    public void initialize() {
        if (moneyLabel != null) {
            moneyLabel.setText(("Argent : " + wallet + "€"));
        } else {
            System.out.println("⚠️ moneyLabel est toujours null après injection !");
        }
    }

    public void initializeVegetablePrices() {
        vegetablePrices.put("Blé", 10);
        vegetablePrices.put("Mais", 12);
        vegetablePrices.put("Pomme", 5);
        vegetablePrices.put("Banane", 7);
        vegetablePrices.put("Cerise", 50);
    }

    public void buyVegetable(String vegetableName) {
        if (!vegetablePrices.containsKey(vegetableName)){
            System.out.println("existe pas prix");
            return;
        }
        int price = vegetablePrices.get(vegetableName);
        if (wallet >= price) {
            wallet = wallet - price;
            vegetableInventory.putIfAbsent(vegetableName, new Vegetable(vegetableName));
            vegetableInventory.get(vegetableName).increaseQuantity();
            System.out.println("achat ok" + vegetableName);
            moneyLabel.setText("Argent :" + wallet);
            saveToFile();
        } else {
            System.out.println("Fond insuffisant pr acheter" + vegetableName);
        }
    }

    public void buy(ActionEvent event) {
        Button selectedVegetableBuyButton = (Button) event.getSource();
        System.out.println(selectedVegetableBuyButton.getText());
        buyVegetable(selectedVegetableBuyButton.getText());
    }


    public void initializeVegetableSellPrices() {
        vegetableSellPrices.put("Blé", 15);
        vegetableSellPrices.put("Mais", 17);
        vegetableSellPrices.put("Pomme", 10);
        vegetableSellPrices.put("Banane", 12);
        vegetableSellPrices.put("Cerise", 60);
    }

    public void sellVegetable(String vegetableName) {

        if (!vegetableSellPrices.containsKey(vegetableName)) {
            System.out.println(vegetableSellPrices);
            System.out.println("Le prix de vente de " + vegetableName + " n'existe pas !");
            return;
        }
        if (!vegetableInventory.containsKey(vegetableName) || vegetableInventory.get(vegetableName).getQuantity() <= 0) {
            System.out.println("Vous n'avez pas assez de " + vegetableName + " pour vendre !");
            return;
        }

        int sellPrice = vegetableSellPrices.get(vegetableName);
        wallet += sellPrice;
        vegetableInventory.get(vegetableName).lowerQuantity();
        System.out.println("Vente réussie : " + vegetableName + " vendu pour " + sellPrice + "€.");

        moneyLabel.setText("Argent : " + wallet + "€");
        saveToFile();
    }


    public void sell(ActionEvent event) {
        Button selectedVegetableSellButton = (Button) event.getSource();
        System.out.println("Vente de " + selectedVegetableSellButton.getText());
        sellVegetable(selectedVegetableSellButton.getText());
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
            System.out.println(wallet);
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
                if (line.startsWith("Wallet:")) {
                    wallet = Double.parseDouble(line.split(":")[1].trim());

                } else {
                    String[] parts = line.split(" : ");
                    if (parts.length == 2) {
                        String name = parts[0];
                        int quantity = Integer.parseInt(parts[1]);
                        vegetableInventory.put(name, new Vegetable(name, quantity));
                    }
                }
            });
        }catch(IOException e) {
            System.out.println("load");
            e.printStackTrace();
        }
        return vegetableInventory;
    }
}
