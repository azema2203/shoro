package com.shoro.menu.auth;

import java.sql.SQLException;

public abstract class User {
    private String username;
    private String password;
    private String userType;

    public User(String username, String password, String userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public abstract void showMenu() throws SQLException;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }
}