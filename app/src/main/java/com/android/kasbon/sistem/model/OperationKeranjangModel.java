package com.android.kasbon.sistem.model;

import android.annotation.SuppressLint;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.android.kasbon.sistem.BR;

import java.io.Serializable;
import java.util.List;

public class OperationKeranjangModel extends BaseObservable implements Serializable {

    private List<ItemKeranjangModel> listKeranjang;
    private String jumlah = null, total = null;

    public OperationKeranjangModel() {
    }

    public OperationKeranjangModel(List<ItemKeranjangModel> listKeranjang) {
        this.listKeranjang = listKeranjang;
    }

    public OperationKeranjangModel(List<ItemKeranjangModel> listKeranjang, String jumlah, String total) {
        this.listKeranjang = listKeranjang;
        this.jumlah = jumlah;
        this.total = total;
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
    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
        notifyPropertyChanged(BR.jumlah);
    }

    @Bindable
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
        notifyPropertyChanged(BR.total);
    }
}
