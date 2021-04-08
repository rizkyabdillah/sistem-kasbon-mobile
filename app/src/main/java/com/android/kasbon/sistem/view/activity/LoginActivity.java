package com.android.kasbon.sistem.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.databinding.ActivityLoginBinding;
import com.android.kasbon.sistem.utilitas.AlertInfo;
import com.android.kasbon.sistem.utilitas.AlertProgress;
import com.android.kasbon.sistem.viewmodel.AuthViewModel;
import com.android.kasbon.sistem.viewmodel.ReadViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.intentfilter.androidpermissions.PermissionManager;
import com.intentfilter.androidpermissions.models.DeniedPermissions;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity{

    private AuthViewModel viewModel;
    private ReadViewModel readViewModel;
    private ActivityLoginBinding binding;
    private AlertProgress alertProgress;
    private AlertInfo alertInfo;
    private LifecycleOwner OWNER = this;

    private PermissionManager permissionManager;
    private final String[] PERMISSION = {
        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        viewModel = ViewModelProviders.of(this).get(AuthViewModel.class);
//        readViewModel = ViewModelProviders.of(this).get(ReadViewModel.class);


//        readViewModel.readAll("Sips").observe(this, new Observer<DocumentSnapshot>() {
//            @Override
//            public void onChanged(DocumentSnapshot documentSnapshot) {
//                Log.d("=============", documentSnapshot.getData().toString());
//                if(documentSnapshot.getData() != null) {
//                    readViewModel.updateData("o2mn9qBJXDRLtvaeCuj1kaLEqQZ2", documentSnapshot)
//                        .observe(OWNER, new Observer<Boolean>() {
//                            @Override
//                            public void onChanged(Boolean aBoolean) {
//                                Log.d("=============", "" + aBoolean);
//                                if(aBoolean) {
//                                    db.collection("transaksi").document("Sips").delete();
//                                }
//                            }
//                        });
//                }
//            }
//        });


//        Log.d("=============", "Sukses");
//        db.collection("transaksi").document("o2mn9qBJXDRLtvaeCuj1kaLEqQZ2").delete();


        binding.textViewDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PendaftaranActivity.class));
                finish();
            }
        });

        binding.constraintLayoutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()) {

                    alertProgress = new AlertProgress(v, "Sedang mengautentikasi data");
                    alertProgress.showDialog();

                    viewModel.firebaseSign(
                        binding.editTextLoginEmail.getText().toString(), binding.editTextLoginPassword.getText().toString()
                    ).observe(OWNER, new Observer<Task<AuthResult>>() {
                        @Override
                        public void onChanged(Task<AuthResult> task) {
                            if (task.isComplete()) {
                                alertProgress.dismissDialog();
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    alertInfo = new AlertInfo(LoginActivity.this, "Berhasil masuk", intent);
                                } else {
                                    alertInfo = new AlertInfo(LoginActivity.this, task.getException().getMessage());
                                }
                                alertInfo.showDialog();
                            }
                        }
                    });
                }
            }
        });


    }

    private void checkPermission() {
        permissionManager = PermissionManager.getInstance(this);
        permissionManager.checkPermissions(Arrays.asList(PERMISSION), new PermissionManager.PermissionRequestListener() {
            @Override  public void onPermissionGranted() { }

            @Override
            public void onPermissionDenied(DeniedPermissions deniedPermissions) {
                alertInfo = new AlertInfo(LoginActivity.this, permissionManager);
                alertInfo.showDialog();
            }
        });
    }



    private boolean checkInput() {
        int count = 0;

        if(binding.editTextLoginEmail.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Email anda masih kosong", Toast.LENGTH_SHORT).show();
            binding.editTextLoginEmail.requestFocus();
            count++;
        }

        if(binding.editTextLoginPassword.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Password anda masih kosong", Toast.LENGTH_SHORT).show();
            binding.editTextLoginPassword.requestFocus();
            count++;
        }

        return (count == 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkPermission();
    }
}