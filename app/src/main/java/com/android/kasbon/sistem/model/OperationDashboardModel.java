package com.android.kasbon.sistem.model;

public class OperationDashboardModel {

    private UserModel userModel;

    public OperationDashboardModel() {
    }

    public OperationDashboardModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public String getFirstNama() {
        String[] nama = userModel.getNama().split(" ");
        return "Hai " + nama[0];
    }
}
