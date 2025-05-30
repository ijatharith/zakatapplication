package com.mycompany.zakatapplication;


import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String email;
    private String password;
    private UserZakatRecord zakatRecord;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.zakatRecord = new UserZakatRecord(); // Make this serializable too if needed
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserZakatRecord getZakatRecord() {
        return zakatRecord;
    }

    public void setZakatRecord(UserZakatRecord zakatRecord) {
        this.zakatRecord = zakatRecord;
    }

    // Login method to validate credentials
    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    public String getUsername() {
        return email;
    }
}



