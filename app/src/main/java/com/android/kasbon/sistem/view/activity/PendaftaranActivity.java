package com.android.kasbon.sistem.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.databinding.ActivityPendaftaranBinding;
import com.android.kasbon.sistem.utilitas.AlertInfo;
import com.android.kasbon.sistem.utilitas.AlertProgress;
import com.android.kasbon.sistem.utilitas.Preference;
import com.android.kasbon.sistem.viewmodel.AuthViewModel;
import com.android.kasbon.sistem.viewmodel.InsertViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PendaftaranActivity extends AppCompatActivity {

    private AuthViewModel authViewModel;
    private InsertViewModel insertViewModel;
    private ActivityPendaftaranBinding binding;
    private AlertProgress alertProgress;
    private AlertInfo alertInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pendaftaran);

        authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);
        insertViewModel = ViewModelProviders.of(this).get(InsertViewModel.class);

        binding.constraintLayoutDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()) {

                    alertProgress = new AlertProgress(v, "Sedang mendaftarkan data");
                    alertProgress.showDialog();

                    authViewModel.firebaseCreateNewUser(
                        binding.editTextDaftarEmail.getText().toString(), binding.editTextDaftarPassword.getText().toString()
                    ).observe(PendaftaranActivity.this, new Observer<Task<AuthResult>>() {
                        @Override
                        public void onChanged(Task<AuthResult> task) {
                            if(task.isSuccessful()) {

                                Map<String, Object> user = new HashMap<>();
                                user.put("nama", binding.editTextDaftarNama.getText().toString());
                                user.put("telepon", null);
                                user.put("alamat", null);
                                user.put("saldo", 0);
                                user.put("password", binding.editTextDaftarPassword.getText().toString());

                                String idUser = task.getResult().getUser().getUid();
                                insertViewModel.insertDataUser(user, idUser).observe(PendaftaranActivity.this, new Observer<String>() {
                                    @Override
                                    public void onChanged(String s) {
                                        alertProgress.dismissDialog();
                                        if(s.equals("SUKSES")) {
                                            Intent intent = new Intent(PendaftaranActivity.this, MainActivity.class);
                                            alertInfo = new AlertInfo(PendaftaranActivity.this,"Data berhasil terdaftar", intent);
                                        } else  {
                                            alertInfo = new AlertInfo(PendaftaranActivity.this, s);
                                        }
                                        alertInfo.showDialog();
                                    }
                                });
                            } else {
                                alertProgress.dismissDialog();
                                alertInfo = new AlertInfo(PendaftaranActivity.this, task.getException().getMessage());
                                alertInfo.showDialog();
                            }
                        }
                    });
                }
            }
        });

        binding.textViewMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), LoginActivity.class));
                finish();
            }
        });


    }

    private boolean checkInput() {
        int count = 0;

        if(binding.editTextDaftarEmail.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Email anda masih kosong", Toast.LENGTH_SHORT).show();
            binding.editTextDaftarEmail.requestFocus();
            count++;
        }

        if(binding.editTextDaftarPassword.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Password anda masih kosong", Toast.LENGTH_SHORT).show();
            binding.editTextDaftarPassword.requestFocus();
            count++;
        }

        return (count == 0);
    }
}