package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Farm;

import java.io.IOException;

public class GameController {

    private Farm farm;

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    @FXML
    private void openBuyStore(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/openBuyStore.fxml"));
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("BuyStore");
            modalStage.setScene(new Scene(root));

            modalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openSellStore(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/openSellStore.fxml"));
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("BuyStore");
            modalStage.setScene(new Scene(root));

            modalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
