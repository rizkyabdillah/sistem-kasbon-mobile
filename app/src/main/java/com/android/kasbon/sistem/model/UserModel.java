package com.android.kasbon.sistem.model;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import com.android.kasbon.sistem.BR;

public class UserModel extends BaseObservable {

    private String nama = "", telepon = "", alamat = "",  password = "";

    public UserModel() {
    }

    public UserModel(String nama, String telepon, String alamat, String password) {
        this.nama = nama;
        this.telepon = telepon;
        this.alamat = alamat;
        this.password = password;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
        notifyPropertyChanged(BR.nama);
    }

    @Bindable
    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
        notifyPropertyChanged(BR.telepon);
    }

    @Bindable
    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
        notifyPropertyChanged(BR.alamat);
    }
}
