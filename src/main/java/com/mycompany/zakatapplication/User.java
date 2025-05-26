package com.mycompany.zakatapplication;

public class User {

    private String email;
    private String password;
    private String name;

    public User() {
        this.email = "admin";
        this.password = "admin";
    }

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setName(String name) {
        this.name = name;
    }



}
