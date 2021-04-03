package com.android.kasbon.sistem.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.kasbon.sistem.repository.UpdateRepository;

import java.util.Map;

public class UpdateViewModel extends AndroidViewModel {

    private final UpdateRepository repository;

    public UpdateViewModel(@NonNull Application application) {
        super(application);
        this.repository = new UpdateRepository();
    }

    public MutableLiveData<String> updateDataUser(Map<String, Object> user, String uIdUser) {
        return repository.updateDataUser(user, uIdUser);
    }

    public MutableLiveData<String> updateEmailUser(String email, String emailBefore, String pass) {
        return repository.updateEmailUser(email, emailBefore, pass);
    }

    public MutableLiveData<String> updatePasswordUser(String password, String email, String pass) {
        return repository.updatePasswordUser(password, email, pass);
    }







}
