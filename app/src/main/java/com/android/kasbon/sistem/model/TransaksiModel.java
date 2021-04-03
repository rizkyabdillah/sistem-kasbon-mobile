package com.android.kasbon.sistem.model;

import android.annotation.SuppressLint;

import com.android.kasbon.sistem.R;

public class TransaksiModel {

    private String id_user = null, id_transaksi = null, aksi = null, tanggal = null, nama = null;
    private int saldo_sebelum = 0, saldo_sesudah = 0, total = 0;

    public TransaksiModel() {
    }

    public TransaksiModel(String id_user, String aksi, String tanggal, String nama, String id_transaksi, int saldo_sebelum, int saldo_sesudah, int total) {
        this.id_user = id_user;
        this.aksi = aksi;
        this.tanggal = tanggal;
        this.saldo_sebelum = saldo_sebelum;
        this.saldo_sesudah = saldo_sesudah;
        this.total = total;
        this.nama = nama;
        this.id_transaksi = id_transaksi;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getAksi() {
        return aksi;
    }

    public void setAksi(String aksi) {
        this.aksi = aksi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getSaldo_sebelum() {
        return saldo_sebelum;
    }

    public void setSaldo_sebelum(int saldo_sebelum) {
        this.saldo_sebelum = saldo_sebelum;
    }

    public int getSaldo_sesudah() {
        return saldo_sesudah;
    }

    public void setSaldo_sesudah(int saldo_sesudah) {
        this.saldo_sesudah = saldo_sesudah;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTotalCurrency()  {
        return formatCurrency(getTotal(), getAksi());
    }

    public int getColorTotal() {
        return getAksi().equals("Bayar") ? R.color.app_green : R.color.app_red;
    }

    @SuppressLint("DefaultLocale")
    private String formatCurrency(double amount, String aksi) {
        final String PREFIX = aksi.equals("Bayar") ? "+" : "-";
        return String.format("%5s%,.0f",PREFIX + " Rp ", amount);
    }
}
