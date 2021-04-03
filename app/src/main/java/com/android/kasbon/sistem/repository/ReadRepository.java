package com.android.kasbon.sistem.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.android.kasbon.sistem.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ReadRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MutableLiveData<User> readDataUser(String uIdUser) {
        MutableLiveData<User> liveData = new MutableLiveData<>();
        db.collection("users").document(uIdUser).get()
        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                liveData.postValue(documentSnapshot.toObject(User.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                liveData.postValue(null);
            }
        }); return liveData;
    }

    public MutableLiveData<QuerySnapshot> readDataTransaksiUser(String idUser) {
        MutableLiveData<QuerySnapshot> liveData = new MutableLiveData<>();
        db.collection("transaksi").whereEqualTo("id_user", idUser).get()
        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                liveData.postValue(queryDocumentSnapshots);
            }
        }); return liveData;
    }

    public MutableLiveData<QuerySnapshot> readDataTransaksiAll() {
        MutableLiveData<QuerySnapshot> liveData = new MutableLiveData<>();
        db.collection("transaksi").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                liveData.postValue(queryDocumentSnapshots);
            }
        }); return liveData;
    }




}
