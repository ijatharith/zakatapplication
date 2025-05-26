package com.mycompany.zakatapplication;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainMenu {
    public Parent getView(MainApp app) {

        Label header = new Label("Welcome to Zakat Application!");
        header.setStyle("-fx-font-weight: bold");
        Button btnIncomeZakat = new Button("Zakat on Income");
        Button btnZakatFitrah = new Button("Zakat Fitrah");
        Button btnAgriZakat = new Button("Agriculture Zakat");
        Button btnLogout = new Button("Logout");
        Button btnCheckTotal = new Button("Check Total");
        Button btnPayment = new Button("Payment");

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

        TextArea textArea = new TextArea();
        textArea.setMaxSize(300, 20);




        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(header, btnIncomeZakat, btnZakatFitrah, btnAgriZakat, btnPayment,  btnCheckTotal, textArea, btnLogout);


        return vbox;




    }
}