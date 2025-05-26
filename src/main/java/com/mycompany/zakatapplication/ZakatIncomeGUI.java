package com.mycompany.zakatapplication;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

        Label labelexpense = new Label("Total Expenses (RM):");
        TextField tfexpense = new TextField();

        Label labelresult = new Label("Result:");
        Label taresult = new Label();


        Button bttncalculate = new Button("Calculate Zakat");
        Button bttnclear = new Button("Clear");

        // Calculate action
        bttncalculate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    double income = Double.parseDouble(tfincome.getText());
                    double expenses = Double.parseDouble(tfexpense.getText());

                    if (income < 0 || expenses < 0) {
                        taresult.setText("Error: Please enter positive numbers only.");
                        return;
                    }

                    double netIncome = income - expenses;
                    double amount;

                    if (netIncome >= 4000) {
                        amount = netIncome * 0.025;
                        taresult.setText(String.format("Your Zakat Income payment: RM %.2f", amount));
                    } else {
                        taresult.setText("You are not liable to pay Zakat on income.");
                    }

                } catch (NumberFormatException ex) {
                    taresult.setText("Error: Invalid input. Please enter valid numbers.");
                }
            }
        });

        // Recalculate action
        bttnclear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                tfincome.clear();
                tfexpense.clear();
                //taresult.clear();//
            }
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

        // --- Bottom Buttons ---
        Button bttnback = new Button("Back");
        Button bttnnext = new Button("Next");
        Button bttnexit = new Button("Exit");

        bttnback.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                app.setScene(app.getMainMenuView());
            }
        });

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(bttnback);

        // --- Main Layout Wrapper ---
        VBox root = new VBox(grid, buttonBox);
        root.setSpacing(10);

        return root;

    }


}
