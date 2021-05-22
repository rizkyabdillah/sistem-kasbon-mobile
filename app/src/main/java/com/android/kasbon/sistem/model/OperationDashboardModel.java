package com.android.kasbon.sistem.model;

import android.annotation.SuppressLint;

public class OperationDashboardModel {

    private UserModel userModel;
    private JaminanModel jaminanModel;

    public OperationDashboardModel() {
    }

    public OperationDashboardModel(UserModel userModel, JaminanModel jaminanModel) {
        this.userModel = userModel;
        this.jaminanModel = jaminanModel;
    }

    public String getFirstNama() {
        String[] nama = userModel.getNama().split(" ");
        return "Hai " + nama[0];
    }

    @SuppressLint("DefaultLocale")
    public String getLimitKredit() {
        return String.format("%3s%,.0f","Rp ", (double) jaminanModel.getLimit_kredit());
    }

    public double getLimit() {
        return jaminanModel.getLimit_kredit();
    }
}
