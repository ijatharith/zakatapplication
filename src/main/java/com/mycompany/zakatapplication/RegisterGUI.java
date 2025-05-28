package com.mycompany.zakatapplication;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RegisterGUI {
    public Parent getView(MainApp app) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        Label header = new Label("Register a new account");
        grid.add(header, 0, 0);

        Label nameLabel = new Label("Full Name:");
        TextField nameField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Label comfirmPasswordLabel = new Label("Confirm Password:");
        PasswordField comfirmPasswordField = new PasswordField();

        VBox details = new VBox(nameLabel, emailLabel, passwordLabel, comfirmPasswordLabel);
        details.setSpacing(20);
        VBox field = new VBox(nameField, emailField, passwordField, comfirmPasswordField);
        field.setSpacing(10);

        grid.add(details, 0, 1);
        grid.add(field, 1, 1);

        Button registerButton = new Button("Register");
        Button cancelButton = new Button("Close");

        HBox button = new HBox(registerButton, cancelButton);
        button.setSpacing(10);
        button.setAlignment(Pos.BASELINE_RIGHT);

        Label messageLabel = new Label();

        Button test = new Button("test");




        grid.add(button, 1, 2);

        registerButton.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();


            if (email.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Please fill in all fields.");
                messageLabel.setStyle("-fx-text-fill: red;");
                grid.add(messageLabel, 0, 3);
            } else {
                User updateUser = new User();
                updateUser.setEmail(email);
                updateUser.setPassword(password);

                app.setRegisteredUser(updateUser);


                messageLabel.setText("Registration successful!");
                messageLabel.setStyle("-fx-text-fill: green;");
                grid.add(messageLabel, 0, 3);
            }
        });


        cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e) {
                app.setScene(app.getLoginPanelView());
            }
        });
        grid.add(test, 0, 4);

        return grid;
    }
}
