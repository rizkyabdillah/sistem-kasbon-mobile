package com.android.kasbon.sistem.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.view.fragment.HomePembeliFragment;
import com.android.kasbon.sistem.view.fragment.HomePenjualFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        loadFragment(new HomePenjualFragment());

//        final DocumentReference sfDocRef = db.collection("cities").document("SF");
//        db.runTransaction(new Transaction.Function<Double>() {
//            @Override
//            public Double apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
//                DocumentSnapshot snapshot = transaction.get(sfDocRef);
//                double newPopulation = snapshot.getDouble("population") + 1;
//                if (newPopulation <= 1000000) {
//                    transaction.update(sfDocRef, "population", newPopulation);
//                    return newPopulation;
//                } else {
//                    throw new FirebaseFirestoreException("Population too high",
//                            FirebaseFirestoreException.Code.ABORTED);
//                }
//            }
//        }).addOnSuccessListener(new OnSuccessListener<Double>() {
//            @Override
//            public void onSuccess(Double aDouble) {
//                Log.d("=============", "Transaction success: " + aDouble);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });

//        final DocumentReference docRef = db.collection("cities").document("SF");
//        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot snapshot,
//                                @Nullable FirebaseFirestoreException e) {
//                if (e != null) {
//                    Log.w("=========", "Listen failed.", e);
//                    return;
//                }
//
//                String source = snapshot != null && snapshot.getMetadata().hasPendingWrites()
//                        ? "Local" : "Server";
//
//                if (snapshot != null && snapshot.exists()) {
//                    Log.d("=========", source + " data: " + snapshot.getData());
//                } else {
//                    Log.d("=========", source + " data: null");
//                }
//            }
//        });














    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutFragment, fragment).commit();
    }

}