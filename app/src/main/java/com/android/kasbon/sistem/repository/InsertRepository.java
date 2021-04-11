package com.android.kasbon.sistem.repository;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.android.kasbon.sistem.model.DetailTransaksiModel;
import com.android.kasbon.sistem.model.ItemKeranjangModel;
import com.android.kasbon.sistem.model.JaminanModel;
import com.android.kasbon.sistem.model.TransaksiModel;
import com.android.kasbon.sistem.model.UserModel;
import com.android.kasbon.sistem.utilitas.UtilsSingleton;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;

public class InsertRepository {

    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final StorageReference reference = storage.getReference();

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

    public MutableLiveData<Task<Uri>> insertFoto(byte[] bytes) {
        MutableLiveData<Task<Uri>> liveData = new MutableLiveData<>();
        StorageReference ref = reference.child("" + System.currentTimeMillis());
        UploadTask task = ref.putBytes(bytes);
        task.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()) {
                    throw task.getException();
                }
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                liveData.postValue(task);
            }
        }); return liveData;
    }

    public MutableLiveData<Task<Void>> insertDetailBatch(List<ItemKeranjangModel> list, String noTransaksi) {
        MutableLiveData<Task<Void>> liveData = new MutableLiveData<>();
        for(int i = 0; i < list.size(); i++) {
            @SuppressLint("DefaultLocale")
            String idDetail = UtilsSingleton.getRandom("DTL", 3);
            DocumentReference reference = db.collection("transaksi").document(noTransaksi).collection("detail").document(idDetail);
            batch.set(reference, new DetailTransaksiModel(list.get(i).getNamaBarang(),list.get(i).getHarga(), list.get(i).getJumlah()));
        }
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                liveData.postValue(task);
            }
        }); return liveData;
    }

    public MutableLiveData<Task<Void>> insertTransaksi(TransaksiModel model, String idTransaksi) {
        MutableLiveData<Task<Void>> liveData = new MutableLiveData<>();
        db.collection("transaksi").document(idTransaksi).set(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                liveData.postValue(task);
            }
        });
        return liveData;
    }



}
