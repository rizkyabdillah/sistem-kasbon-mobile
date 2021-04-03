package com.android.kasbon.sistem.repository;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.android.kasbon.sistem.utilitas.AlertInfo;
import com.android.kasbon.sistem.view.activity.MainActivity;
import com.android.kasbon.sistem.view.activity.PendaftaranActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class InsertRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MutableLiveData<String> insertDataUser(Map user, String idUser) {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        db.collection("users").document(idUser).set(user)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) { liveData.postValue("SUKSES"); }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) { liveData.postValue(e.getMessage()); }
            }
        ); return liveData;
    }

}
