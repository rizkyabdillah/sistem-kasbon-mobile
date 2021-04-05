package com.android.kasbon.sistem.model;

import android.annotation.SuppressLint;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.android.kasbon.sistem.BR;

import java.io.Serializable;

public class ItemKeranjangModel extends BaseObservable implements Serializable {

    private String namaBarang = null, harga = null, jumlah = null;

    public ItemKeranjangModel() {
    }

    public ItemKeranjangModel(String namaBarang, String harga, String jumlah) {
        this.namaBarang = namaBarang;
        this.harga = harga;
        this.jumlah = jumlah;
    }

    @Bindable
    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
        notifyPropertyChanged(BR.namaBarang);
    }

    @Bindable
    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
        notifyPropertyChanged(BR.harga);
    }

    @Bindable
    public String getJumlah() {
        return jumlah;
    }

    @SuppressLint("DefaultLocale")
    @Bindable
    public String getHargaMinus() {
        return "- " + "Rp. " + String.format("%,.0f",(Double.parseDouble(getHarga()) * Double.parseDouble(getJumlah())));
    }

    @SuppressLint("DefaultLocale")
    @Bindable
    public String getJumlahPlusHarga() {
        return jumlah + " x Rp. " + String.format("%,.0f",  Double.parseDouble(getHarga()));
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
        notifyPropertyChanged(BR.jumlah);
    }
}
