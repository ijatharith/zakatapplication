package com.mycompany.zakatapplication;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ZakatIncomeGUI {
    public Parent getView(MainApp app) {

        // Main layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Labels and Fields
        Label labelincome = new Label("Monthly Income (RM):");
        TextField tfincome = new TextField();
        tfincome.setPromptText("e.g. 5000.00");

        Label labelexpense = new Label("Total Expenses (RM):");
        TextField tfexpense = new TextField();
        tfexpense.setPromptText("e.g. 1500.00");

        Label labelresult = new Label("Result:");
        Label taresult = new Label();

        Button bttncalculate = new Button("Calculate Zakat");
        Button bttnclear = new Button("Clear");

        // Calculate action
        bttncalculate.setOnAction((ActionEvent e) -> {
            try {
                double income = Double.parseDouble(tfincome.getText());
                double expenses = Double.parseDouble(tfexpense.getText());

                if (income < 0 || expenses < 0) {
                    taresult.setText("Error: Please enter positive numbers only.");
                    return;
                }

                ZakatIncome zakatCalc = new ZakatIncome(income, expenses);
                double zakatAmount = zakatCalc.calculateZakat();

                String msg = zakatAmount > 0 ?
                        String.format("Your Zakat Income payment: RM %.2f", zakatAmount) :
                        "You are not liable to pay Zakat on income.";
                taresult.setText(msg);

                User currentUser = app.getLoggedInUser();
                if (currentUser != null) {
                    UserZakatRecord record = currentUser.getZakatRecord();
                    record.setZakatIncome(zakatAmount);
                }

            } catch (NumberFormatException ex) {
                taresult.setText("Error: Invalid input. Please enter valid numbers.");
            }

        });

        // Clear action
        bttnclear.setOnAction((ActionEvent e) -> {
            tfincome.clear();
            tfexpense.clear();
            taresult.setText("");
        });

        // Add components to layout
        grid.add(labelincome, 0, 0);
        grid.add(tfincome, 1, 0);
        grid.add(labelexpense, 0, 1);
        grid.add(tfexpense, 1, 1);
        grid.add(bttncalculate, 1, 2);
        grid.add(bttnclear, 2, 1);
        grid.add(labelresult, 0, 3);
        grid.add(taresult, 0, 4, 3, 1);

        // Bottom Buttons
        Button bttnback = new Button("Back");
        bttnback.setOnAction((ActionEvent e) -> {
            app.setScene(app.getMainMenuView());
        });

        Button bttnExit = new Button("Exit"); // Added Exit button


        HBox buttonBox = new HBox(10, bttnback, bttnExit); // Added Exit button
        buttonBox.setAlignment(Pos.CENTER);

        // Wrap everything in VBox
        VBox root = new VBox(10, grid, buttonBox);
        return root;
    }
}