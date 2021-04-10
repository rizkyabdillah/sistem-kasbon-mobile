package com.android.kasbon.sistem.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.databinding.ActivityProfilPembeliBinding;
import com.android.kasbon.sistem.model.AuthModel;
import com.android.kasbon.sistem.model.ConstantModel;
import com.android.kasbon.sistem.model.JaminanModel;
import com.android.kasbon.sistem.model.OperationProfileModel;
import com.android.kasbon.sistem.model.UserModel;
import com.android.kasbon.sistem.utilitas.AlertInfo;
import com.android.kasbon.sistem.utilitas.AlertProgress;
import com.android.kasbon.sistem.viewmodel.InsertViewModel;
import com.android.kasbon.sistem.viewmodel.ReadViewModel;
import com.android.kasbon.sistem.viewmodel.UpdateViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class ProfilPembeliActivity extends AppCompatActivity {

    private ReadViewModel readViewModel;
    private UpdateViewModel updateViewModel;
    private InsertViewModel insertViewModel;

    private ActivityProfilPembeliBinding binding;
    private FirebaseUser firebaseUser;
    private ConstantModel constant;
    private final LifecycleOwner OWNER = this;
    private final Activity THIS = ProfilPembeliActivity.this;
    private String imageUri = null, passChange = null;
    private double limitYangDidapat = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profil_pembeli);

        readViewModel = ViewModelProviders.of(this).get(ReadViewModel.class);
        updateViewModel = ViewModelProviders.of(this).get(UpdateViewModel.class);
        insertViewModel = ViewModelProviders.of(this).get(InsertViewModel.class);










        // Set alert dialog progress
        AlertProgress alertProgress = new AlertProgress(this, "Sedang mengambil data");
        alertProgress.showDialog();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        binding.setAuth(new AuthModel(firebaseUser.getEmail(), ""));
        // Read all data
        readViewModel.readDataUser(firebaseUser.getUid()).observe(OWNER, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                binding.setUser(userModel);
                readViewModel.readDataJaminan(firebaseUser.getUid()).observe(OWNER, new Observer<JaminanModel>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onChanged(JaminanModel jaminanModel) {
                        int checked = jaminanModel.getJenis_jaminan() ? R.id.rdbYes : R.id.rdbNo;
                        binding.rdbGroupJaminanDititipkan.check(checked);
                        binding.textBeratEmas.setText(jaminanModel.getBerat_emas());
                        setPicasso(Uri.parse(jaminanModel.getFoto()));
                        imageUri = jaminanModel.getFoto();
                        readViewModel.readDataHargaEmas().observe(OWNER, new Observer<ConstantModel>() {
                            @Override
                            public void onChanged(ConstantModel constantModel) {
                                alertProgress.dismissDialog();
                                constant = constantModel;

                                limitYangDidapat = getPendapatan(constant, Double.parseDouble(jaminanModel.getBerat_emas()));
                                binding.textViewLimitYangDidapat.setText(formatCurrency(limitYangDidapat));

                                OperationProfileModel model = new OperationProfileModel(jaminanModel, constantModel);
                                binding.setOperation(model);
                            }
                        });
                    }
                });
            }
        });




        // If button simpan clicked
        binding.constraintLayoutSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()) {
                    AlertProgress alertProgress = new AlertProgress(THIS, "Sedang mengupdate data");
                    alertProgress.showDialog();

                    final double BERAT_EMAS = Double.parseDouble(binding.textBeratEmas.getText().toString());

                    JaminanModel jaminanModel = new JaminanModel();
                    jaminanModel.setBerat_emas(String.valueOf(BERAT_EMAS));
                    jaminanModel.setLimit_kredit(limitYangDidapat);
                    jaminanModel.setJenis_jaminan(binding.rdbYes.isChecked());

                    UserModel userModel = binding.getUser();
                    passChange = userModel.getPassword();
                    if(!binding.getAuth().getPassword().isEmpty()) {
                        userModel.setPassword(binding.getAuth().getPassword());
                    }

                    updateViewModel.updateBatchUserJaminan(userModel, jaminanModel,firebaseUser.getUid()).observe(OWNER, new Observer<Task<Void>>() {
                        @Override
                        public void onChanged(Task<Void> task) {
                            if(task.isSuccessful()) {
                                Log.d("===============", "LOLOS");
                                updateViewModel.updateEmailUser(binding.getAuth(), passChange).observe(OWNER, new Observer<Task<Void>>() {
                                    @Override
                                    public void onChanged(Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            Log.d("===============", "LOLOS1");
                                            if(passChange.equals(userModel.getPassword())) {
                                                alertProgress.dismissDialog();
                                                new AlertInfo(THIS, "Data berhasil diubah", true).showDialog();
                                            } else {
                                                updateViewModel.updatePasswordUser(binding.getAuth(), passChange).observe(OWNER, new Observer<Task<Void>>() {
                                                    @Override
                                                    public void onChanged(Task<Void> task) {
                                                        if(task.isComplete()) {
                                                            Log.d("===============", "LOLOS2");
                                                            alertProgress.dismissDialog();
                                                            if (!task.isSuccessful()) {
                                                                new AlertInfo(THIS, task.getException().getMessage()).showDialog();
                                                            } else {
                                                                new AlertInfo(THIS, "Data berhasil diubah", true).showDialog();
                                                            }
                                                        }
                                                    }
                                                });
                                            }
                                        } else {
                                            alertProgress.dismissDialog();
                                            new AlertInfo(THIS, task.getException().getMessage()).showDialog();
                                        }
                                    }
                                });
                            } else {
                                alertProgress.dismissDialog();
                                new AlertInfo(THIS, task.getException().getMessage()).showDialog();
                            }
                        }
                    });
                }
            }
        });

        // If button back clicked
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // If button minus clicked
        binding.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final double beratEmas = Double.parseDouble(binding.textBeratEmas.getText().toString()) - 0.1;
                if(beratEmas >= 0.1) {
                    @SuppressLint("DefaultLocale")
                    String values = String.format("%.1f", beratEmas);
                    binding.textBeratEmas.setText(values);
                    limitYangDidapat = getPendapatan(constant,beratEmas);
                    binding.textViewLimitYangDidapat.setText(formatCurrency(limitYangDidapat));
                }
            }
        });

        // If button plus clicked
        binding.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final double beratEmas = Double.parseDouble(binding.textBeratEmas.getText().toString()) + 0.1;
                if(beratEmas <= 2.0) {
                    @SuppressLint("DefaultLocale")
                    String values = String.format("%.1f", beratEmas);
                    binding.textBeratEmas.setText(values);
                    limitYangDidapat = getPendapatan(constant,beratEmas);
                    binding.textViewLimitYangDidapat.setText(formatCurrency(limitYangDidapat));
                }
            }
        });

        // If radio group changed
        binding.rdbGroupJaminanDititipkan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(constant != null) {
                    final double beratEmas = Double.parseDouble(binding.textBeratEmas.getText().toString());
                    final double persen = (checkedId == R.id.rdbYes) ? 0.75 : 0.5;
                    limitYangDidapat = Double.parseDouble(constant.getHarga()) * beratEmas * persen;
                    binding.textViewLimitYangDidapat.setText(formatCurrency(limitYangDidapat));
                }
            }
        });

        // If image jaminan clicked
        binding.imageJaminan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"),1);
            }
        });

    }



    private double getPendapatan(ConstantModel constant, double beratEmas) {
        final double persen = binding.rdbYes.isChecked() ? 0.75 : 0.5;
        return Double.parseDouble(constant.getHarga()) * beratEmas * persen;
    }

    @SuppressLint("DefaultLocale")
    private String formatCurrency(Double value) {
        return String.format("%4s %,.0f", "Rp. ", value);
    }

    // =================================

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null) {
            AlertProgress alertProgress = new AlertProgress(this, "Sedang mengupload foto");
            alertProgress.showDialog();
            insertViewModel.insertFoto(data.getData()).observe(OWNER, new Observer<Task<Uri>>() {
                @Override
                public void onChanged(Task<Uri> task) {
                    if(task.isSuccessful()) {
                        imageUri = task.getResult().toString();
                        updateViewModel.updateUriFoto(imageUri.toString(), firebaseUser.getUid()).observe(OWNER, new Observer<Task<Void>>() {
                            @Override
                            public void onChanged(Task<Void> task) {
                                alertProgress.dismissDialog();
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Foto berhasil diupdate", Toast.LENGTH_SHORT).show();
                                } else {
                                    new AlertInfo(THIS, task.getException().getMessage()).showDialog();
                                }
                            }
                        });
                    } else {
                        alertProgress.dismissDialog();
                        new AlertInfo(THIS, task.getException().getMessage()).showDialog();
                    }
                }
            });
        } else {
            Toast.makeText(this, "Tidak ada gambar yang dipilih", Toast.LENGTH_SHORT).show();
        }
    }

    // =================================

    public void setPicasso(Uri uri) {
        Picasso.get().load(uri).placeholder(R.drawable.ic_image).into(binding.imageJaminan);
    }

    // =================================

    private boolean checkInput() {
        int count = 0;

        if(binding.getUser().getNama().isEmpty()) {
            Toast.makeText(getApplicationContext(), getPrefixInputEmpty("nama"), Toast.LENGTH_SHORT).show();
            count++;
        }

        if(binding.getUser().getTelepon().isEmpty()) {
            Toast.makeText(getApplicationContext(), getPrefixInputEmpty("telepon"), Toast.LENGTH_SHORT).show();
            count++;
        }

        if(binding.getUser().getAlamat().isEmpty()) {
            Toast.makeText(getApplicationContext(), getPrefixInputEmpty("alamat"), Toast.LENGTH_SHORT).show();
            count++;
        }

        if(binding.getAuth().getEmail().isEmpty()) {
            Toast.makeText(getApplicationContext(), getPrefixInputEmpty("email"), Toast.LENGTH_SHORT).show();
            count++;
        }

        if(imageUri.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Foto jaminan belum terpilih", Toast.LENGTH_SHORT).show();
            count++;
        }

        return (count == 0);
    }

    private String getPrefixInputEmpty(String prefix) {
        return "Field " + prefix + " tidak boleh kosong";
    }
}