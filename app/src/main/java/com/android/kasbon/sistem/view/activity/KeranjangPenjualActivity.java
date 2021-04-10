package com.android.kasbon.sistem.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.adapter.KeranjangAdapter;
import com.android.kasbon.sistem.databinding.ActivityKeranjangPenjualBinding;
import com.android.kasbon.sistem.model.ItemKeranjangModel;
import com.android.kasbon.sistem.model.OperationKeranjangModel;
import com.android.kasbon.sistem.model.TransaksiModel;
import com.android.kasbon.sistem.utilitas.AlertInfo;
import com.android.kasbon.sistem.utilitas.AlertProgress;
import com.android.kasbon.sistem.utilitas.AlertQRCode;
import com.android.kasbon.sistem.utilitas.UtilsSingleton;
import com.android.kasbon.sistem.viewmodel.InsertViewModel;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class KeranjangPenjualActivity extends AppCompatActivity implements KeranjangAdapter.onSelectedData{

    private InsertViewModel insertViewModel;

    private OperationKeranjangModel operationKeranjangModel;
    private ActivityKeranjangPenjualBinding binding;
    private ItemKeranjangModel itemKeranjangModel;
    private final LifecycleOwner OWNER = this;
    private List<ItemKeranjangModel> listKeranjang;
    private final Activity THIS = KeranjangPenjualActivity.this;

    private final String IDTRANSAKSI = UtilsSingleton.getRandom("TR", 6);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_keranjang_penjual);
        insertViewModel = ViewModelProviders.of(this).get(InsertViewModel.class);

        listKeranjang = new ArrayList<>();

        itemKeranjangModel = new ItemKeranjangModel();
        operationKeranjangModel = new OperationKeranjangModel(listKeranjang);
        binding.setCart(itemKeranjangModel);
        binding.setOperation(operationKeranjangModel);

        binding.recyclerViewKeranjang.setHasFixedSize(true);
        binding.recyclerViewKeranjang.setLayoutManager(new LinearLayoutManager(this));

        // ================

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // ================

        binding.constraintLayoutTunai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listKeranjang.size() > 0) {
                    AlertProgress progress = new AlertProgress(THIS, "Menyimpan Data");
                    progress.showDialog();

                    final TransaksiModel model = new TransaksiModel();
                    model.setStatus_jual(true);
                    model.setStatus_bayar(true);
                    model.setJumlah(operationKeranjangModel.getJumlah());
                    model.setTotal(operationKeranjangModel.getTotal());
                    model.setId_user("000");
                    insertViewModel.insertTransaksi(model, IDTRANSAKSI).observe(OWNER, new Observer<Task<Void>>() {
                        @Override
                        public void onChanged(Task<Void> task) {
                            if(task.isSuccessful()) {
                                insertViewModel.insertDetailBatch(listKeranjang, IDTRANSAKSI).observe(OWNER, new Observer<Task<Void>>() {
                                    @Override
                                    public void onChanged(Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            progress.dismissDialog();
                                            finish();
                                            startActivity(new Intent(THIS, ResultSuksesActivity.class));
                                        } else {
                                            AlertInfo info = new AlertInfo(THIS, task.getException().getMessage(), true);
                                            info.showDialog();
                                        }
                                    }
                                });
                            } else {
                                AlertInfo info = new AlertInfo(THIS, task.getException().getMessage(), true);
                                info.showDialog();
                            }
                        }
                    });
                } else {
                    Toast.makeText(THIS, "Keranjang anda masih kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // ================

        binding.constraintLayoutKasbon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listKeranjang.size() > 0) {
                    AlertProgress progress = new AlertProgress(THIS, "Menyimpan Data");
                    progress.showDialog();

                    final TransaksiModel model = new TransaksiModel();
                    model.setStatus_jual(false);
                    model.setStatus_bayar(false);
                    model.setJumlah(operationKeranjangModel.getJumlah());
                    model.setTotal(operationKeranjangModel.getTotal());
                    model.setId_user("TEMP");
                    insertViewModel.insertTransaksi(model, IDTRANSAKSI).observe(OWNER, new Observer<Task<Void>>() {
                        @Override
                        public void onChanged(Task<Void> task) {
                            if(task.isSuccessful()) {
                                insertViewModel.insertDetailBatch(listKeranjang, IDTRANSAKSI).observe(OWNER, new Observer<Task<Void>>() {
                                    @Override
                                    public void onChanged(Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            progress.dismissDialog();
                                            AlertQRCode alertQRCode = new AlertQRCode(THIS,IDTRANSAKSI + "/" + operationKeranjangModel.getTotal());
                                            alertQRCode.showDialog();
                                        } else {
                                            AlertInfo info = new AlertInfo(THIS, task.getException().getMessage(), true);
                                            info.showDialog();
                                        }
                                    }
                                });
                            } else {
                                AlertInfo info = new AlertInfo(THIS, task.getException().getMessage(), true);
                                info.showDialog();
                            }
                        }
                    });
                } else {
                    Toast.makeText(THIS, "Keranjang anda masih kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // ================

        binding.constraintLayoutAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkData()) {
                    listKeranjang.add(itemKeranjangModel);
                    binding.editTextNamaBarang.requestFocus();

                    itemKeranjangModel = new ItemKeranjangModel();
                    binding.setCart(itemKeranjangModel);

                    operationKeranjangModel = new OperationKeranjangModel(listKeranjang);
                    binding.setOperation(operationKeranjangModel);

                    binding.recyclerViewKeranjang.setAdapter(new KeranjangAdapter(listKeranjang,KeranjangPenjualActivity.this));
                    Toast.makeText(v.getContext(), "Data Berhasil masuk ke keranjang", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // ================


    }

    @Override
    public void onSelected(int position) {
        listKeranjang.remove(position);
        binding.recyclerViewKeranjang.setAdapter(new KeranjangAdapter(listKeranjang,this));
        binding.recyclerViewKeranjang.invalidate();
        binding.setOperation(operationKeranjangModel);
    }

    private boolean checkData() {
        int count = 0;
        if(itemKeranjangModel.getNamaBarang().isEmpty()) {
            Toast.makeText(THIS, "Nama barang masih kosong", Toast.LENGTH_SHORT).show();
            count++;
        }
        if(itemKeranjangModel.getHarga().isEmpty()) {
            Toast.makeText(THIS, "Harga barang masih kosong", Toast.LENGTH_SHORT).show();
            count++;
        }
        if(itemKeranjangModel.getHarga().equals("0")) {
            Toast.makeText(THIS, "Harga barang tidak boleh 0 (nol)", Toast.LENGTH_SHORT).show();
            count++;
        }
        if(itemKeranjangModel.getJumlah().isEmpty()) {
            Toast.makeText(THIS, "Jumlah barang masih kosong", Toast.LENGTH_SHORT).show();
            count++;
        }
        if(itemKeranjangModel.getJumlah().equals("0")) {
            Toast.makeText(THIS, "Jumlah barang tidak boleh 0 (nol)", Toast.LENGTH_SHORT).show();
            count++;
        }
        return (count == 0);
    }
}