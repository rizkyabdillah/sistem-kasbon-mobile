package com.android.kasbon.sistem.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.android.kasbon.sistem.BR;

public class ConstantModel extends BaseObservable {

    private String harga = "";

    public ConstantModel() {
    }

    @Bindable
    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
        notifyPropertyChanged(BR.harga);
    }

}
