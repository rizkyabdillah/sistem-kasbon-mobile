package com.android.kasbon.sistem.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.android.kasbon.sistem.model.JaminanModel;
import com.android.kasbon.sistem.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

public class InsertRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final WriteBatch batch = db.batch();

    public MutableLiveData<Task<Void>> insertBatchUserJaminan(UserModel userModel, JaminanModel jaminan, String idUser) {
        MutableLiveData<Task<Void>> liveData = new MutableLiveData<>();
        DocumentReference userReference = db.collection("users").document(idUser);
        batch.set(userReference, userModel);

        DocumentReference jaminanReference = db.collection("jaminan").document(idUser);
        batch.set(jaminanReference, jaminan);

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                liveData.postValue(task);
            }
        }); return liveData;
    }









}
