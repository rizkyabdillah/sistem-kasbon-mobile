package com.android.kasbon.sistem.model;


import android.annotation.SuppressLint;

import java.text.DecimalFormat;

public class User {

    private String nama, telepon, alamat, email, password;
    private int saldo;

    public User() {
    }

    public User(String nama, String telepon, String alamat, int saldo) {
        this.nama = nama;
        this.telepon = telepon;
        this.alamat = alamat;
        this.saldo = saldo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public String getFirstNama() {
        String[] nama = getNama().split(" ");
        return "Hai " + nama[0];
    }

    public void setNama(String nama) {
        this.nama = nama;
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

    public int getSaldo() {
        return saldo;
    }

    @SuppressLint("DefaultLocale")
    public String getSaldoFormatted() {
        return "Rp. " +  String.format("%,.0f",  (double) getSaldo());
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
}
