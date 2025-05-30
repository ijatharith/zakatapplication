package com.mycompany.zakatapplication;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RegisterGUI {

    public Parent getView(MainApp app) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        Label header = new Label("Register a new account");
        header.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        grid.add(header, 0, 0, 2, 1);

        Label nameLabel = new Label("Full Name:");
        TextField nameField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Label confirmPasswordLabel = new Label("Confirm Password:");
        PasswordField confirmPasswordField = new PasswordField();

        VBox details = new VBox(nameLabel, emailLabel, passwordLabel, confirmPasswordLabel);
        details.setSpacing(20);
        VBox fields = new VBox(nameField, emailField, passwordField, confirmPasswordField);
        fields.setSpacing(10);

        grid.add(details, 0, 1);
        grid.add(fields, 1, 1);

        Button registerButton = new Button("Register");
        Button cancelButton = new Button("Cancel");

        HBox buttonBox = new HBox(registerButton, cancelButton);
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.BASELINE_RIGHT);
        grid.add(buttonBox, 1, 2);

        Label messageLabel = new Label();
        grid.add(messageLabel, 0, 3, 2, 1);

        registerButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            // Basic validation
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                messageLabel.setText("Please fill in all fields.");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            if (!password.equals(confirmPassword)) {
                messageLabel.setText("Passwords do not match.");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // Check if email is already registered
            boolean emailExists = app.getRegisteredUsers().stream()
                    .anyMatch(user -> user.getUsername().equalsIgnoreCase(email));

            if (emailExists) {
                messageLabel.setText("Email is already registered.");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // Create new user and add to registered users list
            User newUser = new User(email, password);
            app.registerUser(newUser);

            messageLabel.setText("Registration successful! Please login.");
            messageLabel.setStyle("-fx-text-fill: green;");

            // Clear fields
            nameField.clear();
            emailField.clear();
            passwordField.clear();
            confirmPasswordField.clear();
        });

        cancelButton.setOnAction(e -> {
            app.setScene(app.getLoginPanelView());
        });

        return grid;
    }
}
