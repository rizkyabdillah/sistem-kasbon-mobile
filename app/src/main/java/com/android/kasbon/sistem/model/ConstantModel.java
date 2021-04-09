package com.android.kasbon.sistem.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.android.kasbon.sistem.BR;

public class ConstantModel extends BaseObservable {

    private int harga = 0;

    public ConstantModel() {
    }

    @Bindable
    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
        notifyPropertyChanged(BR.harga);
    }

}
