package com.mycompany.zakatapplication;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ZakatAgricultureGUI {
    private static final double NISAB_KG = 937.5;

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

        btnCheck.setOnAction((ActionEvent e) -> {
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
                    lblResult.setText("No zakat due. Harvest is below nisab (937.5 kg).");
                    return;
                }

                double rate = irrigationType.contains("Natural") ? 0.10 : 0.05;

                // Use your abstracted class
                ZakatAgriculture zakatCalc = new ZakatAgriculture(harvestKg, rate);
                double zakatRM = zakatCalc.calculateZakat();

                lblResult.setText(String.format("Zakat Due: RM %.2f", zakatRM));

                User currentUser = app.getLoggedInUser();
                if (currentUser != null) {
                    currentUser.getZakatRecord().setZakatAgriculture(zakatRM);
                }

            } catch (NumberFormatException ex) {
                lblResult.setText("Please enter valid numeric values.");
            }
        });

        // Navigation Buttons
        Button btnNext = new Button("Next");
        Button btnBack = new Button("Back");
        Button btnExit = new Button("Exit");

        btnBack.setOnAction(e -> app.setScene(app.getMainMenuView()));
        btnExit.setOnAction(e -> System.exit(0));
        // TODO: Define action for btnNext if needed

        HBox hboxNavi = new HBox(15, btnBack, btnNext, btnExit);
        hboxNavi.setAlignment(Pos.CENTER);

        VBox vboxLayout = new VBox(10);
        vboxLayout.setPadding(new Insets(20));
        vboxLayout.setAlignment(Pos.TOP_LEFT);

        vboxLayout.getChildren().addAll(
                lblTitle,
                lblHarvest, tfHarvest,
                lblIrrigation, cbIrrigation,
                btnCheck,
                lblResult,
                hboxNavi
        );

        return vboxLayout;
    }
}
