package com.android.kasbon.sistem.model;

import android.annotation.SuppressLint;

public class OperationProfileModel {

    private JaminanModel jaminanModel;
    private ConstantModel constantModel;

    public OperationProfileModel() {
    }

    public OperationProfileModel( JaminanModel jaminanModel, ConstantModel constantModel) {
        this.jaminanModel = jaminanModel;
        this.constantModel = constantModel;
    }

    public String getBeratEmas() {
        return String.valueOf(constantModel.getHarga());
    }

    @SuppressLint("DefaultLocale")
    public String getHargaEmas() {
        return "Rp. " +  String.format("%,.0f",  (double) constantModel.getHarga());
    }

    public JaminanModel getJaminanModel() {
        return jaminanModel;
    }

    public void setJaminanModel(JaminanModel jaminanModel) {
        this.jaminanModel = jaminanModel;
    }

    public ConstantModel getConstantModel() {
        return constantModel;
    }

    public void setConstantModel(ConstantModel constantModel) {
        this.constantModel = constantModel;
    }
}
