//package controller;
//
//import javafx.application.Platform;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class FarmController {
//
//    private Button selectedButton;
//
//    @FXML
//    private void selectButton(ActionEvent event) {
//        selectedButton = (Button) event.getSource();
//        openChoicePlantation();
//    }
//    @FXML
//    private void openChoicePlantation() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/openChoicePlantation.fxml"));
//            Parent root = loader.load();
//
//            //Button clickedButton = (Button) event.getSource();
//
//            Stage modalStage = new Stage();
//            modalStage.initModality(Modality.APPLICATION_MODAL);
//            modalStage.setTitle("Plantation");
//            modalStage.setScene(new Scene(root));
//
//            modalStage.showAndWait();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    private void handleWheatSelection() {
//        startPlanting("B", 10);
//
//        Stage stage = (Stage) selectedButton.getScene().getWindow();
//        stage.close();
//    }
//
//    private void startPlanting(String name, int growTime) {
//        selectedButton.setText(name);
//
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Platform.runLater(() -> selectedButton.setText("Récolté")); // Change le texte après le temps écoulé
//            }
//        }, growTime * 1000);
//    }
//
//
//
//}
//
//
//package controller;
//
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//public class GameController {
//
//    public Button selectedButton; // Le bouton actuellement sélectionné
//    public String id; // L'ID du bouton sélectionné
//    private Button lastSelectedButton;
//    //    @FXML
////    public Label labelSelection;
//    private Map<Button, String> plantedCrops = new HashMap<>(); // Stocke les cultures par bouton
//
//    @FXML
//    private void openBuyStore(ActionEvent event) {
//        openModal("../fxml/openBuyStore.fxml", "Magasin d'Achat");
//    }
//
//    @FXML
//    private void openSellStore(ActionEvent event ) {
//        openModal("../fxml/openSellStore.fxml", "Magasin de Vente");
//    }
//
//    private void openModal(String fxmlPath, String title) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
//            Parent root = loader.load();
//
//            Stage modalStage = new Stage();
//            modalStage.initModality(Modality.APPLICATION_MODAL);
//            modalStage.setTitle(title);
//            modalStage.setScene(new Scene(root));
//
//            modalStage.showAndWait();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    public void selectButton(ActionEvent event) {
//        selectedButton = (Button) event.getSource();
//        lastSelectedButton = selectedButton;
//        id = selectedButton.getId();
//        System.out.println("Button ID: " + id);
//        plantedCrops.put(lastSelectedButton, "Blé");
//        startPlanting("Blé", 5);
//        //openChoicePlantation();
//    }
//
//    @FXML
//    private void openChoicePlantation() {
//        //updateLabelSelection("Sélectionne une culture pour la parcelle n°"+ selectedButton);
//
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/openChoicePlantation.fxml"));
//            Parent root = loader.load();
//            Stage modalStage = new Stage();
//            modalStage.initModality(Modality.APPLICATION_MODAL);
//            modalStage.setTitle(id);
//
//            modalStage.setScene(new Scene(root));
//            modalStage.showAndWait();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
////    @FXML
////    private void updateLabelSelection(String text) {
////        labelSelection.setText(text);
////    }
//
//    // Méthode appelée pour planter un type de culture
//    public void handleCropSelection(String cropName, int growTime) {
//        if (lastSelectedButton == null) {
//            System.err.println("Aucun bouton sélectionné !");
//            return;
//        }
//
//        plantedCrops.put(lastSelectedButton, cropName);
//        startPlanting(cropName, growTime);
//    }
////    @FXML
////    public void handleWheatSelection() {
////        handleCropSelection("Blé", 5);
////    }
////
////    @FXML
////    public void handleCornSelection() {
////        handleCropSelection("Maïs", 7);
////    }
////
////    @FXML
////    public void handleCarrotSelection() {
////        handleCropSelection("Carottes", 6);
////    }
//
//
//    private void startPlanting(String cropName, int growTime) {
//        if (lastSelectedButton == null) {
//            System.err.println("Aucun bouton sélectionné !");
//            return;
//        }
//
//        lastSelectedButton.setText(cropName + " 🌱");
//        lastSelectedButton.setDisable(true);
//
//        Timeline timeline = new Timeline();
//
//        for (int i = growTime; i > 0; i--) {
//            final int secondsRemaining = i;
//            timeline.getKeyFrames().add(new KeyFrame(
//                    Duration.seconds(growTime - i),
//                    e -> lastSelectedButton.setText(cropName + " (" + secondsRemaining + "s)")
//            ));
//        }
//
//        timeline.getKeyFrames().add(new KeyFrame(
//                Duration.seconds(growTime),
//                e -> {
//                    lastSelectedButton.setText(plantedCrops.get(lastSelectedButton) + " 🌾");
//                    lastSelectedButton.setDisable(false);
//                }
//        ));
//
//        timeline.play();
//    }
//} je veux que au clique sur un bouton une plante pousse visuellement package controller;
//
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//public class GameController {
//
//    public Button selectedButton; // Le bouton actuellement sélectionné
//    public String id; // L'ID du bouton sélectionné
//    private Button lastSelectedButton;
//    //    @FXML
////    public Label labelSelection;
//    private Map<Button, String> plantedCrops = new HashMap<>(); // Stocke les cultures par bouton
//
//    @FXML
//    private void openBuyStore(ActionEvent event) {
//        openModal("../fxml/openBuyStore.fxml", "Magasin d'Achat");
//    }
//
//    @FXML
//    private void openSellStore(ActionEvent event ) {
//        openModal("../fxml/openSellStore.fxml", "Magasin de Vente");
//    }
//
//    private void openModal(String fxmlPath, String title) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
//            Parent root = loader.load();
//
//            Stage modalStage = new Stage();
//            modalStage.initModality(Modality.APPLICATION_MODAL);
//            modalStage.setTitle(title);
//            modalStage.setScene(new Scene(root));
//
//            modalStage.showAndWait();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    public void selectButton(ActionEvent event) {
//        selectedButton = (Button) event.getSource();
//        lastSelectedButton = selectedButton;
//        id = selectedButton.getId();
//        System.out.println("Button ID: " + id);
//        plantedCrops.put(lastSelectedButton, "Blé");
//        startPlanting("Blé", 5);
//        //openChoicePlantation();
//    }
//
//    @FXML
//    private void openChoicePlantation() {
//        //updateLabelSelection("Sélectionne une culture pour la parcelle n°"+ selectedButton);
//
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/openChoicePlantation.fxml"));
//            Parent root = loader.load();
//            Stage modalStage = new Stage();
//            modalStage.initModality(Modality.APPLICATION_MODAL);
//            modalStage.setTitle(id);
//
//            modalStage.setScene(new Scene(root));
//            modalStage.showAndWait();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
////    @FXML
////    private void updateLabelSelection(String text) {
////        labelSelection.setText(text);
////    }
//
//    // Méthode appelée pour planter un type de culture
//    public void handleCropSelection(String cropName, int growTime) {
//        if (lastSelectedButton == null) {
//            System.err.println("Aucun bouton sélectionné !");
//            return;
//        }
//
//        plantedCrops.put(lastSelectedButton, cropName);
//        startPlanting(cropName, growTime);
//    }
////    @FXML
////    public void handleWheatSelection() {
////        handleCropSelection("Blé", 5);
////    }
////
////    @FXML
////    public void handleCornSelection() {
////        handleCropSelection("Maïs", 7);
////    }
////
////    @FXML
////    public void handleCarrotSelection() {
////        handleCropSelection("Carottes", 6);
////    }
//
//
//    private void startPlanting(String cropName, int growTime) {
//        if (lastSelectedButton == null) {
//            System.err.println("Aucun bouton sélectionné !");
//            return;
//        }
//
//        lastSelectedButton.setText(cropName + " 🌱");
//        lastSelectedButton.setDisable(true);
//
//        Timeline timeline = new Timeline();
//
//        for (int i = growTime; i > 0; i--) {
//            final int secondsRemaining = i;
//            timeline.getKeyFrames().add(new KeyFrame(
//                    Duration.seconds(growTime - i),
//                    e -> lastSelectedButton.setText(cropName + " (" + secondsRemaining + "s)")
//            ));
//        }
//
//        timeline.getKeyFrames().add(new KeyFrame(
//                Duration.seconds(growTime),
//                e -> {
//                    lastSelectedButton.setText(plantedCrops.get(lastSelectedButton) + " 🌾");
//                    lastSelectedButton.setDisable(false);
//                }
//        ));
//
//        timeline.play();
//    }
//}
//
//
//package controller;
//
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class GameController {
//
//    private final ArrayList<Button> plantedButtons = new ArrayList<>();
//    private final ArrayList<Button> nothingButtons = new ArrayList<>();
//    private final ArrayList<Button> finishPlantedButtons = new ArrayList<>();
//
//    private Button selectedButton;
//
//    // Ouvrir la boutique d'achat
//    private void openBuyStore(ActionEvent event) {
//        openModalWindow("../fxml/openBuyStore.fxml", "Magasin d'Achat");
//    }
//
//    // Ouvrir la boutique de vente
//    private void openSellStore(ActionEvent event) {
//        openModalWindow("../fxml/openSellStore.fxml", "Magasin de Vente");
//    }
//
//    // Fonction pour ouvrir une fenêtre modale
//    private void openModalWindow(String fxml, String title) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml)); // Correction de getRessource -> getResource
//            Parent root = loader.load();
//
//            Stage modal = new Stage();
//            modal.initModality(Modality.APPLICATION_MODAL);
//            modal.setTitle(title);
//            modal.setScene(new Scene(root));
//
//            modal.showAndWait();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Sélection d'un bouton dans le champ
//    @FXML
//    public void selectButton(ActionEvent event) {
//        selectedButton = (Button) event.getSource(); // Correction de eventgetSource() -> event.getSource()
//
//        if (plantedButtons.contains(selectedButton)) {
//            System.out.println("Culture en cours de pousse...");
//
//        } else if (finishPlantedButtons.contains(selectedButton)) {
//            harvestVegetable(selectedButton);
//        } else {
//            selectedButton.setText("Blé 🌱");
//            plantVegetable("Blé", selectedButton);
//        }
//    }
//
//    // Fonction pour planter un légume
//    private void plantVegetable(String vegetableName, Button button) {
//        System.out.println("Plantage de " + vegetableName + "...");
//        plantedButtons.add(button); // Correction de put -> add
//        button.setText(vegetableName + " 🌱");
//
//        // Simulation du temps de pousse (5 secondes avant récolte possible)
//        Timeline growthTimeline = new Timeline(
//                new KeyFrame(Duration.seconds(5), e -> {
//                    plantedButtons.remove(button);
//                    finishPlantedButtons.add(button);
//                    button.setText("Récolte prête : " + vegetableName);
//                    System.out.println(vegetableName + " est prêt à être récolté !");
//                })
//        );
//        growthTimeline.setCycleCount(1);
//        growthTimeline.play();
//    }
//
//    // Fonction pour récolter un légume
//    private void harvestVegetable(Button button) {
//        System.out.println("Récolte en cours...");
//        finishPlantedButtons.remove(button); // Supprime de la liste des cultures prêtes à récolter
//        button.setText("Champ vide");
//        nothingButtons.add(button); // Ajoute à la liste des champs vides
//    }
//}
