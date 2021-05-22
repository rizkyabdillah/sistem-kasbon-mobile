package com.android.kasbon.sistem.model;

import android.annotation.SuppressLint;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.android.kasbon.sistem.BR;

import java.io.Serializable;
import java.util.List;

public class OperationKeranjangModel extends BaseObservable implements Serializable {

    private List<ItemKeranjangModel> listKeranjang;

    public OperationKeranjangModel() {
    }

    public OperationKeranjangModel(List<ItemKeranjangModel> listKeranjang) {
        this.listKeranjang = listKeranjang;
    }

    @Bindable
    public List<ItemKeranjangModel> getListKeranjang() {
        return listKeranjang;
    }

    public void setListKeranjang(List<ItemKeranjangModel> listKeranjang) {
        this.listKeranjang = listKeranjang;
        notifyPropertyChanged(BR.listKeranjang);
    }

    @Bindable
    public String getJumlahBarang() {
        int jumlah = 0;
        for(int i = 0; i < listKeranjang.size(); i++) {
            jumlah += Integer.parseInt(listKeranjang.get(i).getJumlah());
        }
        return "" + jumlah;
    }

    @SuppressLint("DefaultLocale")
    @Bindable
    public String getJumlahHarga() {
        int harga = 0;
        for(int i = 0; i < listKeranjang.size(); i++) {
            harga += (Integer.parseInt(listKeranjang.get(i).getJumlah()) * Integer.parseInt(listKeranjang.get(i).getHarga()));
        }
        return "Rp. " +  String.format("%,.0f",  (double) harga);
    }

    @Bindable
    public int getJumlah() {
        int jumlah = 0;
        for(int i = 0; i < listKeranjang.size(); i++) {
            jumlah += Integer.parseInt(listKeranjang.get(i).getJumlah());
        }
        return jumlah;
    }

    @Bindable
    public int getTotal() {
        int harga = 0;
        for(int i = 0; i < listKeranjang.size(); i++) {
            harga += (Integer.parseInt(listKeranjang.get(i).getJumlah()) * Integer.parseInt(listKeranjang.get(i).getHarga()));
        }
        return harga;
    }
}
