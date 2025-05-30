package com.mycompany.zakatapplication;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

public class AdminGUI {
    public Parent getView(MainApp app) {
        VBox root = new VBox();
        root.setPadding(new Insets(20));
        root.setSpacing(15);
        root.setAlignment(Pos.TOP_CENTER);

        Label welcomeLabel = new Label("Welcome, Admin!");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Search Components
        Label searchLabel = new Label("Search Payment History by Username:");
        TextField tfSearchUsername = new TextField();
        tfSearchUsername.setPromptText("Enter username...");

        Button btnSearchHistory = new Button("Search History");
        TextArea zakatResult = new TextArea();
        zakatResult.setWrapText(true);
        zakatResult.setPrefHeight(200);
        zakatResult.setEditable(false);

        VBox searchBox = new VBox(8, searchLabel, tfSearchUsername, btnSearchHistory);
        searchBox.setPadding(new Insets(10));
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setMaxWidth(400);

        btnSearchHistory.setOnAction(e -> {
            String searchUsername = tfSearchUsername.getText().trim();
            if (searchUsername.isEmpty()) {
                zakatResult.setText("Please enter a username.");
                return;
            }

            String searchFile = "history_" + searchUsername + ".txt";
            try {
                String content = Files.readString(Paths.get(searchFile));
                zakatResult.setText("Payment History for " + searchUsername + ":\n\n" + content);
            } catch (IOException ex) {
                zakatResult.setText("No history found for username: " + searchUsername);
            }
        });

        // View Total Zakat Collected Button
        Button btnTotalZakat = new Button("View Total Zakat Collected");
        btnTotalZakat.setOnAction(e -> {
            double totalZakat = 0.0;

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("."), "history_*.txt")) {
                for (Path entry : stream) {
                    try (Stream<String> lines = Files.lines(entry)) {
                        for (String line : (Iterable<String>) lines::iterator) {
                            if (line.startsWith("Zakat Paid: RM")) {
                                String amountStr = line.replace("Zakat Paid: RM", "").trim();
                                try {
                                    totalZakat += Double.parseDouble(amountStr);
                                } catch (NumberFormatException ignore) {}
                            }
                        }
                    } catch (IOException ex) {
                        // Handle reading individual files
                        zakatResult.appendText("Error reading file: " + entry.getFileName() + "\n");
                    }
                }

                zakatResult.setText(String.format("Total Zakat Collected from All Users: RM %.2f", totalZakat));

            } catch (IOException ex) {
                zakatResult.setText("Error accessing history files: " + ex.getMessage());
            }
        });

        // Action buttons
        Button manageUsersButton = new Button("Manage Users");
        Button logoutButton = new Button("Logout");

        manageUsersButton.setOnAction(e -> {
            System.out.println("Manage Users clicked");
        });

        logoutButton.setOnAction(e -> {
            app.setScene(app.getLoginPanelView());
        });

        HBox actionButtons = new HBox(15, manageUsersButton, logoutButton);
        actionButtons.setAlignment(Pos.CENTER);

        root.getChildren().addAll(
                welcomeLabel,
                searchBox,
                btnSearchHistory,
                btnTotalZakat,
                zakatResult,
                actionButtons
        );

        return root;
    }
}
