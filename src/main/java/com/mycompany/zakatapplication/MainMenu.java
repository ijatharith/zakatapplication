package com.mycompany.zakatapplication;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.swing.*;

public class MainMenu {
    public Parent getView(MainApp app) {

        Image image = new Image(getClass().getResourceAsStream("/images/systemlogo.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(347*1.5);
        imageView.setFitHeight(94*1.5);

        Button btnIncomeZakat = new Button("Zakat Income Calculator");
        btnIncomeZakat.setMaxSize(200, 10);
        Button btnZakatFitrah = new Button("Zakat Fitrah Calculator");
        btnZakatFitrah.setMaxSize(200, 10);
        Button btnAgriZakat = new Button("Zakat Agriculture Calculator");
        btnAgriZakat.setMaxSize(200, 10);
        Button btnLogout = new Button("Logout");
        btnLogout.setMaxSize(150, 10);
        Button btnCheckTotal = new Button("Check Total");
        btnCheckTotal.setMaxSize(200, 10);
        Button btnPayment = new Button("Payment");
        btnPayment.setMaxSize(200, 10);

        btnIncomeZakat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                app.setScene(app.getZakatIncomeGUI());
            }
        });

        btnZakatFitrah.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                app.setScene(app.getZakatFitrahGUI());
            }
        });

        btnAgriZakat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                app.setScene(app.getZakatAgricGUI());
            }
        });

        btnLogout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                app.setScene(app.getLoginPanelView());
            }
        });
        btnPayment.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                app.setScene(app.getPaymentGUI());
            }
        });

        TextArea textArea = new TextArea();
        textArea.setMaxSize(500,100);

        btnCheckTotal.setOnAction(e -> {
            User loggedInUser = app.getLoggedInUser();

            if (loggedInUser != null && loggedInUser.getZakatRecord() != null) {
                double total = loggedInUser.getZakatRecord().getTotalZakat();
                double fitrah = loggedInUser.getZakatRecord().getZakatFitrah();
                double income = loggedInUser.getZakatRecord().getZakatIncome();
                double agriculture = loggedInUser.getZakatRecord().getZakatAgriculture();

                textArea.setText(String.format("Zakat Fitrah: RM %.2f\n"
                        + "Zakat Income: RM %.2f\n"
                        + "Zakat Agriculture: RM %.2f\n"
                        + "Total Zakat: RM %.2f", fitrah, income, agriculture, total));
            } else {
                textArea.setText("No user or zakat record found.");
            }
        });

        Image zakatSelangorImage = new Image(getClass().getResourceAsStream("/images/zakatselangor.png"));
        ImageView zakatSelangor = new ImageView(zakatSelangorImage);
        zakatSelangor.setFitWidth(200);
        zakatSelangor.setFitHeight(50);

        Image zakatNesemilanImage = new Image(getClass().getResourceAsStream("/images/zakatn9.png"));
        ImageView zakatNesemilan = new ImageView(zakatNesemilanImage);
        zakatNesemilan.setFitWidth(200);
        zakatNesemilan.setFitHeight(50);

        Image zakatKedahImage = new Image(getClass().getResourceAsStream("/images/zakatkedah.png"));
        ImageView zakatKedah = new ImageView(zakatKedahImage);
        zakatKedah.setFitWidth(200);
        zakatKedah.setFitHeight(50);

        HBox bottomLogoBox = new HBox(zakatSelangor, zakatNesemilan, zakatKedah);
        bottomLogoBox.setAlignment(Pos.CENTER);
        bottomLogoBox.setSpacing(10);
        bottomLogoBox.setPadding(new Insets(10, 0, 20, 0));

        Label sponsorLabel = new Label("In collaboration with");
        sponsorLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        sponsorLabel.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(imageView, btnIncomeZakat, btnZakatFitrah, btnAgriZakat,  btnCheckTotal, textArea, btnPayment, btnLogout, sponsorLabel, bottomLogoBox);


        return vbox;




    }
}