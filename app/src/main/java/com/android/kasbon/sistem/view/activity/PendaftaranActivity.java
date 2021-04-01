package com.android.kasbon.sistem.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.databinding.ActivityPendaftaranBinding;
import com.android.kasbon.sistem.utilitas.AlertInfo;
import com.android.kasbon.sistem.utilitas.AlertProgress;
import com.android.kasbon.sistem.utilitas.Preference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class PendaftaranActivity extends AppCompatActivity {

    private ActivityPendaftaranBinding binding;
    private FirebaseAuth auth;
    private AlertProgress alertProgress;
    private AlertInfo alertInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pendaftaran);

        this.auth = FirebaseAuth.getInstance();

        binding.constraintLayoutDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()) {
                    alertProgress = new AlertProgress(v, "Sedang mengirim data");
                    alertProgress.showDialog();
                    auth.createUserWithEmailAndPassword(
                        binding.editTextDaftarEmail.getText().toString(), binding.editTextDaftarPassword.getText().toString()
                    ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isComplete()) {
                                alertProgress.dismissDialog();
                                if(task.isSuccessful()) {
                                    alertInfo = new AlertInfo(PendaftaranActivity.this,"Data berhasil terdaftar");
                                    Preference.setUserData(auth.getCurrentUser());
                                } else {
                                    alertInfo = new AlertInfo(PendaftaranActivity.this,task.getException().getMessage());
                                }
                                alertInfo.showDialog();
                            }
                        }
                    });
                }
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