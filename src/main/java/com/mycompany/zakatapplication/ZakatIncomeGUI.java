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
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ZakatIncomeGUI {
    public Parent getView(MainApp app) {
        // Header
        HBox headerbox = new HBox();
        headerbox.setPadding(new Insets(10));
        headerbox.setAlignment(Pos.CENTER);
        headerbox.setSpacing(10);

        //ImageView logoview = new ImageView(new Image("/images/systemlogo.png")); // Update path as needed
        //logoview.setFitHeight(120);
        //logoview.setPreserveRatio(true);

        //headerbox.getChildren().addAll(logoview);

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

        HBox bttnbox = new HBox(10, bttnback);
        bttnbox.setAlignment(Pos.CENTER);

        // 3 Logos
        HBox logobox = new HBox(20);
        logobox.setAlignment(Pos.CENTER);
        logobox.setPadding(new Insets(20));

        // ImageView img1 = new ImageView(new Image("C:\\Users\\itzki\\Downloads\\WhatsApp Image 2025-06-11 at 2.57.27 AM.jpeg"));
        // ImageView img2 = new ImageView(new Image("C:\\Users\\itzki\\Downloads\\WhatsApp Image 2025-06-11 at 3.00.08 AM.jpeg"));
        // ImageView img3 = new ImageView(new Image("C:\\Users\\itzki\\Downloads\\WhatsApp Image 2025-06-11 at 3.00.15 AM.jpeg"));

        //for (ImageView img : new ImageView[]{img1, img2, img3}) {
        //    img.setFitWidth(200);
        //    img.setPreserveRatio(true);
        // }

        // logobox.getChildren().addAll(img1, img2, img3);

        // Side panels
        VBox leftpanel = new VBox(10);
        leftpanel.setPadding(new Insets(15));
        leftpanel.setStyle("-fx-background-color: #CCC;"); // Light gray color
        leftpanel.setPrefWidth(200);

        Label tipstitle = new Label("Zakat Income Tips:");
        tipstitle.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label tip1 = new Label("• Zakat is 2.5% of your surplus income.");
        Label tip2 = new Label("• Only pay if your wealth exceeds Nisab.");
        Label tip3 = new Label("• Expenses are deductible before Zakat.");

        leftpanel.getChildren().addAll(tipstitle, tip1, tip2, tip3);

        VBox rightpanel = new VBox();
        rightpanel.setPadding(new Insets(0));
        rightpanel.setStyle("-fx-background-color: #CCC;");
        rightpanel.setAlignment(Pos.CENTER);
        rightpanel.setPrefWidth(200);

        //ImageView gifview = new ImageView("https://i.pinimg.com/originals/da/3f/c3/da3fc37c777bdc2e8f1322298f80d610.gif");
        //gifview.setFitWidth(200);
        //gifview.setFitHeight(420);
        //gifview.setPreserveRatio(false);

        //rightpanel.getChildren().add(gifview);

        // Grouping All Together
        BorderPane mainlayout = new BorderPane();
        mainlayout.setPadding(new Insets(20));
        mainlayout.setTop(headerbox);
        mainlayout.setCenter(grid);
        mainlayout.setLeft(leftpanel);
        mainlayout.setRight(rightpanel);

        // Wrap everything in VBox
        VBox bottomSection = new VBox(10, bttnbox, logobox);
        bottomSection.setAlignment(Pos.CENTER);
        mainlayout.setBottom(bottomSection);

        return mainlayout;
    }
}