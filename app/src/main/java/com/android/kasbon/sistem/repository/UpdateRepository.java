package com.android.kasbon.sistem.repository;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.android.kasbon.sistem.model.AuthModel;
import com.android.kasbon.sistem.model.JaminanModel;
import com.android.kasbon.sistem.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.Map;

public class UpdateRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final WriteBatch batch = db.batch();
    private final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    public MutableLiveData<Task<Void>> updateUriFoto(String uri, String idUser) {
        MutableLiveData<Task<Void>> liveData = new MutableLiveData<>();
        db.collection("jaminan").document(idUser).update("foto", uri).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                liveData.postValue(task);
            }
        }); return liveData;
    }

    public MutableLiveData<Task<Void>> updateBatchUserJaminan(UserModel user,JaminanModel jaminan, String idUser) {
        MutableLiveData<Task<Void>> liveData = new MutableLiveData<>();
        DocumentReference userReference = db.collection("users").document(idUser);
        batch.update(userReference,
                "nama", user.getNama(),
                "password", user.getPassword(),
                "telepon", user.getTelepon(),
                "alamat", user.getAlamat());

        DocumentReference jaminanReference = db.collection("jaminan").document(idUser);
        batch.update(jaminanReference,
                "berat_emas", jaminan.getBerat_emas(),
                "jenis_jaminan", jaminan.getJenis_jaminan(),
                "limit_kredit", jaminan.getLimit_kredit());

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) { liveData.postValue(task);  }
        }); return liveData;
    }

    public MutableLiveData<Task<Void>> updateEmailUser(AuthModel authModel, String password) {
        MutableLiveData<Task<Void>> liveData = new MutableLiveData<>();
        AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), password);
        firebaseUser.reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                firebaseUser.updateEmail(authModel.getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        liveData.setValue(task);
                    }
                });
            }
        });  return liveData;
    }

    public MutableLiveData<Task<Void>> updatePasswordUser(AuthModel authModel, String password) {
        MutableLiveData<Task<Void>> liveData = new MutableLiveData<>();
        AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(),password);
        firebaseUser.reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                firebaseUser.updatePassword(authModel.getPassword()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        liveData.postValue(task);
                    }
                });
            }
        }); return liveData;
    }

    public MutableLiveData<Task<Void>> updateHargaEmas(String hargaEmas) {
        MutableLiveData<Task<Void>> liveData = new MutableLiveData<>();
        db.collection("constant").document("HARGA_EMAS").update("harga", hargaEmas)
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                liveData.postValue(task);
            }
        }); return liveData;
    }

    public MutableLiveData<Task<Void>> updateBatchTransaksiQR(String idTransaksi, String idUser, int limitKredit) {
        MutableLiveData<Task<Void>> liveData = new MutableLiveData<>();
        DocumentReference transaksiReference = db.collection("transaksi").document(idTransaksi);
        batch.update(transaksiReference, "id_user", idUser);

        DocumentReference jaminanReference = db.collection("jaminan").document(idUser);
        batch.update(jaminanReference, "limit_kredit", limitKredit);

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) { liveData.postValue(task);  }
        }); return liveData;
    }

}
