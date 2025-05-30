package com.mycompany.zakatapplication;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
    private final AdminGUI adminGUI = new AdminGUI();
    private static final String USER_FILE = "users.dat";


    private List<User> registeredUsers = new ArrayList<>();
    private User loggedInUser;


    public List<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public void registerUser(User user) {
        registeredUsers.add(user);
        saveUsersToFile(); // Save immediately upon registering
    }

    private void saveUsersToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(USER_FILE))) {
            out.writeObject(registeredUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUsersFromFile() {
        File file = new File(USER_FILE);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                registeredUsers = (List<User>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }


    @Override
    public void start(Stage primaryStage) {

        loadUsersFromFile();

        this.primaryStage = primaryStage;
        Parent root = getLoginPanelView();
        scene = new Scene(root);
        primaryStage.setTitle("Zakat Calculation Application");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            saveUsersToFile();
            if (!registeredUsers.isEmpty()) {
                System.out.println("Accounts registered:");
                registeredUsers.forEach(u -> System.out.println("- " + u.getEmail()));
            }
        });
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
    public Parent getAdminGUI() {
        return adminGUI.getView(this);
    }

    public static void main(String[] args) {
        launch(args);
    }
}