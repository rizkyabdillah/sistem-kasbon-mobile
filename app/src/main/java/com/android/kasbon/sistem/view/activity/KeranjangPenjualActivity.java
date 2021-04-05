package com.android.kasbon.sistem.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.adapter.KeranjangAdapter;
import com.android.kasbon.sistem.databinding.ActivityKeranjangPenjualBinding;
import com.android.kasbon.sistem.model.ItemKeranjangModel;
import com.android.kasbon.sistem.model.OperationKeranjangModel;

import java.io.Serializable;
import java.util.List;

public class KeranjangPenjualActivity extends AppCompatActivity implements KeranjangAdapter.onSelectedData{

    private OperationKeranjangModel model;
    private ActivityKeranjangPenjualBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_keranjang_penjual);

        model = (OperationKeranjangModel) getIntent().getSerializableExtra("LIST");
        binding.setOperation(model);

        binding.recyclerViewKeranjang.setHasFixedSize(true);
        binding.recyclerViewKeranjang.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewKeranjang.setAdapter(new KeranjangAdapter(model.getListKeranjang(),this));

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    @Override
    public void onSelected(int position) {
        model.getListKeranjang().remove(position);
        binding.recyclerViewKeranjang.removeViewAt(position);
        binding.recyclerViewKeranjang.invalidate();

        binding.setOperation(model);
    }
}