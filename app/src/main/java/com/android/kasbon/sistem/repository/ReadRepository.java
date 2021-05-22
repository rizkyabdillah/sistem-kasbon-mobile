package com.android.kasbon.sistem.repository;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.android.kasbon.sistem.model.ConstantModel;
import com.android.kasbon.sistem.model.JaminanModel;
import com.android.kasbon.sistem.model.KontakDaruratModel;
import com.android.kasbon.sistem.model.UserModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

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

    public MutableLiveData<DocumentSnapshot> readDataTransaksiReload(String idTransaksi) {
        MutableLiveData<DocumentSnapshot> liveData = new MutableLiveData<>();
        db.collection("transaksi").document(idTransaksi).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
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

    public MutableLiveData<KontakDaruratModel> readDataKontakDarurat(String idUser) {
        MutableLiveData<KontakDaruratModel> liveData = new MutableLiveData<>();
        db.collection("kontak").document(idUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                liveData.postValue(value.toObject(KontakDaruratModel.class));
                Log.d("=================", "onEvent: " + value.getData().size());
            }
        }); return liveData;
    }

    public MutableLiveData<Map<String, Object>> readAllDataNameUser() {
        MutableLiveData<Map<String, Object>> liveData = new MutableLiveData<>();
        db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                Map<String, Object> name = new HashMap<>();
                for(QueryDocumentSnapshot query : value) {
                    name.put(query.getId(), query.get("nama"));
                }
                liveData.postValue(name);
            }
        }); return liveData;
    }





}
