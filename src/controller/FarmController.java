package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Animal;
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
    private final Map<String, Integer> vegetablePrices = new HashMap<>();
    private final Map<String, Integer> vegetableSellPrices = new HashMap<>();
    private final Map<String, Integer> animalPrices = new HashMap<>();
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
        initializeVegetablePrices();
        initializeAnimalPrices();
        initializeVegetableSellPrices();
        loadData();
        loadDataProducts();
        loadBank();

        System.out.println(vegetableInventory);
        System.out.println(vegetableProducts);
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

    public void initializeAnimalPrices() {
        animalPrices.put("Poule", 11);
        animalPrices.put("Vache", 22);
        animalPrices.put("Cochon", 33);
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


    public void initializeVegetableSellPrices() {
        vegetableSellPrices.put("Blé", 15);
        vegetableSellPrices.put("Mais", 17);
        vegetableSellPrices.put("Pomme", 10);
        vegetableSellPrices.put("Banane", 12);
        vegetableSellPrices.put("Cerise", 60);
    }

    public void initializeAnimalSellPrices() {
        vegetableSellPrices.put("Vache", 22);
        vegetableSellPrices.put("Poule", 11);
        vegetableSellPrices.put("Cochon", 33);
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
        if (!animalPrices.containsKey(animalName)){
            System.out.println(animalPrices);
            System.out.println("Le prix de l'animal " + animalName + " n'existe pas !");
            System.out.println("existe pas prix animal");
            return;
        }
        int price = animalPrices.get(animalName);

        if (wallet >= price) {
            wallet -= price;
            //System.out.println(animalInventory);
            animalInventory.putIfAbsent(animalName, new Animal(animalName));
            animalInventory.get(animalName).increaseQuantity();
            System.out.println(animalName + " acheté !");
            saveToFile();  // Assurez-vous de sauvegarder après l'achat de l'animal.
        } else {
            System.out.println("Pas assez d'argent pour acheter " + animalName);
        }
    }


//    public void addResource(String resourceName, int quantity) {
//        System.out.println(resourceName + " produit " + quantity + " unité(s) !");
//        // Ajout à l’inventaire des ressources (ex : œufs)
//        saveToFile();
//    }


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
    }     // tab avec veg et animaux, et qd enregistrer pas animaux donc load pas naimaux

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
                writer.write(vegetable.getName() + " : " + vegetable.getQuantity() + "\n");
            }
//
//            for (Vegetable vegetable : vegetableInventory.values()) {
//                writer.write(vegetable.getName() + " : " + vegetable.getQuantity() + "\n");
//            }
//            for (Animal animal : vegetableInventory.values()) {
//                writer.write(animal.getName() + " : " + animal.getAge() + "\n");
//            }
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
                        String name = parts[0];
                        int quantity = Integer.parseInt(parts[1]);
                        vegetableProducts.put(name, new Vegetable(name, quantity));
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
