package com.android.kasbon.sistem.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.model.AuthModel;
import com.android.kasbon.sistem.model.ItemKeranjangModel;
import com.android.kasbon.sistem.model.TransaksiModel;
import com.android.kasbon.sistem.utilitas.AlertProgress;
import com.android.kasbon.sistem.view.fragment.HomePembeliFragment;
import com.android.kasbon.sistem.view.fragment.HomePenjualFragment;
import com.android.kasbon.sistem.viewmodel.InsertViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    WriteBatch batch = db.batch();

    private InsertViewModel insertViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insertViewModel = ViewModelProviders.of(this).get(InsertViewModel.class);

        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("admin@email.com")) {
                loadFragment(new HomePenjualFragment());
            } else {
                loadFragment(new HomePembeliFragment());
            }
        }

//        AlertProgress progress = new AlertProgress(this, "Menyimpan Data");
//        progress.showDialog();
//        db.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                Map<String, Object> name = new HashMap<>();
//                for(QueryDocumentSnapshot query : value) {
//                    name.put(query.getId(), query.get("nama"));
//                }
//                if(name.size() > 0) {
//                    db.collection("transaksi").addSnapshotListener(new EventListener<QuerySnapshot>() {
//                        @Override
//                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                            progress.dismissDialog();
//                            List<TransaksiModel> list = new ArrayList<>();
//                            for (QueryDocumentSnapshot doc : value) {
//                                TransaksiModel model = new TransaksiModel();
//                                model.setNama_user(name.get(doc.getString("id_user")).toString());
//                                list.add(model);
//                            }
//                            for(TransaksiModel list1 : list) {
//                                Log.d("============", "" + list1.getNama_user());
//                            }
//                        }
//                    });
//                }
//            }
//        });






//        ==============================

//        String idTransaksi = "TR123129";
//
//        TransaksiModel t = new TransaksiModel();
//        t.setId_user("000");
//        t.setJumlah(25000);
//        t.setStatus_bayar(true);
//        t.setStatus_jual(true);
//
//        List<ItemKeranjangModel> list = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            list.add(new ItemKeranjangModel("Barang" + i, "100" + i, "10" + 1));
//        }
//
//        AlertProgress progress = new AlertProgress(this, "Menyimpan Data");
//        progress.showDialog();
//        insertViewModel.insertTransaksi(t, idTransaksi).observe(this, new Observer<Task<Void>>() {
//            @Override
//            public void onChanged(Task<Void> task) {
//                if(task.isSuccessful()) {
//                    insertViewModel.insertDetailBatch(list, idTransaksi).observe(MainActivity.this, new Observer<Task<Void>>() {
//                        @Override
//                        public void onChanged(Task<Void> task) {
//                            if(task.isSuccessful()) {
//                                progress.dismissDialog();
//                                Toast.makeText(getApplicationContext(), "SUKSES",
//                                        Toast.LENGTH_SHORT).show();
//                            } else {
//                                Log.d("============", task.getException().getMessage());
//                            }
//                        }
//                    });
//                } else {
//                    Log.d("============", task.getException().getMessage());
//                }
//            }
//        });


    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutFragment, fragment).commit();
    }

}