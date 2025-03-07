package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameController {

    private final FarmController farmController = new FarmController();
    private final Map<MenuItem, String> plantedVegetables = new HashMap<>();
    private final Map<MenuItem, String> readyToHarvestVegetable = new HashMap<>();


    @FXML
    private GridPane gridPane;
//    private final ArrayList<MenuItem> plantedButtons = new ArrayList<>();
//    private final ArrayList<MenuItem> nothingButtons = new ArrayList<>();
//    private final ArrayList<MenuItem> finishPlantedButtons = new ArrayList<>();

    //private MenuItem buttonSelected;


    @FXML
    private void openBuyStore(ActionEvent event) {
        openModalWindow("../fxml/openBuyStore.fxml", "Magasin d'Achat");
    }

    @FXML
    private void openSellStore(ActionEvent event) {
        openModalWindow("../fxml/openSellStore.fxml", "Magasin de vente");
    }

    private void openModalWindow(String fxml, String title) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            Stage modal = new Stage();
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setTitle(title);
            modal.setScene(new Scene(root));

            modal.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void selectButton(ActionEvent event) {
        MenuItem selectedVegetableItem = (MenuItem) event.getSource();
        //buttonSelected = (MenuItem) event.getSource();
        MenuButton parentMenuButton = (MenuButton) selectedVegetableItem.getParentPopup().getOwnerNode();
        //System.out.println(parentMenuButton);
       // parentMenuButton.setText(buttonSelected.getText());
        //System.out.println(parentMenuButton.getText());
       // System.out.println(parentMenuButton.setText(buttonSelected.getText()));
        String vegetableName = selectedVegetableItem.getText();

        if (plantedVegetables.containsKey(selectedVegetableItem)) {
            System.out.println("en cours de pousse");

        }  else if (readyToHarvestVegetable.containsKey(selectedVegetableItem)){
            harvestVegetable(selectedVegetableItem);

        } else {
            farmController.plantVegetable(vegetableName);
            plantedVegetables.put(selectedVegetableItem, vegetableName);
            parentMenuButton.setText("p");
            startGrowthTimer(selectedVegetableItem);
//            System.out.println("planter");
//
//            buttonSelected.setText("Blé");
//            parentMenuButton.setText("b");
//
//            plantVegetable("Blé", buttonSelected);
        }

    }

    private void startGrowthTimer(MenuItem selectedVegetableItem) {
//        plantedButtons.add(button);
//        button.setText("p");

        // Simulation du temps de pousse (5 secondes avant récolte possible)
        Timeline growthTimeline = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> {
                    String vegetableName = plantedVegetables.get(selectedVegetableItem);

                    plantedVegetables.remove(selectedVegetableItem);
                    readyToHarvestVegetable.put(selectedVegetableItem, vegetableName);
                    MenuButton parentMenuButton = (MenuButton) selectedVegetableItem.getParentPopup().getOwnerNode();
                    parentMenuButton.setText("R");
                    selectedVegetableItem.setText("Blé");
                    System.out.println("time finish swith recolte");





//                    String vegetableName = plantedVegetables.get(selectedVegetableItem);
//                    if (vegetableName != null) {
//                        farmController.harvestVegetable(vegetableName);
//                        selectedVegetableItem.setText("recolter");
//                    }
//                    plantedButtons.remove(button);
//                    finishPlantedButtons.add(button);
//                    button.setText("R");
//                    System.out.println(vegetableName + " est prêt à être récolté !");
                })
        );
        growthTimeline.setCycleCount(1);
        growthTimeline.play();
    }

    private void harvestVegetable (MenuItem currentVegetableItem) {

        System.out.println(currentVegetableItem);
        String vegetableName = readyToHarvestVegetable.get(currentVegetableItem);

        if (vegetableName != null) {
            farmController.harvestVegetable(vegetableName);
            readyToHarvestVegetable.remove(currentVegetableItem);
            currentVegetableItem.setText("Blé");
            System.out.println("recolte !");
        } else {
            System.out.println(("erreur recolte"));
        }
//        System.out.println("Récolte en cours...");
//        finishPlantedButtons.remove(button); // Supprime de la liste des cultures prêtes à récolter
//        button.setText("n");
//        nothingButtons.add(button);

    }

}
