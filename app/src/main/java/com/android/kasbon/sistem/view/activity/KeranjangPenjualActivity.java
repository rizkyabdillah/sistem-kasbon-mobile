package com.android.kasbon.sistem.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.adapter.KeranjangAdapter;
import com.android.kasbon.sistem.databinding.ActivityKeranjangPenjualBinding;
import com.android.kasbon.sistem.model.ItemKeranjangModel;
import com.android.kasbon.sistem.model.OperationKeranjangModel;

import java.util.ArrayList;
import java.util.List;

public class KeranjangPenjualActivity extends AppCompatActivity implements KeranjangAdapter.onSelectedData{

    private OperationKeranjangModel operationKeranjangModel;
    private ActivityKeranjangPenjualBinding binding;
    private ItemKeranjangModel itemKeranjangModel;
    private final LifecycleOwner OWNER = this;
    private List<ItemKeranjangModel> listKeranjang;
    private final Context THIS = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_keranjang_penjual);
        listKeranjang = new ArrayList<>();

        itemKeranjangModel = new ItemKeranjangModel();
        operationKeranjangModel = new OperationKeranjangModel(listKeranjang);
        binding.setCart(itemKeranjangModel);
        binding.setOperation(operationKeranjangModel);

        binding.recyclerViewKeranjang.setHasFixedSize(true);
        binding.recyclerViewKeranjang.setLayoutManager(new LinearLayoutManager(this));

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.constraintLayoutTunai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(THIS, "Data pembelian sukses", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(v.THIS, ));
            }
        });

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
                    Log.d("=======", "" + listKeranjang.iterator());
                }
            }
        });


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