package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Transaction;

import java.io.*;

public class BankController {

    private double wallet;
    private final ObservableList<Transaction> transactions = FXCollections.observableArrayList(); // ✅ Liste observable pour TableView

    @FXML
    private TableView<Transaction> transactionsTable;

    @FXML
    private TableColumn<Transaction, String> colNom;

    @FXML
    private TableColumn<Transaction, Double> colPrix;

    @FXML
    private TableColumn<Transaction, String> colType;

    @FXML
    public void initialize() {
        // ✅ Associer les colonnes aux propriétés du modèle Transaction
        colNom.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("price"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));

        transactionsTable.setItems(transactions); // ✅ Liaison avec la TableView
        loadBank(); // Chargement du fichier
    }

    private void loadBank() {
        try (BufferedReader reader = new BufferedReader(new FileReader("bank.txt"))) {
            reader.lines().forEach(this::parseTransaction);
        } catch (IOException e) {
            System.err.println("❌ Erreur lors du chargement du fichier bank.txt.");
            e.printStackTrace();
        }
    }

    private void parseTransaction(String line) {
        if (line.startsWith("Wallet:")) {
            wallet = Double.parseDouble(line.split(":")[1].trim()); // ✅ Extraction du portefeuille
        } else {
            // ✅ Gestion des transactions "Achat : Nom : X pièces" et "Vente : Nom : X pièces"
            String[] parts = line.split(" : ");
            if (parts.length == 3) {
                String type = parts[0].trim();
                String name = parts[1].trim();
                int quantity = Integer.parseInt(parts[2].replace("pièces", "").trim());

                double price = quantity; // ✅ Prix basé sur la quantité (tu peux changer ça si nécessaire)
                transactions.add(new Transaction(name, price, type));
            }
        }
    }
}
