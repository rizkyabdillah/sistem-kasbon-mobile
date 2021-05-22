package com.android.kasbon.sistem.model;

public class DetailTransaksiModel {

    private String nama_barang = null, harga = null, jumlah = null;

    public DetailTransaksiModel() {
    }

    public DetailTransaksiModel(String nama_barang, String harga, String jumlah) {
        this.nama_barang = nama_barang;
        this.harga = harga;
        this.jumlah = jumlah;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }
}
