package com.mycompany.zakatapplication;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PaymentGUI {
    public Parent getView(MainApp app) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));

        Label header = new Label("Payment Interface");
        header.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        HBox headerBox = new HBox(header);
        headerBox.setAlignment(Pos.CENTER);
        grid.add(headerBox, 0, 0);

        Label chooseZakat = new Label("Which zakat do you want to pay?");

        CheckBox zakatFitrah = new CheckBox("Zakat fitrah");
        CheckBox zakatIncome =  new CheckBox("Zakat income");
        CheckBox zakatAgriculture = new CheckBox("Zakat agriculture");

        VBox zakatType = new VBox(zakatFitrah, zakatIncome, zakatAgriculture);
        zakatType.setSpacing(10);
        zakatType.setPadding(new Insets(10));


        Button checkTotalButton = new Button("Check Total");
        Button payTotalButton = new Button("Pay Total");

        HBox buttonBox = new HBox(checkTotalButton, payTotalButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(10));



        grid.add(chooseZakat, 0, 1);
        grid.add(zakatType, 0, 2);
        grid.add(buttonBox, 0, 3);

        TextArea zakatResult = new TextArea();
        zakatResult.setMaxSize(300, 50);
        grid.add(zakatResult, 0, 4);

        Button backBtn = new Button("Back");
        HBox backBtnBox =  new HBox(backBtn);
        backBtnBox.setAlignment(Pos.CENTER);
        backBtnBox.setSpacing(10);
        backBtnBox.setPadding(new Insets(10));


        backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                app.setScene(app.getMainMenuView());
            }
        });

        return grid;
    }

}
