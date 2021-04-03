package com.android.kasbon.sistem.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.kasbon.sistem.model.User;
import com.android.kasbon.sistem.repository.ReadRepository;

public class ReadViewModel extends AndroidViewModel {

    private final ReadRepository repository;

    public ReadViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ReadRepository();
    }

    public MutableLiveData<User> readDataUser(String uIdUser) {
        return repository.readDataUser(uIdUser);
    }





}
