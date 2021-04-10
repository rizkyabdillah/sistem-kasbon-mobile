package com.android.kasbon.sistem.model;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TransaksiModel {

    @SuppressLint("SimpleDateFormat")
    private String id_user = "",  tanggal = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
    private double jumlah = 0.0;
    private boolean status_jual = false, status_bayar = false;

    public TransaksiModel() {
    }

    public TransaksiModel(String id_user, double jumlah, boolean status_jual, boolean status_bayar) {
        this.id_user = id_user;
        this.jumlah = jumlah;
        this.status_jual = status_jual;
        this.status_bayar = status_bayar;
    }

    public TransaksiModel(String id_user, String tanggal, double jumlah, boolean status_jual, boolean status_bayar) {
        this.id_user = id_user;
        this.tanggal = tanggal;
        this.jumlah = jumlah;
        this.status_jual = status_jual;
        this.status_bayar = status_bayar;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    public boolean isStatus_jual() {
        return status_jual;
    }

    public void setStatus_jual(boolean status_jual) {
        this.status_jual = status_jual;
    }

    public boolean isStatus_bayar() {
        return status_bayar;
    }

    public void setStatus_bayar(boolean status_bayar) {
        this.status_bayar = status_bayar;
    }
}
