package com.android.kasbon.sistem.model;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TransaksiModel {

    @SuppressLint("SimpleDateFormat")
    private String id_user = "",  tanggal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    private int jumlah = 0, total = 0;
    private boolean status_jual = false, status_bayar = false;

    public TransaksiModel() {
    }

    public TransaksiModel(String id_user, int jumlah, boolean status_jual, boolean status_bayar) {
        this.id_user = id_user;
        this.jumlah = jumlah;
        this.status_jual = status_jual;
        this.status_bayar = status_bayar;
    }

    public TransaksiModel(String id_user, String tanggal, int jumlah, boolean status_jual, boolean status_bayar) {
        this.id_user = id_user;
        this.tanggal = tanggal;
        this.jumlah = jumlah;
        this.status_jual = status_jual;
        this.status_bayar = status_bayar;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
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
