package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class FarmController {

    private Button selectedButton;

    private void selectButton(ActionEvent event) {
        selectedButton = (Button) event.getSource();
        openChoicePlantation();
    }
    @FXML
    private void openChoicePlantation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/openChoicePlantation.fxml"));
            Parent root = loader.load();

            //Button clickedButton = (Button) event.getSource();

            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Plantation");
            modalStage.setScene(new Scene(root));

            modalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleWheatSelection() {
        startPlanting("B", 5);
        closeWindow();
    }

    private void startPlanting(String name, int growTime) {

    }



}