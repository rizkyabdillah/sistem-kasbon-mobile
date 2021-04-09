package com.android.kasbon.sistem.repository;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.android.kasbon.sistem.model.ConstantModel;
import com.android.kasbon.sistem.model.JaminanModel;
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

    public MutableLiveData<JaminanModel> readDataJaminan(String uIdUser) {
        MutableLiveData<JaminanModel> liveData = new MutableLiveData<>();
        db.collection("jaminan").document(uIdUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                liveData.postValue(value.toObject(JaminanModel.class));
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

    public MutableLiveData<ConstantModel> readDataHargaEmas() {
        MutableLiveData<ConstantModel> liveData = new MutableLiveData<>();
        db.collection("constant").document("HARGA_EMAS").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                liveData.postValue(value.toObject(ConstantModel.class));
            }
        }); return liveData;
    }





}
