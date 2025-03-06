package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameController {


    private final ArrayList<Button> plantedButtons = new ArrayList<>();
    private final ArrayList<Button> nothingButtons = new ArrayList<>();
    private final ArrayList<Button> finishPlantedButtons = new ArrayList<>();

    private Button buttonSelected;

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
        buttonSelected = (Button) event.getSource();

        if (plantedButtons.contains(buttonSelected)) {
            System.out.println("en cours de pousse");

        } else if (finishPlantedButtons.contains(buttonSelected)) {
            harvestVegetable(buttonSelected);
        } else {
            buttonSelected.setText("Blé");
            plantVegetable("Blé", buttonSelected);
        }

    }

    private void plantVegetable(String vegetableName, Button button) {
        plantedButtons.add(button);
        button.setText("p");

        // Simulation du temps de pousse (5 secondes avant récolte possible)
        Timeline growthTimeline = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> {
                    plantedButtons.remove(button);
                    finishPlantedButtons.add(button);
                    button.setText("R");
                    //System.out.println(vegetableName + " est prêt à être récolté !");
                })
        );
        growthTimeline.setCycleCount(1);
        growthTimeline.play();
    }

    private void harvestVegetable(Button button) {
        System.out.println("Récolte en cours...");
        finishPlantedButtons.remove(button); // Supprime de la liste des cultures prêtes à récolter
        button.setText("n");
        nothingButtons.add(button);

    }

}
