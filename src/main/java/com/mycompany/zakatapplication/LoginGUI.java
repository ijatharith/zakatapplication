package com.mycompany.zakatapplication;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LoginGUI {
    public Parent getView(MainApp app) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));


        Label header = new Label("Welcome to the \nZakat Calculation System");
        header.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-alignment: center;");
        header.setPadding(new Insets(5));
        grid.add(header, 1, 0);

        Label emailLabel = new Label("Email: ");
        TextField emailField = new TextField();


        Label password = new Label("Password: ");
        PasswordField passwordField = new PasswordField();

        VBox details = new VBox(emailLabel, password);
        details.setSpacing(20);
        VBox field = new VBox(emailField, passwordField);
        field.setSpacing(10);

        grid.add(details, 0, 1);
        grid.add(field, 1, 1);

        Button loginButton = new Button("Login");
        Button cancelButton = new Button("Cancel");


        HBox buttonBox = new HBox(loginButton, cancelButton);
        buttonBox.setAlignment(Pos.BASELINE_RIGHT);
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(5));

        grid.add(buttonBox, 1, 2);

        Label newUserQuery = new Label("Are you a new user? Register Here");
        Button registerButton = new Button("Register");

        VBox newUserQueryBox = new VBox(newUserQuery, registerButton);
        newUserQueryBox.setAlignment(Pos.CENTER);
        newUserQueryBox.setSpacing(5);
        newUserQueryBox.setPadding(new Insets(10));

        Label messageLabel = new Label();

        grid.add(newUserQueryBox, 2, 1);

        loginButton.setOnAction(e -> {
            String email = emailField.getText();
            String pass = passwordField.getText();

            User updateUser = app.getRegisteredUser();

            if (updateUser.getEmail().equals(email) && updateUser.getPassword().equals(pass)) {
                messageLabel.setText("Login successful!");
                messageLabel.setStyle("-fx-text-fill: green;");
                app.setScene(app.getMainMenuView());
            } else {
                messageLabel.setText("Invalid credentials.");
                messageLabel.setStyle("-fx-text-fill: red;");
                grid.add(messageLabel, 1, 3);
            }
        });

        registerButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e) {
                app.setScene(app.getRegisterPanelView());
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e) {
                app.setScene(app.getLoginPanelView());
            }
        });



        return grid;
    }
}