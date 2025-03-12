package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Animal;
import model.Production;
import model.Vegetable;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FarmController {
    private final Map<String, Vegetable> vegetableInventory = new HashMap<>();
    private final Map<String, Vegetable> vegetableProducts = new HashMap<>();

    private final Map<String, Animal> animalInventory = new HashMap<>();
    private final Map<String, Production> animalProduction = new HashMap<>();

    private final Map<String, Integer> vegetablePrices = new HashMap<>();
    private final Map<String, Integer> vegetableSellPrices = new HashMap<>();
    private final Map<String, Integer> animalPrices = new HashMap<>();
    private final Map<String, String> animalFood = new HashMap<>();
    private final Map<String, String> animalResource = new HashMap<>();



    private final Map<String, Integer> animalSellPrices = new HashMap<>();
    private final ArrayList<String> bankInstructions = new ArrayList<>();

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
        initializeData();
        loadData();
        loadDataProducts();
        loadBank();

        System.out.println(vegetableInventory);
        System.out.println(vegetableProducts);
    }
    private void initializeData() {
        vegetablePrices.putAll(Map.of(
                "Blé", 10, "Mais", 12, "Pomme", 5, "Banane", 7, "Cerise", 50
        ));
        vegetableSellPrices.putAll(Map.of(
                "Blé", 15, "Mais", 17, "Pomme", 10, "Banane", 12, "Cerise", 60
        ));
        animalPrices.putAll(Map.of(
                "Poule", 11, "Vache", 22, "Mouton", 33
        ));
        animalSellPrices.putAll(Map.of(
                "Poule", 11, "Vache", 22, "Mouton", 33
        ));
        animalFood.putAll(Map.of(
                "Poule", "Mais", "Mouton", "Pomme", "Vache", "Blé"
        ));
        animalResource.putAll(Map.of(
                "Poule", "Oeufs", "Mouton", "Laine", "Vache", "Lait"
        ));
    }
    public void initialize() {
        if (moneyLabel != null) {
            moneyLabel.setText(("Argent : " + wallet + "€"));
        } else {
            System.out.println(" moneyLabel est toujours null après injection !");
        }
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
            bankInstructions.add("Achat : " + vegetableName + " : " +  price + "pièces");
            System.out.println("achat ok" + vegetableName);
            moneyLabel.setText("Argent :" + wallet);
            saveToFile();
            saveToFileBank();
        } else {
            System.out.println("Fond insuffisant pr acheter" + vegetableName);
        }
    }
    //

    public void buyVegetable(ActionEvent event) {
        Button selectedVegetableBuyButton = (Button) event.getSource();
        System.out.println(selectedVegetableBuyButton.getText());
        buyVegetable(selectedVegetableBuyButton.getText());
    }
    //

    public void buyAnimal(ActionEvent event) {
        Button selectedAnimalBuyButton = (Button) event.getSource();
        System.out.println(selectedAnimalBuyButton.getText());
        buyAnimal(selectedAnimalBuyButton.getText());
    }
    public void sellVegetable(String vegetableName) {

        if (!vegetableSellPrices.containsKey(vegetableName)) {
            System.out.println(vegetableSellPrices);
            System.out.println("Le prix de vente de " + vegetableName + " n'existe pas !");
            return;
        }
        if (!vegetableProducts.containsKey(vegetableName) || vegetableProducts.get(vegetableName).getQuantity() <= 0) {
            System.out.println("Vous n'avez pas assez de " + vegetableName + " pour vendre !");
            return;
        }

        int sellPrice = vegetableSellPrices.get(vegetableName);
        wallet += sellPrice;

        vegetableProducts.get(vegetableName).lowerQuantity();
        System.out.println("Vente réussie : " + vegetableName + " vendu pour " + sellPrice + "€.");

        bankInstructions.add("Vente : " + vegetableName + " : " + sellPrice + "pièces");

        moneyLabel.setText("Argent : " + wallet + "€");
        saveToFileProducts();
        System.out.println(bankInstructions);
        saveToFileBank();
    }

    public void buyAnimal(String animalName) {
        if (!animalPrices.containsKey(animalName)) return;

        int price = animalPrices.get(animalName);
        if (wallet >= price) {
            wallet -= price;
            animalInventory.putIfAbsent(animalName, new Animal(animalName));
            animalInventory.get(animalName).increaseQuantity();
            bankInstructions.add("Achat : " + animalName + " : " + price + "€");
            moneyLabel.setText("Argent : " + wallet + "€");
            saveToFileBank();
        } else {
            System.out.println("Pas assez d'argent pour acheter " + animalName);
        }
    }

    public boolean feedAnimal(String animalName) {
        if (!animalInventory.containsKey(animalName)) {
            System.out.println("Cet animal n'existe pas dans l'inventaire !");
            return false;
        }

        if (!animalFood.containsKey(animalName)) {
            System.out.println("Aucune nourriture définie pour " + animalName);
            return false;
        }

        String food = animalFood.get(animalName);

        if (vegetableProducts.containsKey(food) &&  vegetableProducts.get(food).getQuantity() > 0) {
            Vegetable foodResource = vegetableProducts.get(food);
            foodResource.lowerQuantity();
            System.out.println(animalName + " a été nourri avec " + food + " !");
            saveToFile();
            return true;
        } else {
            System.out.println("Pas assez de " + food + " pour nourrir " + animalName);
        }
        return false;
    }

    public void recoltAnimal(String animalName) {
        if (!animalInventory.containsKey(animalName)) {
            System.out.println(" Cet animal n'existe pas dans l'inventaire !");
            return;
        }

        if (!animalResource.containsKey(animalName)) {
            System.out.println(" Aucune ressource définie pour " + animalName);
            return;
        }

        String resource = animalResource.get(animalName);

        if (animalProduction.containsKey(animalName)) {
            Production product = animalProduction.get(animalName);
            product.increaseQuantity();
            System.out.println("Récolte réussie : " + resource + " (+1)");
        } else {
            Production product = new Production(resource, 3);
            animalProduction.put(animalName, product);
            System.out.println(" Nouvelle production enregistrée : " + resource + " (1)");
        }

        saveToFileProducts();
    }



    public void sell(ActionEvent event) {
        Button selectedVegetableSellButton = (Button) event.getSource();
        System.out.println("Vente de " + selectedVegetableSellButton.getText());
        sellVegetable(selectedVegetableSellButton.getText());
    }

    public boolean plantVegetable(String vegetableName) {
        if (vegetableInventory.containsKey(vegetableName) && vegetableInventory.get(vegetableName).getQuantity() > 0) {
            vegetableInventory.get(vegetableName).lowerQuantity();
            System.out.println(vegetableName + " planté !");
            saveToFile();
            return true;
        }else {
            System.out.println("Pas assez de " + vegetableName + " dans l'inventaire !");
            return false;
        }
    }

    public void installAnimal(String animalName) {
        System.out.println("d");

        System.out.println(vegetableInventory);
        System.out.println(animalInventory);

        if (animalInventory.containsKey(animalName) && animalInventory.get(animalName).getQuantity() > 0) {
            animalInventory.get(animalName).lowerQuantity();
            System.out.println(animalName + " lancé en prmiere phase de croissqnace !");
            saveToFile();
        }else {
            System.out.println("Pas assez de " + animalName + " dans l'inventaire !");
        }
    }

    public void harvestVegetable(String vegetableName) {
        if (vegetableProducts.containsKey(vegetableName)) {
            Vegetable currentHarvestVegetable = vegetableProducts.get(vegetableName);

            System.out.println("Avant récolte : " + currentHarvestVegetable);
            currentHarvestVegetable.increaseQuantity();
            System.out.println("Après récolte : " + currentHarvestVegetable);

            System.out.println(vegetableName + " récolté !");
            saveToFileProducts();

        } else {

            System.out.println("Erreur : " + vegetableName + " n'existe pas dans l'inventaire !");
        }
    }

    private void saveToFile() {
        try (FileWriter writer = new FileWriter("harvest.txt")) {
            // Sauvegarde des légumes
            for (Vegetable vegetable : vegetableInventory.values()) {
                writer.write("V:" + vegetable.getName() + " : " + vegetable.getQuantity() + "\n");
            }

            // Sauvegarde des animaux
            for (Animal animal : animalInventory.values()) {
                writer.write("A:" + animal.getName() + " : " + animal.getQuantity() + "\n");
            }

            System.out.println("Inventaire sauvegardé !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void saveToFileProducts() {
        try (FileWriter writer = new FileWriter("products.txt")) {

            for (Vegetable vegetable : vegetableProducts.values()) {
                writer.write("V:" + vegetable.getName() + " : " + vegetable.getQuantity() + "\n");
            }

            // Sauvegarde des animaux
            for (Production product : animalProduction.values()) {
                writer.write("P:" + product.getName() + " : " + product.getQuantity() + "\n");
            }
            System.out.println("Inventaire sauvegardé !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveToFileBank() {
        try (FileWriter writer = new FileWriter("bank.txt")) {
            writer.write("Wallet:" + wallet + "\n");
            System.out.println(wallet);
            for (int i = 0; i < bankInstructions.size(); i++) {
                writer.write(bankInstructions.get(i) + "\n");
            }
            System.out.println("bank sauvegardé !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("harvest.txt"))) {
            reader.lines().forEach(line -> {
                String[] parts = line.split(" : ");
                if (parts.length == 2) {
                    String typeAndName = parts[0];
                    int quantity = Integer.parseInt(parts[1]);

                    if (typeAndName.startsWith("V:")) {
                        String name = typeAndName.substring(2); // Enlève "V:"
                        vegetableInventory.put(name, new Vegetable(name, quantity));
                    } else if (typeAndName.startsWith("A:")) {
                        String name = typeAndName.substring(2); // Enlève "A:"
                        animalInventory.put(name, new Animal(name, quantity));
                    }
                }
            });
            System.out.println("Données chargées !");
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement des données.");
            e.printStackTrace();
        }
    }


    private Map loadDataProducts() {
        try (BufferedReader reader = new BufferedReader((new FileReader("products.txt")))) {
            reader.lines().forEach(line -> {

                    String[] parts = line.split(" : ");
                    if (parts.length == 2) {
                        String typeAndName = parts[0];
                        int quantity = Integer.parseInt(parts[1]);
                        if (typeAndName.startsWith("V:")) {
                            String name = typeAndName.substring(2); // Enlève "V:"
                            vegetableProducts.put(name, new Vegetable(name, quantity));
                        } else if (typeAndName.startsWith("P:")) {
                            String name = typeAndName.substring(2); // Enlève "A:"
                            animalProduction.put(name, new Production(name, quantity));
                        }
                    }

            });
        }catch(IOException e) {
            System.out.println("load");
            e.printStackTrace();
        }
        return vegetableProducts;
    }

    private ArrayList loadBank() {
        try (BufferedReader reader = new BufferedReader((new FileReader("bank.txt")))) {
            reader.lines().forEach(line -> {
                if (line.startsWith("Wallet:")) {
                    wallet = Double.parseDouble(line.split(":")[1].trim());

                } else {
                    bankInstructions.add(line); // Ajoute la ligne telle quelle
                }
            });

        }catch(IOException e) {
            System.out.println("load");
            e.printStackTrace();
        }
        return bankInstructions;
    }

}
