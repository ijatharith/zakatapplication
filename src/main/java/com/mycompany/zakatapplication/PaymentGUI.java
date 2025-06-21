package com.mycompany.zakatapplication;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

        Image image = new Image(getClass().getResourceAsStream("/images/systemlogo.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(347 * 1.5);
        imageView.setFitHeight(94 * 1.5);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));

        Label header = new Label("Payment Interface");
        header.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        HBox headerBox = new HBox(header);
        headerBox.setAlignment(Pos.CENTER);

        // Zakat selection using RadioButtons
        Label chooseZakat = new Label("Which zakat do you want to pay?");
        ToggleGroup zakatGroup = new ToggleGroup();

        RadioButton zakatFitrah = new RadioButton("Zakat Fitrah");
        RadioButton zakatIncome = new RadioButton("Zakat Income");
        RadioButton zakatAgriculture = new RadioButton("Zakat Agriculture");

        zakatFitrah.setToggleGroup(zakatGroup);
        zakatIncome.setToggleGroup(zakatGroup);
        zakatAgriculture.setToggleGroup(zakatGroup);

        VBox zakatType = new VBox(zakatFitrah, zakatIncome, zakatAgriculture);
        zakatType.setAlignment(Pos.CENTER);
        zakatType.setSpacing(10);
        zakatType.setPadding(new Insets(10));

        Button payTotalButton = new Button("Pay Total");
        Button viewHistoryButton = new Button("View History");
        Button backBtn = new Button("Back");

        HBox buttonBox = new HBox(payTotalButton, viewHistoryButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(10));

        TextArea zakatResult = new TextArea();
        zakatResult.setEditable(false);
        zakatResult.setMaxSize(400, 100);
        zakatResult.setWrapText(true);


        // Pay Total Logic
        payTotalButton.setOnAction(e -> {
            User user = app.getLoggedInUser();
            if (user == null) {
                zakatResult.setText("No user logged in.");
                return;
            }

            Toggle selectedToggle = zakatGroup.getSelectedToggle();
            if (selectedToggle == null) {
                zakatResult.setText("Please select one zakat type to pay.");
                return;
            }

            UserZakatRecord record = user.getZakatRecord();
            String selected = ((RadioButton) selectedToggle).getText();
            StringBuilder paidSummary = new StringBuilder("Zakat Paid:\n");
            boolean paidSomething = false;

            switch (selected) {
                case "Zakat Fitrah":
                    if (record.getZakatFitrah() > 0) {
                        paidSummary.append(String.format("Zakat Fitrah: RM %.2f -> RM 0.00\n", record.getZakatFitrah()));
                        record.setZakatFitrah(0.0);
                        paidSomething = true;
                    }
                    break;
                case "Zakat Income":
                    if (record.getZakatIncome() > 0) {
                        paidSummary.append(String.format("Zakat Income: RM %.2f -> RM 0.00\n", record.getZakatIncome()));
                        record.setZakatIncome(0.0);
                        paidSomething = true;
                    }
                    break;
                case "Zakat Agriculture":
                    if (record.getZakatAgriculture() > 0) {
                        paidSummary.append(String.format("Zakat Agriculture: RM %.2f -> RM 0.00\n", record.getZakatAgriculture()));
                        record.setZakatAgriculture(0.0);
                        paidSomething = true;
                    }
                    break;
            }

            if (!paidSomething) {
                zakatResult.setText("Zakat already paid or amount is zero.");
                return;
            }

            paidSummary.append("Payment successful.\n");

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            historyLog.append("Payment at ").append(timestamp).append(":\n")
                    .append(paidSummary).append("\n");

            zakatResult.setText(paidSummary.toString());

            Dialog<Void> receiptDialog = new Dialog<>();
            receiptDialog.setTitle("Payment Receipt");
            receiptDialog.setHeaderText("Zakat Payment Receipt");

            String receiptContent = "Username: " + user.getUsername() + "\n"
                    + "Date: " + timestamp + "\n"
                    + paidSummary;

            TextArea receiptTextArea = new TextArea(receiptContent);
            receiptTextArea.setWrapText(true);
            receiptTextArea.setEditable(false);
            receiptTextArea.setPrefWidth(400);
            receiptTextArea.setPrefHeight(200);

            ButtonType printButtonType = new ButtonType("Print", ButtonBar.ButtonData.LEFT);
            ButtonType closeButtonType = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
            receiptDialog.getDialogPane().getButtonTypes().addAll(printButtonType, closeButtonType);
            receiptDialog.getDialogPane().setContent(receiptTextArea);
            receiptDialog.setResultConverter(dialogButton -> {
                if (dialogButton == printButtonType) {
                    System.out.println("Printing Receipt:\n" + receiptTextArea.getText());
                }
                return null;
            });

            receiptDialog.showAndWait();

            String username = user.getUsername();
            String filename = "history_" + username + ".txt";

            double totalPaid = 0.0;
            Pattern pattern = Pattern.compile("RM\\s*(\\d+(\\.\\d{1,2})?)");

            for (String line : historyLog.toString().split("\n")) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    try {
                        totalPaid += Double.parseDouble(matcher.group(1));
                    } catch (NumberFormatException ignored) {
                    }
                }
            }

            String fullLog = "Username: " + username + "\n"
                    + historyLog
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
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(15));
        mainBox.getChildren().addAll(
                imageView,
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
