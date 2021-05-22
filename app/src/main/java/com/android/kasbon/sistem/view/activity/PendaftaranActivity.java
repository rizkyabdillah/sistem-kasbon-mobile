package com.android.kasbon.sistem.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.databinding.ActivityPendaftaranBinding;
import com.android.kasbon.sistem.model.AuthModel;
import com.android.kasbon.sistem.model.JaminanModel;
import com.android.kasbon.sistem.model.KontakDaruratModel;
import com.android.kasbon.sistem.model.UserModel;
import com.android.kasbon.sistem.utilitas.AlertInfo;
import com.android.kasbon.sistem.utilitas.AlertProgress;
import com.android.kasbon.sistem.viewmodel.AuthViewModel;
import com.android.kasbon.sistem.viewmodel.InsertViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class PendaftaranActivity extends AppCompatActivity {

    private AuthViewModel authViewModel;
    private InsertViewModel insertViewModel;
    private ActivityPendaftaranBinding binding;
    private AlertProgress alertProgress;
    private AlertInfo alertInfo;
    private UserModel users;
    private AuthModel auths;
    private final LifecycleOwner OWNER = this;
    private final Activity THIS = PendaftaranActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pendaftaran);

        auths = new AuthModel();
        binding.setAuth(auths);

        users = new UserModel();
        binding.setUser(users);

        authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);
        insertViewModel = ViewModelProviders.of(this).get(InsertViewModel.class);

        binding.constraintLayoutDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()) {
                    alertProgress = new AlertProgress(v, "Sedang mendaftarkan data");
                    alertProgress.showDialog();

                    alertInfo = new AlertInfo(THIS,"Data berhasil terdaftar", new Intent(THIS, MainActivity.class));

                    authViewModel.firebaseCreateNewUser(auths).observe(OWNER, new Observer<Task<AuthResult>>() {
                        @Override
                        public void onChanged(Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                String idUser = task.getResult().getUser().getUid();
                                users.setPassword(auths.getPassword());
                                insertViewModel.insertBatchUserJaminan(users, new JaminanModel(), new KontakDaruratModel(), idUser).observe(OWNER, new Observer<Task<Void>>() {
                                    @Override
                                    public void onChanged(Task<Void> voidTask) {
                                        if(voidTask.isSuccessful()) {
                                            Log.d("=================", "SUKSES");
                                        } else {
                                            Log.d("=================", "Exception 2");
                                            alertInfo = new AlertInfo(THIS, task.getException().getMessage());
                                        }
                                    }
                                });
                            } else {
                                Log.d("=================", "Exception 1");
                                alertInfo = new AlertInfo(THIS, task.getException().getMessage());
                            }

                            if(alertProgress.isDialogShowing()) {
                                alertProgress.dismissDialog();
                            }

                            alertInfo.showDialog();
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

        if(users.getNama().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Nama anda masih kosong", Toast.LENGTH_SHORT).show();
            binding.editTextDaftarNama.requestFocus();
            count++;
        }

        if(auths.getEmail().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Email anda masih kosong", Toast.LENGTH_SHORT).show();
            binding.editTextDaftarEmail.requestFocus();
            count++;
        }

        if(auths.getPassword().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Password anda masih kosong", Toast.LENGTH_SHORT).show();
            binding.editTextDaftarPassword.requestFocus();
            count++;
        }

        return (count == 0);
    }
}