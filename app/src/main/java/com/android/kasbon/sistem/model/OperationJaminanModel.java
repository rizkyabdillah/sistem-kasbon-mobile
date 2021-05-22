package com.android.kasbon.sistem.model;

import android.annotation.SuppressLint;

public class OperationJaminanModel {

    private JaminanModel model;

    public OperationJaminanModel() {
    }

    public OperationJaminanModel(JaminanModel model) {
        this.model = model;
    }

    public JaminanModel getModel() {
        return model;
    }

    public void setModel(JaminanModel model) {
        this.model = model;
    }

    @SuppressLint("DefaultLocale")
    public String getSaldoFormatted() {
        return "Rp. " +  String.format("%,.0f",  (double) model.getLimit_kredit());
    }
}
