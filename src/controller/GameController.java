package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Vegetable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameController {

    private final FarmController farmController = new FarmController();
    private final Map<MenuButton, String> plantedVegetables = new HashMap<>();
    private final Map<MenuButton, String> readyToHarvestVegetable = new HashMap<>();
    private final Map<MenuButton, String> startingGrowing = new HashMap<>();
    private final Map<MenuButton, String> readyToFeed = new HashMap<>();
    private final Map<MenuButton, String> continueGrowing = new HashMap<>();
    private final Map<MenuButton, String> readyToRecoltRessource = new HashMap<>();


    private double wallet;

    @FXML
    private Label labelMoney;



    @FXML
    private GridPane gridPane;

    public void initialize() {
        loadWallet();
        if (labelMoney != null) {
            labelMoney.setText(("Solde : " + wallet + "€"));
        } else {
            System.out.println("⚠️ moneyLabel est toujours null après injection !");
        }
    }

    @FXML
    private void openBuyStore(ActionEvent event) {
        openModalWindow("../fxml/openBuyStore.fxml", "Magasin d'Achat");
    }

    @FXML
    private void openSellStore(ActionEvent event) {
        openModalWindow("../fxml/openSellStore.fxml", "Magasin de vente");
    }

    @FXML
    private void openBank(ActionEvent event) {
        openModalWindow("../fxml/openBank.fxml", "Banque");
    }

    private void openModalWindow(String fxml, String title) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            labelMoney.setText(String.valueOf("Solde : " + wallet + "€"));
            Stage modal = new Stage();
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setTitle(title);


            modal.setScene(new Scene(root));

            modal.showAndWait();
            loadWallet();
            labelMoney.setText(String.valueOf("Solde : " + wallet + "€"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void selectButton(ActionEvent event) {
        MenuItem selectedVegetableItem = (MenuItem) event.getSource();
        MenuButton parentMenuButton = (MenuButton) selectedVegetableItem.getParentPopup().getOwnerNode();

        String vegetableName = selectedVegetableItem.getText();

        if (startingGrowing.containsKey(parentMenuButton) ||
                readyToFeed.containsKey(parentMenuButton) ||
                continueGrowing.containsKey(parentMenuButton) ||
                readyToRecoltRessource.containsKey(parentMenuButton)) {

            System.out.println("Impossible de planter un légume ici, un animal est déjà présent !");
            return;
        }
        if (plantedVegetables.containsKey(parentMenuButton)) {
            System.out.println("en cours de pousse");

        }  else if (readyToHarvestVegetable.containsKey(parentMenuButton)){
            harvestVegetable(parentMenuButton, selectedVegetableItem);


        } else {
            if(farmController.plantVegetable(vegetableName)) {
                plantedVegetables.put(parentMenuButton, vegetableName);
                parentMenuButton.setText("p");
                startGrowthTimer(parentMenuButton, selectedVegetableItem);
            };

        }

    }

    public void selectButtonAnimal(ActionEvent event) {
        MenuItem selectedAnimalItem = (MenuItem) event.getSource();
        MenuButton parentMenuButton = (MenuButton) selectedAnimalItem.getParentPopup().getOwnerNode();

        String animalName = selectedAnimalItem.getText();

        if (plantedVegetables.containsKey(parentMenuButton) ||
                readyToHarvestVegetable.containsKey(parentMenuButton)) {

            System.out.println("Impossible d'installer un animal ici, un légume est déjà présent !");
            return;
        }

        if (startingGrowing.containsKey(parentMenuButton)) {
            System.out.println("en première phase de croissnace");

        } else if (readyToFeed.containsKey(parentMenuButton)){
            System.out.println("doit etre nourrit");
//            harvestVegetable(parentMenuButton, selectedAnimalItem);

        } else if (continueGrowing.containsKey(parentMenuButton)){
            System.out.println("en deuxieme phase de croissnace");

        }else if (readyToRecoltRessource.containsKey(parentMenuButton)){
            harvestVegetable(parentMenuButton, selectedAnimalItem);

        } else {
            farmController.installAnimal(animalName);
            startingGrowing.put(parentMenuButton, animalName);
            parentMenuButton.setText("a");
            startGrowthTimer(parentMenuButton, selectedAnimalItem);
        }

    }

    private void startGrowthTimer(MenuButton parentButton, MenuItem selectedVegetableItem) {
//        plantedButtons.add(button);
//        button.setText("p");

        // Simulation du temps de pousse (5 secondes avant récolte possible)
        Timeline growthTimeline = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> {
                    String vegetableName = plantedVegetables.get(parentButton);

                    plantedVegetables.remove(parentButton);
                    readyToHarvestVegetable.put(parentButton, vegetableName);
                    MenuButton parentMenuButton = (MenuButton) selectedVegetableItem.getParentPopup().getOwnerNode();
                    parentButton.setText("R");
                    selectedVegetableItem.setText("Récolter");
                    System.out.println("time finish swith recolte");

                })
        );
        growthTimeline.setCycleCount(1);
        growthTimeline.play();
    }

    private void harvestVegetable (MenuButton parentButton, MenuItem selectedVegetableItem) {
        System.out.println(parentButton);
        String action = selectedVegetableItem.getText();
        String vegetableName = readyToHarvestVegetable.get(parentButton);
        System.out.println("gf");
        System.out.println(vegetableName);
        parentButton.setText("R");


        if (action.equals("Récolter") ) {
            System.out.println("j");

            selectedVegetableItem.setText(vegetableName);
            farmController.harvestVegetable(vegetableName);
            readyToHarvestVegetable.remove(parentButton);
            //parentButton.setText("Blé");
            System.out.println("recolte !");
            parentButton.setText("");

        } else {
            System.out.println(("erreur recolte"));
        }

    }


    private double loadWallet() {
        try (BufferedReader reader = new BufferedReader((new FileReader("bank.txt")))) {
            reader.lines().forEach(line -> {
                if (line.startsWith("Wallet:")) {
                    wallet = Double.parseDouble(line.split(":")[1].trim());

                }
            });
        }catch(IOException e) {
            System.out.println("load wallet error");
            e.printStackTrace();
        }
        return wallet;
    }

}