package com.android.kasbon.sistem.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.kasbon.sistem.repository.UpdateRepository;
import com.google.android.gms.tasks.Task;

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

    public MutableLiveData<Task<Void>> updateEmailUser(String email, String emailBefore, String password) {
        return repository.updateEmailUser(email, emailBefore, password);
    }

    public MutableLiveData<Task<Void>> updatePasswordUser(String passwordBefore, String email, String password) {
        return repository.updatePasswordUser(passwordBefore, email, password);
    }







}
