package com.android.kasbon.sistem.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Toast;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.databinding.ActivityProfilPembeliBinding;
import com.android.kasbon.sistem.model.User;
import com.android.kasbon.sistem.utilitas.AlertInfo;
import com.android.kasbon.sistem.utilitas.AlertProgress;
import com.android.kasbon.sistem.viewmodel.ReadViewModel;
import com.android.kasbon.sistem.viewmodel.UpdateViewModel;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class ProfilPembeliActivity extends AppCompatActivity {

    private ReadViewModel readViewModel;
    private UpdateViewModel updateViewModel;
    private AlertProgress alertProgress;
    private AlertInfo alertInfo;
    private ActivityProfilPembeliBinding binding;
    private FirebaseUser firebaseUser;
    private final LifecycleOwner OWNER = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profil_pembeli);

        readViewModel = ViewModelProviders.of(this).get(ReadViewModel.class);
        updateViewModel = ViewModelProviders.of(this).get(UpdateViewModel.class);

        alertProgress = new AlertProgress(this, "Sedang mengambil data");
        alertProgress.showDialog();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        readViewModel.readDataUser(firebaseUser.getUid()).observe(OWNER, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                alertProgress.dismissDialog();
                if(user != null) {
                    user.setEmail(firebaseUser.getEmail());
                    binding.setUser(user);
                } else {
                    Toast.makeText(getApplicationContext(), "Terdapat kesalahan koneksi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.constraintLayoutSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()) {

                    Map<String, Object> user = new HashMap<>();
                    user.put("nama", binding.editTextTextProfileNama.getText().toString());
                    user.put("telepon", binding.editTextTextProfileTelepon.getText().toString());
                    user.put("alamat", binding.editTextProfileAlamat.getText().toString());

                    alertProgress = new AlertProgress(v, "Sedang mengupdate data");
                    alertProgress.showDialog();
                    updateViewModel.updateDataUser(user, firebaseUser.getUid()).observe(OWNER, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            alertProgress.dismissDialog();
                            if(!s.equals("SUKSES")) {
                                alertInfo = new AlertInfo(ProfilPembeliActivity.this, s);
                                alertInfo.showDialog();
                            } else {

                                boolean isEmailEqual = binding.editTextTextProfileEmail.getText().toString().equals(firebaseUser.getEmail());
                                boolean isPasswordEmpty = binding.editTextTextProfilePassword.getText().toString().isEmpty();

                                if(!isEmailEqual) {
                                    updateEmail(
                                        binding.editTextTextProfileEmail.getText().toString(),
                                        binding.editTextTextProfilePassword.getText().toString(),
                                        isPasswordEmpty
                                    );

                                    isPasswordEmpty = true;
                                }

                                if(!isPasswordEmpty) {
                                    updatePassword(binding.editTextTextProfilePassword.getText().toString());
                                }

                                if(isEmailEqual && isPasswordEmpty) {
                                    intentBackHome();
                                }

                            }
                        }
                    });
                }
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void updateEmail(String email, String pass, boolean isUpdatePassword) {
        alertProgress = new AlertProgress(binding.getRoot(), "Sedang mengupdate email");
        alertProgress.showDialog();

        updateViewModel.updateEmailUser(
            email, firebaseUser.getEmail(), binding.getUser().getPassword()
        ) .observe(OWNER, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                alertProgress.dismissDialog();
                if(!s.equals("SUKSES")) {
                    alertInfo = new AlertInfo(ProfilPembeliActivity.this, s);
                    alertInfo.showDialog();
                } else {
                    if(!isUpdatePassword) {
                        updatePassword(pass);
                    } else {
                        intentBackHome();
                    }
                }
            }
        });
    }

    private void updatePassword(String password) {
        alertProgress = new AlertProgress(binding.getRoot(), "Sedang mengupdate password");
        alertProgress.showDialog();

        updateViewModel.updatePasswordUser(
            password, firebaseUser.getEmail(), binding.getUser().getPassword()
        ).observe(OWNER, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(!s.equals("SUKSES")) {
                    alertProgress.dismissDialog();
                    alertInfo = new AlertInfo(ProfilPembeliActivity.this, s);
                    alertInfo.showDialog();
                } else {
                    Map<String, Object> user = new HashMap<>();
                    user.put("password", password);
                    updateViewModel.updateDataUser(user, firebaseUser.getUid()).observe(OWNER, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            alertProgress.dismissDialog();
                            intentBackHome();
                        }
                    });
                }
            }
        });
    }

    private void intentBackHome() {
        Intent intent = new Intent(ProfilPembeliActivity.this, MainActivity.class);
        alertInfo = new AlertInfo(ProfilPembeliActivity.this, "Data berhasil terupdate", intent);
        alertInfo.showDialog();
    }

    private boolean checkInput() {
        int count = 0;

        if(binding.editTextTextProfileNama.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), getPrefixInputEmpty("nama"), Toast.LENGTH_SHORT).show();
            count++;
        }

        if(binding.editTextTextProfileEmail.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), getPrefixInputEmpty("email"), Toast.LENGTH_SHORT).show();
            count++;
        }

        if(binding.editTextTextProfileTelepon.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), getPrefixInputEmpty("telepon"), Toast.LENGTH_SHORT).show();
            count++;
        }

        if(binding.editTextProfileAlamat.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), getPrefixInputEmpty("alamat"), Toast.LENGTH_SHORT).show();
            count++;
        }

        return (count == 0);
    }

    private String getPrefixInputEmpty(String prefix) {
        return "Field " + prefix + " tidak boleh kosong";
    }
}