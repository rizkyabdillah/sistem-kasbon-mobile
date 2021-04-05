package com.android.kasbon.sistem.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.kasbon.sistem.adapter.TransaksiPenjualAdapter;
import com.android.kasbon.sistem.databinding.FragmentHomePenjualBinding;
import com.android.kasbon.sistem.model.ItemKeranjangModel;
import com.android.kasbon.sistem.model.OperationKeranjangModel;
import com.android.kasbon.sistem.view.activity.KeranjangPenjualActivity;
import com.android.kasbon.sistem.view.activity.TransaksiAllActivity;
import com.android.kasbon.sistem.viewmodel.ReadViewModel;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomePenjualFragment extends Fragment {

    private FragmentHomePenjualBinding binding;
    private ReadViewModel readViewModel;
    private TransaksiPenjualAdapter adapter;
    private ItemKeranjangModel itemKeranjangModel;
    private final LifecycleOwner OWNER = this;
    private List<ItemKeranjangModel> listKeranjang;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomePenjualBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemKeranjangModel = new ItemKeranjangModel("","","");
        binding.setItem(itemKeranjangModel);

        readViewModel = ViewModelProviders.of(this).get(ReadViewModel.class);
        listKeranjang = new ArrayList<>();

        binding.recyclerViewTransaksiPenjual.setHasFixedSize(true);
        binding.recyclerViewTransaksiPenjual.setLayoutManager(new LinearLayoutManager(view.getContext()));

        readViewModel.readDataTransaksiAll().observe(OWNER, new Observer<QuerySnapshot>() {
            @Override
            public void onChanged(QuerySnapshot queryDocumentSnapshots) {
                
                adapter = new TransaksiPenjualAdapter(queryDocumentSnapshots);
                binding.recyclerViewTransaksiPenjual.setAdapter(adapter);
            }
        });

        binding.btnLihatSemua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TransaksiAllActivity.class);
                intent.putExtra("IS_READ_SELLER", true);
                startActivity(intent);
            }
        });

        binding.constraintLayoutAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkData()) {
                    listKeranjang.add(itemKeranjangModel);
                    itemKeranjangModel = new ItemKeranjangModel("","","");
                    binding.setItem(itemKeranjangModel);
                    binding.editTextNamaBarang.requestFocus();
                    Toast.makeText(v.getContext(), "Data Berhasil masuk ke keranjang", Toast.LENGTH_SHORT).show();
                    Log.d("=======", "" + listKeranjang.iterator());
                }
            }
        });

        binding.fabMasukKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listKeranjang.size() < 1) {
                    Toast.makeText(v.getContext(), "Keranjang anda masih kosong", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(v.getContext(), KeranjangPenjualActivity.class);
                    intent.putExtra("LIST", (Serializable) new OperationKeranjangModel(listKeranjang));
                    startActivity(intent);
                    listKeranjang.clear();
                }
            }
        });

    }

    private boolean checkData() {
        int count = 0;
        if(itemKeranjangModel.getNamaBarang().isEmpty()) {
            Toast.makeText(getContext(), "Nama barang masih kosong", Toast.LENGTH_SHORT).show();
            count++;
        }
        if(itemKeranjangModel.getHarga().isEmpty()) {
            Toast.makeText(getContext(), "Harga barang masih kosong", Toast.LENGTH_SHORT).show();
            count++;
        }
        if(itemKeranjangModel.getHarga().equals("0")) {
            Toast.makeText(getContext(), "Harga barang tidak boleh 0 (nol)", Toast.LENGTH_SHORT).show();
            count++;
        }
        if(itemKeranjangModel.getJumlah().isEmpty()) {
            Toast.makeText(getContext(), "Jumlah barang masih kosong", Toast.LENGTH_SHORT).show();
            count++;
        }
        if(itemKeranjangModel.getJumlah().equals("0")) {
            Toast.makeText(getContext(), "Jumlah barang tidak boleh 0 (nol)", Toast.LENGTH_SHORT).show();
            count++;
        }
        return (count == 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}