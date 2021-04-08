package com.android.kasbon.sistem.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.kasbon.sistem.model.JaminanModel;
import com.android.kasbon.sistem.model.UserModel;
import com.android.kasbon.sistem.repository.InsertRepository;
import com.google.android.gms.tasks.Task;

public class InsertViewModel extends AndroidViewModel {

    private final InsertRepository repository;

    public InsertViewModel(@NonNull Application application) {
        super(application);
        this.repository = new InsertRepository();
    }

    public MutableLiveData<Task<Void>> insertBatchUserJaminan(UserModel userModel, JaminanModel jaminan, String idUser) {
        return repository.insertBatchUserJaminan(userModel, jaminan, idUser);
    }






}
