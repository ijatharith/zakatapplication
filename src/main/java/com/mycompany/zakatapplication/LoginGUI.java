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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class LoginGUI {
    public Parent getView(MainApp app) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        Image image = new Image(getClass().getResourceAsStream("/images/systemlogo.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(347*1.5);
        imageView.setFitHeight(94*1.5);

        HBox topLogoBox = new HBox(imageView);
        topLogoBox.setAlignment(Pos.CENTER);
        topLogoBox.setPadding(new Insets(10, 0, 20, 0)); // space between logo and form

        Label emailLabel = new Label("Email: ");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password: ");
        PasswordField passwordField = new PasswordField();

        VBox details = new VBox(emailLabel, passwordLabel);
        details.setSpacing(20);
        VBox fields = new VBox(emailField, passwordField);
        fields.setSpacing(10);

        grid.add(details, 0, 1);
        grid.add(fields, 1, 1);

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

        Label sponsorLabel = new Label("In collaboration with");
        sponsorLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        sponsorLabel.setAlignment(Pos.CENTER);

        // Setup a single Admin instance (only one admin allowed)
        Admin admin = new Admin();

        loginButton.setOnAction(e -> {
            String email = emailField.getText();
            String pass = passwordField.getText();

            // Check if login is admin
            if (admin.login(email, pass)) {
                messageLabel.setText("Admin login successful!");
                messageLabel.setStyle("-fx-text-fill: green;");
                grid.add(messageLabel, 1, 3);

                app.setScene(app.getAdminGUI());
            } else {
                // Check normal users from the registered users list
                boolean loginSuccess = false;
                User loggedInUser = null;

                for (User user : app.getRegisteredUsers()) {
                    if (user.login(email, pass)) {
                        loginSuccess = true;
                        loggedInUser = user;
                        break;
                    }
                }

                if (loginSuccess) {
                    messageLabel.setText("User login successful!");
                    messageLabel.setStyle("-fx-text-fill: green;");
                    grid.add(messageLabel, 1, 3);

                    app.setLoggedInUser(loggedInUser);
                    app.setScene(app.getMainMenuView());
                } else {
                    messageLabel.setText("Invalid credentials.");
                    messageLabel.setStyle("-fx-text-fill: red;");
                    grid.add(messageLabel, 1, 3);
                }
            }
        });

        registerButton.setOnAction(e -> {
            app.setScene(app.getRegisterPanelView());
        });

        cancelButton.setOnAction(e -> {
            app.setScene(app.getLoginPanelView());
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

        VBox root = new VBox(topLogoBox, grid, sponsorLabel, bottomLogoBox);
        root.setAlignment(Pos.CENTER);


        return root;
    }
}

