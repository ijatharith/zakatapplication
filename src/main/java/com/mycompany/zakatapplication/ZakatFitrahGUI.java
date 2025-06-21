package com.mycompany.zakatapplication;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class ZakatFitrahGUI {
    // States and their respective values
    private static final String[] STATES = { "Kedah", "Selangor", "Negeri Sembilan" };
    private static final String[] RICE_TYPES = { "Economical Rice", "Regular Rice", "Premium Rice" };

    public Parent getView(MainApp app) {
        // Main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));

        // ===== TOP: Company Logo =====
        //ImageView logoBanner = new ImageView("/images/systemlogo.png"); // adjust path
        //logoBanner.setFitHeight(120);
        //logoBanner.setPreserveRatio(true);

        //HBox topBanner = new HBox(logoBanner);
        //topBanner.setAlignment(Pos.CENTER);
        //mainLayout.setTop(topBanner);

        // ===== LEFT: Tips Section =====
        VBox tipsBox = new VBox(10);
        tipsBox.setPadding(new Insets(10));
        tipsBox.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: #ccc; -fx-border-width: 1px;");
        tipsBox.setMaxWidth(200);

        Label tipsTitle = new Label("Tips for Zakat Fitrah:");
        tipsTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        tipsBox.getChildren().add(tipsTitle);

        String[] tips = {
                "Ensure all dependents are included.",
                "Choose the correct state for accurate pricing.",
                "Select the appropriate rice type based on your preference.",
                "Verify the total amount before submission.",
                "All source are from zakat website of each state and news",
                "Economical Rice is local rice or import rice",
                "Regular Rice is Beras Perang and other with same level",
                "Premium Rice is Beras Basmathi and other with same level",
        };
        for (String tip : tips) {
            Label tipLabel = new Label("• " + tip);
            tipLabel.setWrapText(true);
            tipsBox.getChildren().add(tipLabel);
        }
        mainLayout.setLeft(tipsBox);

        // ==== RIGHT : Facts about Zakat === //
        VBox FactBox = new VBox(10);
        FactBox.setPadding(new Insets(10));
        FactBox.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: #ccc; -fx-border-width: 1px;");
        FactBox.setMaxWidth(200);

        Label FactTitle = new Label("Facts for Zakat Fitrah:");
        FactTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        FactBox.getChildren().add(FactTitle);

        String[] facts = {
                "Mandatory:\n" + "Every able Muslim, regardless of age or gender, is obligated to pay Zakat al-Fitr. ",
                "Purification:\n" +
                        "It purifies the fasting individual from any minor sins or imperfections during Ramadan. ",
                "Social Welfare:\n" +
                        "It ensures that the poor can also celebrate Eid al-Fitr by providing them with essential food or the means to purchase it. ",
                "Benefits:\n" +
                        "Zakat al-Fitr is considered a form of purification and a way to show compassion and generosity towards those in need. "
        };
        for (String fact : facts) {
            Label FactLabel = new Label("• " + fact);
            FactLabel.setWrapText(true);
            FactBox.getChildren().add(FactLabel);
        }
        mainLayout.setRight(FactBox);

        // ===== CENTER:  Zakat calculator =====
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setFillWidth(true);

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
        dependentsField.setPrefWidth(30);
        dependentsBox.getChildren().addAll(dependentsLabel, dependentsField);
        dependentsBox.setVisible(false);
        dependentsBox.setAlignment(Pos.CENTER);

        withDependents.setOnAction(e -> dependentsBox.setVisible(true));
        myself.setOnAction(e -> dependentsBox.setVisible(false));

        Label stateLabel = new Label("Select State:");
        ComboBox<String> stateComboBox = new ComboBox<>();
        stateComboBox.getItems().addAll(STATES);
        stateComboBox.setValue(STATES[0]);

        Label riceTypeLabel = new Label("Select Rice Type:");
        ComboBox<String> riceTypeComboBox = new ComboBox<>();
        riceTypeComboBox.getItems().addAll(RICE_TYPES);
        riceTypeComboBox.setValue(RICE_TYPES[0]);

        Button calculateBtn = new Button("Calculate Zakat");
        Label resultLabel = new Label();

        calculateBtn.setOnAction(e -> {
            int totalPeople = 0;

            if (myself.isSelected()) {
                totalPeople = 1;
            } else if (withDependents.isSelected()) {
                try {
                    int dependents = Integer.parseInt(dependentsField.getText());
                    if (dependents < 0) {
                        resultLabel.setText("Number of dependents cannot be negative.");
                        return;
                    }
                    totalPeople = 1 + dependents;
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Please enter a valid number of dependents.");
                    return;
                }
            } else {
                resultLabel.setText("Please select an option first.");
                return;
            }

            String selectedState = stateComboBox.getValue();
            String selectedRiceType = riceTypeComboBox.getValue();

            if (selectedState == null || selectedState.isEmpty()) {
                resultLabel.setText("Please select a valid state.");
                return;
            }

            if (selectedRiceType == null || selectedRiceType.isEmpty()) {
                resultLabel.setText("Please select a valid rice type.");
                return;
            }

            ZakatFitrah zakatCalc = new ZakatFitrah(totalPeople, selectedState, selectedRiceType);
            double amount = zakatCalc.calculateZakat();

            resultLabel.setText(String.format("Total Zakat Fitrah for %d person(s): RM %.2f", totalPeople, amount));

            User currentUser = app.getLoggedInUser();
            if (currentUser != null) {
                currentUser.getZakatRecord().setZakatFitrah(amount);
            }
        });

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> app.setScene(app.getMainMenuView()));

        HBox navButtons = new HBox(15, backBtn);
        navButtons.setAlignment(Pos.CENTER);

        root.getChildren().addAll(
                title, myself, withDependents, dependentsBox,
                stateLabel, stateComboBox, riceTypeLabel, riceTypeComboBox,
                calculateBtn, resultLabel, new Separator(), navButtons
        );

        mainLayout.setCenter(root);

        // ===== BOTTOM: Collaborator Logos =====
        //ImageView logo1 = new ImageView("/images/zakatkedah.png");
        //ImageView logo2 = new ImageView("/images/zakatselangor.png");
        //ImageView logo3 = new ImageView("/images/zakatn9.png");

        //logo1.setFitHeight(40);
        //logo1.setPreserveRatio(true);
        //logo2.setFitHeight(40);
        //logo2.setPreserveRatio(true);
        //logo3.setFitHeight(40);
        //logo3.setPreserveRatio(true);

        //HBox logoBox = new HBox(30, logo1, logo2, logo3);
        //logoBox.setAlignment(Pos.CENTER);
        //logoBox.setPadding(new Insets(10, 0, 0, 0));

        //mainLayout.setBottom(logoBox);

        return mainLayout;
    }
}

