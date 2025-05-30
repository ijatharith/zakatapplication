package com.mycompany.zakatapplication;

public class Account {
    protected String email;
    protected String password;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return "User"; // default
    }
}

