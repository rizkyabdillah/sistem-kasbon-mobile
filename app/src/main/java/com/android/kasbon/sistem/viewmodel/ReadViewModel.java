package com.android.kasbon.sistem.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.kasbon.sistem.model.ConstantModel;
import com.android.kasbon.sistem.model.JaminanModel;
import com.android.kasbon.sistem.model.UserModel;
import com.android.kasbon.sistem.repository.ReadRepository;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class ReadViewModel extends AndroidViewModel {

    private final ReadRepository repository;

    public ReadViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ReadRepository();
    }

    public MutableLiveData<UserModel> readDataUser(String uIdUser) {
        return repository.readDataUser(uIdUser);
    }

    public MutableLiveData<JaminanModel> readDataJaminan(String uIdUser) {
        return repository.readDataJaminan(uIdUser);
    }

    public MutableLiveData<QuerySnapshot> readDataTransaksiUser(String idUser) {
        return repository.readDataTransaksiUser(idUser);
    }

    public MutableLiveData<DocumentSnapshot> readDataTransaksiReload(String idTransaksi) {
        return repository.readDataTransaksiReload(idTransaksi);
    }

    public MutableLiveData<QuerySnapshot> readDataTransaksiAll() {
        return repository.readDataTransaksiAll();
    }

    public MutableLiveData<ConstantModel> readDataHargaEmas() {
        return repository.readDataHargaEmas();
    }

    public MutableLiveData<Map<String, Object>> readAllDataNameUser() {
        return repository.readAllDataNameUser();
    }




}
