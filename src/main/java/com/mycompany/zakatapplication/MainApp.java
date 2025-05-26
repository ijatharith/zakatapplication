package com.mycompany.zakatapplication;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Stage primaryStage;
    private Scene scene;
    private final LoginGUI loginPanelView = new LoginGUI();
    private final RegisterGUI registerPanelView = new RegisterGUI();
    private final MainMenu mainMenuView = new MainMenu();
    private final ZakatFitrahGUI zakatFitrahGUI = new ZakatFitrahGUI();
    private final ZakatAgricultureGUI zakatAgricGUI = new ZakatAgricultureGUI();
    private final ZakatIncomeGUI zakatIncomeGUI = new ZakatIncomeGUI();
    private final PaymentGUI paymentGUI = new PaymentGUI();


    private User registeredUser;

    public User getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(User user) {
        this.registeredUser = user;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Parent root = getLoginPanelView();
        scene = new Scene(root);
        primaryStage.setTitle("Zakat Calculation Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void setScene(Parent view) {
        scene.setRoot(view);
    }
    public Parent getLoginPanelView() {
        return loginPanelView.getView(this);
    }
    public Parent getRegisterPanelView() {
        return registerPanelView.getView(this);
    }
    public Parent getMainMenuView() {
        return mainMenuView.getView(this);
    }
    public Parent getZakatFitrahGUI() {
        return zakatFitrahGUI.getView(this);
    }
    public Parent getZakatAgricGUI() {
        return zakatAgricGUI.getView(this);
    }
    public Parent getZakatIncomeGUI() {
        return zakatIncomeGUI.getView(this);
    }
    public Parent getPaymentGUI() {
        return paymentGUI.getView(this);
    }

    public static void main(String[] args) {
        launch(args);
    }
}