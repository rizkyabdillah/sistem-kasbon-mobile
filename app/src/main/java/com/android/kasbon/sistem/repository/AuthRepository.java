package com.android.kasbon.sistem.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthRepository {

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public MutableLiveData<Task<AuthResult>> firebaseSign(String email, String password) {
        final MutableLiveData<Task<AuthResult>> liveData = new MutableLiveData<>();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                liveData.postValue(task);
            }
        }); return liveData;
    }

    public MutableLiveData<Task<AuthResult>> firebaseCreateNewUser(String email, String password) {
        MutableLiveData<Task<AuthResult>> liveData = new MutableLiveData<>();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                liveData.postValue(task);
            }
        }); return liveData;
    }

}
