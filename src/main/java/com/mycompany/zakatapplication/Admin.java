package com.mycompany.zakatapplication;



public class Admin extends User {

    // Default constructor with default admin credentials
    public Admin() {
        super("admin", "adminhensem");
    }

    // Constructor to create Admin with custom credentials
    public Admin(String email, String password) {
        super(email, password);
    }

    // Override login method (optional since same as User)
    @Override
    public boolean login(String email, String password) {
        // Admin login same as user login check
        return super.login(email, password);
    }

    // You can add admin-specific methods here
}


