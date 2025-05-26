package com.mycompany.zakatapplication;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ZakatAgricultureGUI {
    private static final double NISAB_KG = 937.5;
    private static final double WHEAT_PRICE = 1.30;

    public Parent getView(MainApp app) {
        Label lblTitle = new Label("Agriculture Zakat Calculator");
        lblTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label lblHarvest = new Label("Total Harvested Wheat (kg):");
        TextField tfHarvest = new TextField();
        tfHarvest.setPromptText("e.g. 1000");

        Label lblIrrigation = new Label("Irrigation Type:");
        ComboBox<String> cbIrrigation = new ComboBox<>();
        cbIrrigation.getItems().addAll("Natural (10%)", "Artificial (5%)");
        cbIrrigation.setPromptText("Select irrigation type");

        Button btnCheck = new Button("Check Zakat");
        Label lblResult = new Label();

        btnCheck.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    double harvestKg = Double.parseDouble(tfHarvest.getText());
                    String irrigationType = cbIrrigation.getValue();

                    if (irrigationType == null) {
                        lblResult.setText("Please select an irrigation type.");
                        return;
                    }

                    if (harvestKg < 0) {
                        lblResult.setText("Please enter a positive number for harvest.");
                        return;
                    }

                    if (harvestKg <= NISAB_KG) {
                        lblResult.setText("No zakat due. Harvest is below nisab (937.5 kg).\n");
                        return;
                    }

                    double rate;
                    if (irrigationType.contains("Natural")) {
                        rate = 0.10;
                    } else {
                        rate = 0.05;
                    }

                    double zakatKg = harvestKg * rate;
                    double zakatRM = zakatKg * WHEAT_PRICE;
                    lblResult.setText(String.format("Zakat Due: RM %.2f", zakatRM));

                } catch (NumberFormatException ex) {
                    lblResult.setText("Please enter valid numeric values.");
                }
            }
        });

        // navigation buttons
        Button btnNext = new Button("Next");
        Button btnBack = new Button("Back");
        Button btnExit = new Button("Exit");

        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                app.setScene(app.getMainMenuView());
            }
        });

        HBox hboxNavi = new HBox(15, btnBack);
        hboxNavi.setAlignment(Pos.CENTER);

        VBox vboxLayout = new VBox(10);
        vboxLayout.setPadding(new Insets(20));
        vboxLayout.setAlignment(Pos.TOP_LEFT);

        vboxLayout.getChildren().addAll(lblTitle,
                lblHarvest, tfHarvest,
                lblIrrigation, cbIrrigation,
                btnCheck,
                lblResult,
                hboxNavi


        );

        return vboxLayout;
    }
}
