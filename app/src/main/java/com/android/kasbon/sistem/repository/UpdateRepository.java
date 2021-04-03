package com.android.kasbon.sistem.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class UpdateRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    public MutableLiveData<String> updateDataUser(Map<String, Object> user, String uIdUser) {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        db.collection("users").document(uIdUser).update(user)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) { liveData.postValue("SUKSES"); }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) { liveData.postValue(e.getMessage()); }
            });
        return liveData;
    }

    public MutableLiveData<String> updateEmailUser(String email, String emailBefore, String pass) {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        AuthCredential credential = EmailAuthProvider.getCredential(emailBefore, pass);
        firebaseUser.reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                firebaseUser.updateEmail(email)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) { liveData.postValue("SUKSES"); }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) { liveData.postValue(e.getMessage());}
                });
            }
        });  return liveData;
    }

    public MutableLiveData<String> updatePasswordUser(String password, String email, String pass) {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        AuthCredential credential = EmailAuthProvider.getCredential(email, pass);
        firebaseUser.reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                firebaseUser.updatePassword(password).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) { liveData.postValue("SUKSES");}
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) { liveData.postValue(e.getMessage());}
                });
            }
        }); return liveData;
    }

}
