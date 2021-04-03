package com.android.kasbon.sistem.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.kasbon.sistem.repository.InsertRepository;

import java.util.HashMap;
import java.util.Map;

public class InsertViewModel extends AndroidViewModel {

    private final InsertRepository repository;

    public InsertViewModel(@NonNull Application application) {
        super(application);
        this.repository = new InsertRepository();
    }

    public MutableLiveData<String> insertDataUser(Map<String, Object> user, String idUser) {
        return repository.insertDataUser(user, idUser);
    }






}
