package com.android.kasbon.sistem.model;


public class User {

    private String uId, name, email, password, telepon, alamat;

    public User() {
    }

    public User(String uId, String name, String email, String password, String telepon, String alamat) {
        this.uId = uId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.telepon = telepon;
        this.alamat = alamat;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
