package com.android.kasbon.sistem.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.android.kasbon.sistem.model.AuthModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthRepository {

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public MutableLiveData<Task<AuthResult>> firebaseSign(AuthModel auth) {
        final MutableLiveData<Task<AuthResult>> liveData = new MutableLiveData<>();
        firebaseAuth.signInWithEmailAndPassword(auth.getEmail(), auth.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) { liveData.postValue(task); }
        }); return liveData;
    }

    public MutableLiveData<Task<AuthResult>> firebaseCreateNewUser(AuthModel auth) {
        MutableLiveData<Task<AuthResult>> liveData = new MutableLiveData<>();
        firebaseAuth.createUserWithEmailAndPassword(auth.getEmail(), auth.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) { liveData.postValue(task); }
        }); return liveData;
    }

}
