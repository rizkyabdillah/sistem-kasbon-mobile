package com.android.kasbon.sistem.model;

import android.annotation.SuppressLint;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.android.kasbon.sistem.BR;
import com.android.kasbon.sistem.R;

public class KontakDaruratModel extends BaseObservable {

    private String nama = "", telepon = "";
    private int status = 0;

    public KontakDaruratModel() {
    }

    public KontakDaruratModel(String nama, String telepon, int status) {
        this.nama = nama;
        this.telepon = telepon;
        this.status = status;
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
    public int getStatus() {
        return status;
    }

    @Bindable
    public int getStatusId() {
        int id = 0;
        switch (status) {
            case 0 :
                id = R.id.rdbAyah;
                break;
            case 1 :
                id = R.id.rdbIbu;
                break;
            case 2 :
                id = R.id.rdbAnak;
                break;
            case 3 :
                id = R.id.rdbTeman;
                break;

        }
        return id;
    }

    public void setStatus(int status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }

    @SuppressLint("NonConstantResourceId")
    public void setStatusId(int id) {
        int status = 0;
        switch (id) {
            case R.id.rdbAyah :
                status = 0;
                break;
            case R.id.rdbIbu :
                status = 1;
                break;
            case R.id.rdbAnak :
                status = 2;
                break;
            case R.id.rdbTeman :
                status = 3;
                break;

        }
        this.status = status;
        notifyPropertyChanged(BR.status);
    }
}
