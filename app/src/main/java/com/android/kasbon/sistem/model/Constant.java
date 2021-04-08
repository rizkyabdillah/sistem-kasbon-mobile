package com.android.kasbon.sistem.model;

import android.annotation.SuppressLint;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.android.kasbon.sistem.BR;

public class Constant extends BaseObservable {

    private int harga_emas = 0;

    public Constant(int hargaEmas) {
        this.harga_emas = hargaEmas;
        notifyPropertyChanged(BR.hargaEmas);
    }

    public Constant() {
    }

    @Bindable
    public int getHargaEmas() {
        return harga_emas;
    }

    public void setHargaEmas(int hargaEmas) {
        this.harga_emas = hargaEmas;
        notifyPropertyChanged(BR.hargaEmas);
    }

    @Bindable
    @SuppressLint("DefaultLocale")
    public String getHargaEmasFormatted() {
        return "Rp. " +  String.format("%,.0f",  (double) getHargaEmas());
    }

}
