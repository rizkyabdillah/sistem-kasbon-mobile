package com.android.kasbon.sistem.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.kasbon.sistem.model.AuthModel;
import com.android.kasbon.sistem.repository.AuthRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class AuthViewModel extends AndroidViewModel {

    private final AuthRepository repository;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        repository = new AuthRepository();
    }

    public MutableLiveData<Task<AuthResult>> firebaseSign(AuthModel authModel) {
        return repository.firebaseSign(authModel);
    }

    public MutableLiveData<Task<AuthResult>> firebaseCreateNewUser(AuthModel authModel) {
        return repository.firebaseCreateNewUser(authModel);
    }








}
