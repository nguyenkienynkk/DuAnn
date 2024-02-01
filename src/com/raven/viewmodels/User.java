package com.raven.viewmodels;

public class User {

    private int id;
    private String username;
    private String paswword;
    private int role;

    public User() {
    }

    public User(String username, String paswword) {
        this.username = username;
        this.paswword = paswword;
    }

    public User(int id, String username, String paswword, int role) {
        this.id = id;
        this.username = username;
        this.paswword = paswword;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPaswword() {
        return paswword;
    }

    public void setPaswword(String paswword) {
        this.paswword = paswword;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return username;
    }

}
