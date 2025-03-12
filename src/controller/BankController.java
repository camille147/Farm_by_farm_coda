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
        colNom.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("price"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));

        transactionsTable.setItems(transactions);
        loadBank();
    }

    private void loadBank() {
        try (BufferedReader reader = new BufferedReader(new FileReader("bank.txt"))) {
            reader.lines().forEach(this::parseTransaction);
        } catch (IOException e) {
            System.err.println(" Erreur lors du chargement du fichier bank.txt.");
            e.printStackTrace();
        }
    }

    private void parseTransaction(String line) {
        if (line.startsWith("Wallet:")) {
            wallet = Double.parseDouble(line.split(":")[1].trim());
        } else {
            String[] parts = line.split(" : ");
            if (parts.length == 3) {
                String type = parts[0].trim();
                String name = parts[1].trim();
                int quantity = Integer.parseInt(parts[2].replace("pièces", "").trim());

                double price = quantity;
                transactions.add(new Transaction(name, price, type));
            }
        }
    }
}
