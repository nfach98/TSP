package com.example.skripsi.Model.LoginModel;

public class DataUserModel {
    private int id;
    private String Username, Password, stts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getStts() {
        return stts;
    }

    public void setStts(String stts) {
        this.stts = stts;
    }
}
