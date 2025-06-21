package com.mycompany.zakatapplication;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ZakatAgricultureGUI {

    private ComboBox<String> stateCb;
    private ComboBox<String> irrigationCb;
    private Label nisabInfoLbl;
    private TextField harvestTf;
    private Label resultLbl;

    public Parent getView(MainApp app) {
        // System logo at the top
        ImageView logoView = null;
        try {
            Image logoImg = new Image(getClass().getResourceAsStream("/images/systemlogo.png"));
            logoView = new ImageView(logoImg);
            logoView.setFitWidth(400);
            logoView.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("Error loading logo: " + e.getMessage());
        }

        HBox topLogoBox = new HBox(logoView);
        topLogoBox.setAlignment(Pos.CENTER);
        topLogoBox.setPadding(new Insets(10));

        // Input section (center column)
        Label titleLbl = new Label("Agriculture Zakat Calculator");
        titleLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label stateLbl = new Label("Select State:");
        stateCb = new ComboBox<>();
        stateCb.getItems().addAll("Kedah", "Selangor", "Negeri Sembilan");
        stateCb.setPromptText("Select state");

        Label harvestLbl = new Label("Total Harvested Paddy (kg):");
        harvestTf = new TextField();
        harvestTf.setPrefWidth(200);
        harvestTf.setPromptText("e.g. 1500");

        Label irrigationLbl = new Label("Irrigation Type:");
        irrigationCb = new ComboBox<>();
        irrigationCb.setPromptText("Select irrigation type");

        nisabInfoLbl = new Label();
        nisabInfoLbl.setStyle("-fx-font-size: 12px;");

        resultLbl = new Label();
        resultLbl.setStyle(
                "-fx-font-size: 13px; " +
                        "-fx-background-color: #eef; " +
                        "-fx-padding: 10px; " +
                        "-fx-border-color: #99c; " +
                        "-fx-border-radius: 5px;"
        );
        resultLbl.setWrapText(true);
        resultLbl.setMaxWidth(250);

        stateCb.setOnAction(new StateChangeHandler());

        Button btnCheck = new Button("Check Zakat");
        btnCheck.setOnAction(new CheckZakatHandler(app));
        btnCheck.setPrefWidth(150);

        Button btnBack = new Button("Back");
        btnBack.setOnAction(e -> app.setScene(app.getMainMenuView()));
        btnBack.setPrefWidth(150);

        VBox vboxInputs = new VBox(10,
                titleLbl, stateLbl, stateCb,
                nisabInfoLbl, harvestLbl, harvestTf,
                irrigationLbl, irrigationCb,
                btnCheck, resultLbl, btnBack
        );
        vboxInputs.setPadding(new Insets(20));
        vboxInputs.setAlignment(Pos.CENTER_LEFT);

        HBox centerBox = new HBox(vboxInputs);
        centerBox.setAlignment(Pos.CENTER);

        // Bilingual info (right)
        Label lblInfo = new Label(
                "Minimum zakat threshold (Nisab):\n" +
                        "• Kedah: 1300.49 kg (Based on LZNK)\n" +
                        "• Selangor & Negeri Sembilan: 1306 kg (Based on LZS & PZNS)\n\n" +
                        "Paddy floor price: RM1.50/kg – Source: Ministry of Agriculture (KPKM, 2025)\n\n" +
                        "Ambang zakat padi:\n" +
                        "• Kedah: 1300.49 kg (Rujukan LZNK)\n" +
                        "• Selangor & Negeri Sembilan: 1306 kg (Rujukan LZS & PZNS)\n\n" +
                        "Harga lantai padi: RM1.50/kg – Sumber: KPKM (2025)\n\n" +
                        "Note:\nFocus on paddy is due to its significance in Malaysia’s food security."
        );
        lblInfo.setWrapText(true);
        lblInfo.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");

        VBox vboxInfo = new VBox(lblInfo);
        vboxInfo.setPadding(new Insets(20));
        vboxInfo.setMaxWidth(320);

        // Bottom logos (larger)
        HBox hboxLogos = new HBox(30);
        hboxLogos.setAlignment(Pos.CENTER);
        hboxLogos.setPadding(new Insets(15));

        try {
            ImageView ivLZS = new ImageView(new Image(getClass().getResourceAsStream("/logo_lzs.png")));
            ImageView ivPZNS = new ImageView(new Image(getClass().getResourceAsStream("/logo_pzns.png")));
            ImageView ivLZNK = new ImageView(new Image(getClass().getResourceAsStream("/logo_lznk.png")));

            ivLZS.setFitWidth(150); ivLZS.setPreserveRatio(true);
            ivPZNS.setFitWidth(150); ivPZNS.setPreserveRatio(true);
            ivLZNK.setFitWidth(150); ivLZNK.setPreserveRatio(true);

            hboxLogos.getChildren().addAll(ivLZS, ivPZNS, ivLZNK);
        } catch (Exception ex) {
            System.out.println("Error loading bottom logos: " + ex.getMessage());
        }

        // Layout structure
        BorderPane root = new BorderPane();
        root.setTop(topLogoBox);
        root.setCenter(centerBox); // Center input section
        root.setRight(vboxInfo);
        root.setBottom(hboxLogos);

        return root;
    }

    // Handle state selection
    private class StateChangeHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String state = stateCb.getValue();
            irrigationCb.getItems().clear();

            if ("Kedah".equals(state)) {
                nisabInfoLbl.setText("Nisab: 1300.49 kg");
                irrigationCb.getItems().addAll("Natural (10%)", "Semi-Natural (7.5%)", "Artificial (5%)");
            } else {
                nisabInfoLbl.setText("Nisab: 1306 kg");
                irrigationCb.getItems().addAll("Natural (10%)", "Artificial (5%)");
            }
        }
    }

    // Handle Zakat Calculation
    private class CheckZakatHandler implements EventHandler<ActionEvent> {
        private final MainApp app;

        public CheckZakatHandler(MainApp app) {
            this.app = app;
        }

        @Override
        public void handle(ActionEvent event) {
            try {
                String state = stateCb.getValue();
                String irrigationType = irrigationCb.getValue();
                double harvestKg = Double.parseDouble(harvestTf.getText());

                if (state == null || irrigationType == null) {
                    resultLbl.setText("Please select both state and irrigation type.");
                    return;
                }

                if (harvestKg < 0) {
                    resultLbl.setText("Please enter a positive number.");
                    return;
                }

                double nisab = "Kedah".equals(state) ? 1300.49 : 1306;

                if (harvestKg < nisab) {
                    resultLbl.setText("No zakat due. Harvest is below nisab.");
                    return;
                }

                double rate;
                if ("Kedah".equals(state)) {
                    if (irrigationType.contains("Natural") && !irrigationType.contains("Semi")) {
                        rate = 0.10;
                    } else if (irrigationType.contains("Semi")) {
                        rate = 0.075;
                    } else {
                        rate = 0.05;
                    }
                } else {
                    rate = irrigationType.contains("Natural") ? 0.10 : 0.05;
                }

                ZakatAgriculture zakatCalc = new ZakatAgriculture(harvestKg, rate);
                double zakatRM = zakatCalc.calculateZakat();
                resultLbl.setText(String.format("Zakat Due: RM %.2f", zakatRM));

                User currentUser = app.getLoggedInUser();
                if (currentUser != null) {
                    currentUser.getZakatRecord().setZakatAgriculture(zakatRM);
                }

            } catch (NumberFormatException ex) {
                resultLbl.setText("Please enter valid numeric values.");
            }
        }
    }
}
