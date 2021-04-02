package com.android.kasbon.sistem.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.databinding.ActivityLoginBinding;
import com.android.kasbon.sistem.utilitas.AlertInfo;
import com.android.kasbon.sistem.utilitas.AlertProgress;
import com.android.kasbon.sistem.viewmodel.AuthViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends AppCompatActivity{

    private AuthViewModel viewModel;
    private ActivityLoginBinding binding;
    private AlertProgress alertProgress;
    private AlertInfo alertInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
//
        viewModel = ViewModelProviders.of(this).get(AuthViewModel.class);
//
        binding.constraintLayoutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()) {

                    alertProgress = new AlertProgress(v, "Sedang mengautentikasi data");
                    alertProgress.showDialog();

                    viewModel.firebaseSign(
                        binding.editTextLoginEmail.getText().toString(), binding.editTextLoginPassword.getText().toString()
                    ).observe(LoginActivity.this, new Observer<Task<AuthResult>>() {
                        @Override
                        public void onChanged(Task<AuthResult> task) {
                            alertProgress.dismissDialog();
                            if(task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                alertInfo = new AlertInfo(LoginActivity.this,"Berhasil masuk", intent);
                            } else {
                                alertInfo = new AlertInfo(LoginActivity.this, task.getException().getMessage());
                            }
                            alertInfo.showDialog();
                        }
                    });
                }
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

}