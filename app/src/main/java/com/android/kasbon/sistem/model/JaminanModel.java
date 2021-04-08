package com.android.kasbon.sistem.model;

import android.annotation.SuppressLint;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.android.kasbon.sistem.BR;

public class JaminanModel extends BaseObservable {

    private String foto = null, jenis_jaminan = null;
    private int berat_emas = 0, limit_kredit = 0;

    public JaminanModel() {
    }

    public JaminanModel(String foto, String jenis_jaminan, int berat_emas, int limit_kredit) {
        this.foto = foto;
        this.jenis_jaminan = jenis_jaminan;
        this.berat_emas = berat_emas;
        this.limit_kredit = limit_kredit;
    }

    @Bindable
    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
        notifyPropertyChanged(BR.foto);
    }

    @Bindable
    public String getJenis_jaminan() {
        return jenis_jaminan;
    }

    public void setJenis_jaminan(String jenis_jaminan) {
        this.jenis_jaminan = jenis_jaminan;
        notifyPropertyChanged(BR.jenis_jaminan);
    }

    @Bindable
    public int getBerat_emas() {
        return berat_emas;
    }


    public void setBerat_emas(int berat_emas) {
        this.berat_emas = berat_emas;
        notifyPropertyChanged(BR.berat_emas);
    }

    @Bindable
    public int getLimit_kredit() {
        return limit_kredit;
    }

    public void setLimit_kredit(int limit_kredit) {
        this.limit_kredit = limit_kredit;
        notifyPropertyChanged(BR.limit_kredit);
    }

}
