package com.mycompany.zakatapplication;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaymentGUI {

    private double totalToPay = 0.0;
    private final StringBuilder historyLog = new StringBuilder();

    public Parent getView(MainApp app) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));

        Label header = new Label("Payment Interface");
        header.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        HBox headerBox = new HBox(header);
        headerBox.setAlignment(Pos.CENTER);

        // Zakat options
        Label chooseZakat = new Label("Which zakat do you want to pay?");
        CheckBox zakatFitrah = new CheckBox("Zakat Fitrah");
        CheckBox zakatIncome = new CheckBox("Zakat Income");
        CheckBox zakatAgriculture = new CheckBox("Zakat Agriculture");

        VBox zakatType = new VBox(zakatFitrah, zakatIncome, zakatAgriculture);
        zakatType.setSpacing(10);
        zakatType.setPadding(new Insets(10));

        // Buttons
        Button checkTotalButton = new Button("Check Total");
        Button payTotalButton = new Button("Pay Total");
        Button saveHistoryButton = new Button("Save to History");
        Button viewHistoryButton = new Button("View History");
        Button backBtn = new Button("Back");

        HBox buttonBox = new HBox(checkTotalButton, payTotalButton, saveHistoryButton, viewHistoryButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(10));

        TextArea zakatResult = new TextArea();
        zakatResult.setEditable(false);
        zakatResult.setMaxSize(400, 100);
        zakatResult.setWrapText(true);

        // Check Total Logic
        checkTotalButton.setOnAction(e -> {
            User user = app.getLoggedInUser();
            if (user == null) {
                zakatResult.setText("No user logged in.");
                return;
            }

            UserZakatRecord record = user.getZakatRecord();
            totalToPay = 0;
            StringBuilder summary = new StringBuilder("Selected Zakat to Pay:\n");

            if (zakatFitrah.isSelected()) {
                totalToPay += record.getZakatFitrah();
                summary.append(String.format("Zakat Fitrah: RM %.2f\n", record.getZakatFitrah()));
            }

            if (zakatIncome.isSelected()) {
                totalToPay += record.getZakatIncome();
                summary.append(String.format("Zakat Income: RM %.2f\n", record.getZakatIncome()));
            }

            if (zakatAgriculture.isSelected()) {
                totalToPay += record.getZakatAgriculture();
                summary.append(String.format("Zakat Agriculture: RM %.2f\n", record.getZakatAgriculture()));
            }

            summary.append(String.format("\nTotal Zakat to Pay: RM %.2f", totalToPay));
            zakatResult.setText(summary.toString());
        });

        // Pay Total Logic
        payTotalButton.setOnAction(e -> {
            User user = app.getLoggedInUser();
            if (user == null) {
                zakatResult.setText("No user logged in.");
                return;
            }

            UserZakatRecord record = user.getZakatRecord();
            StringBuilder paidSummary = new StringBuilder("Zakat Paid:\n");
            boolean paidSomething = false;

            if (zakatFitrah.isSelected() && record.getZakatFitrah() > 0) {
                paidSummary.append(String.format("Zakat Fitrah: RM %.2f -> RM 0.00\n", record.getZakatFitrah()));
                record.setZakatFitrah(0.0);
                paidSomething = true;
            }

            if (zakatIncome.isSelected() && record.getZakatIncome() > 0) {
                paidSummary.append(String.format("Zakat Income: RM %.2f -> RM 0.00\n", record.getZakatIncome()));
                record.setZakatIncome(0.0);
                paidSomething = true;
            }

            if (zakatAgriculture.isSelected() && record.getZakatAgriculture() > 0) {
                paidSummary.append(String.format("Zakat Agriculture: RM %.2f -> RM 0.00\n", record.getZakatAgriculture()));
                record.setZakatAgriculture(0.0);
                paidSomething = true;
            }

            if (!paidSomething) {
                zakatResult.setText("No zakat selected or all values already paid.");
                return;
            }

            paidSummary.append("Payment successful.\n");

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            historyLog.append("Payment at ").append(timestamp).append(":\n")
                    .append(paidSummary).append("\n");

            zakatResult.setText(paidSummary.toString());
        });

        // Save History Logic
        saveHistoryButton.setOnAction(e -> {
            User currentUser = app.getLoggedInUser();
            if (currentUser == null) {
                zakatResult.setText("User not logged in. Cannot save history.");
                return;
            }

            String username = currentUser.getUsername();
            String filename = "history_" + username + ".txt";

            double totalPaid = 0.0;
            Pattern pattern = Pattern.compile("RM\\s*(\\d+(\\.\\d{1,2})?)");

            for (String line : historyLog.toString().split("\n")) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    try {
                        totalPaid += Double.parseDouble(matcher.group(1));
                    } catch (NumberFormatException ignored) {}
                }
            }

            String fullLog = "Username: " + username + "\n"
                    + historyLog.toString()
                    + "Zakat Paid: RM " + String.format("%.2f", totalPaid) + "\n\n";

            try {
                Files.write(Paths.get(filename), fullLog.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                zakatResult.setText("History saved for user: " + username);
                historyLog.setLength(0); // Clear after save
            } catch (IOException ex) {
                zakatResult.setText("Error saving history: " + ex.getMessage());
            }
        });

        // View History Logic
        viewHistoryButton.setOnAction(e -> {
            User currentUser = app.getLoggedInUser();
            if (currentUser == null) {
                zakatResult.setText("User not logged in. Cannot view history.");
                return;
            }

            String username = currentUser.getUsername();
            String filename = "history_" + username + ".txt";

            try {
                String content = Files.readString(Paths.get(filename));
                zakatResult.setText("Payment History for user: " + username + "\n\n" + content);
            } catch (IOException ex) {
                zakatResult.setText("No payment history found for user: " + username);
            }
        });

        // Back Button
        backBtn.setOnAction(e -> app.setScene(app.getMainMenuView()));
        HBox backBtnBox = new HBox(backBtn);
        backBtnBox.setAlignment(Pos.CENTER);
        backBtnBox.setPadding(new Insets(10));

        // Final layout
        VBox mainBox = new VBox(10);
        mainBox.setPadding(new Insets(15));
        mainBox.getChildren().addAll(
                headerBox,
                chooseZakat,
                zakatType,
                buttonBox,
                zakatResult,
                backBtnBox
        );

        return mainBox;
    }
}
