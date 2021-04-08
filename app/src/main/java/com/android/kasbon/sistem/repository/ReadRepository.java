package com.android.kasbon.sistem.repository;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.android.kasbon.sistem.model.UserModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class ReadRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MutableLiveData<UserModel> readDataUser(String uIdUser) {
        MutableLiveData<UserModel> liveData = new MutableLiveData<>();
        db.collection("users").document(uIdUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                liveData.postValue(value.toObject(UserModel.class));
            }
        }); return liveData;
    }

    public MutableLiveData<QuerySnapshot> readDataTransaksiUser(String idUser) {
        MutableLiveData<QuerySnapshot> liveData = new MutableLiveData<>();
        db.collection("transaksi").whereEqualTo("id_user", idUser).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                liveData.postValue(value);
            }
        }); return liveData;
    }

    public MutableLiveData<QuerySnapshot> readDataTransaksiAll() {
        MutableLiveData<QuerySnapshot> liveData = new MutableLiveData<>();
        db.collection("transaksi").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                liveData.postValue(value);
            }
        }); return liveData;
    }

//    public MutableLiveData<DocumentSnapshot> readAll(String path) {
//        MutableLiveData<DocumentSnapshot> liveData = new MutableLiveData<>();
//        db.collection("transaksi").document(path).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                liveData.postValue(documentSnapshot);
//            }
//        });
//        return liveData;
//    }
//
//    public MutableLiveData<Boolean> updateData(String path, DocumentSnapshot value) {
//        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
//        db.collection("transaksi").document(path).set(value.getData()).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                liveData.postValue(true);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                liveData.postValue(false);
//            }
//        });
//        return liveData;
//    }




}
