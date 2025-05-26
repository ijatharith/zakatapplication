package com.mycompany.zakatapplication;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ZakatFitrahGUI {
    private static final double RICE_PRICE = 1.83; // RM per kg
    private static final double FITRAH_PER_PERSON_KG = 2.5;

    public Parent getView(MainApp app) {

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_LEFT);

        Label title = new Label("Calculate Zakat Fitrah");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ToggleGroup group = new ToggleGroup();
        RadioButton myself = new RadioButton("Myself");
        RadioButton withDependents = new RadioButton("Me and my dependents");
        myself.setToggleGroup(group);
        withDependents.setToggleGroup(group);

        HBox dependentsBox = new HBox(10);
        Label dependentsLabel = new Label("Number of dependents:");
        TextField dependentsField = new TextField();
        dependentsField.setPromptText("e.g. 2");
        dependentsField.setPrefWidth(60);
        dependentsBox.getChildren().addAll(dependentsLabel, dependentsField);
        dependentsBox.setVisible(false);

        withDependents.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dependentsBox.setVisible(true);
            }
        });

        myself.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dependentsBox.setVisible(false);
            }
        });

        Button calculateBtn = new Button("Calculate Zakat");
        Label resultLabel = new Label();

        calculateBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (myself.isSelected()) {
                    double amount = RICE_PRICE * FITRAH_PER_PERSON_KG;
                    String message = String.format("Your Zakat Fitrah: RM %.2f", amount);
                    resultLabel.setText(message);
                } else if (withDependents.isSelected()) {
                    try {
                        int dependents = Integer.parseInt(dependentsField.getText());
                        int totalPeople = 1 + dependents;
                        double amount = totalPeople * RICE_PRICE * FITRAH_PER_PERSON_KG;
                        String message = String.format("Total Zakat Fitrah for %d people: RM %.2f", totalPeople, amount);
                        resultLabel.setText(message);
                    } catch (NumberFormatException ex) {
                        resultLabel.setText("Please enter a valid number of dependents.");
                    }
                } else {
                    resultLabel.setText("Please select an option first.");
                }
            }
        });


        Button backBtn = new Button("Back");

        // Placeholder for action handling

        backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                app.setScene(app.getMainMenuView());
            }
        });





        HBox navButtons = new HBox(15, backBtn);
        navButtons.setAlignment(Pos.CENTER);

        root.getChildren().addAll(
                title,
                myself,
                withDependents,
                dependentsBox,
                calculateBtn,
                resultLabel,
                new Separator(),
                navButtons
        );

        return root;

    }
}
