package com.android.kasbon.sistem.model;

import android.annotation.SuppressLint;

import androidx.databinding.BaseObservable;

public class OperationTransaksiModel extends BaseObservable  {

    private String idUser = "", idTransaksi = "", nama = "", tanggal = "";
    private Double jumlah = 0.0;
    private Boolean statusJual = false, statusBayar = false;

    public OperationTransaksiModel() {
    }

    public OperationTransaksiModel(String nama, String tanggal, double jumlah, boolean statusJual, boolean statusBayar) {
        this.nama = nama;
        this.tanggal = tanggal;
        this.jumlah = jumlah;
        this.statusJual = statusJual;
        this.statusBayar = statusBayar;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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

    public boolean isStatusJual() {
        return statusJual;
    }

    public void setStatusJual(boolean statusJual) {
        this.statusJual = statusJual;
    }

    public boolean isStatusBayar() {
        return statusBayar;
    }

    public void setStatusBayar(boolean statusBayar) {
        this.statusBayar = statusBayar;
    }

    @SuppressLint("DefaultLocale")
    public String getTotal() {
        return "Rp. " + String.format("%,.0f", getJumlah());
    }

    @SuppressLint("DefaultLocale")
    public String getTotalPembeli() {
        return "- Rp. " + String.format("%,.0f", getJumlah());
    }

    public String getStatus() {
        String statusJual = isStatusJual() ? "Tunai" : "Kasbon";
        String statusBayar = isStatusBayar() ? "Lunas" : "Belum Lunas";
        return statusJual.concat("|").concat(statusBayar);
    }
}
