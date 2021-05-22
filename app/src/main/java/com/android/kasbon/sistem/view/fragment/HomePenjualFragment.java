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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.kasbon.sistem.adapter.TransaksiPenjualAdapter;
import com.android.kasbon.sistem.databinding.FragmentHomePenjualBinding;
import com.android.kasbon.sistem.model.ConstantModel;
import com.android.kasbon.sistem.model.OperationTransaksiModel;
import com.android.kasbon.sistem.utilitas.AlertProgress;
import com.android.kasbon.sistem.view.activity.KeranjangPenjualActivity;
import com.android.kasbon.sistem.view.activity.LoginActivity;
import com.android.kasbon.sistem.view.activity.TransaksiAllActivity;
import com.android.kasbon.sistem.viewmodel.ReadViewModel;
import com.android.kasbon.sistem.viewmodel.UpdateViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomePenjualFragment extends Fragment implements TransaksiPenjualAdapter.onSelectedData{

    private ReadViewModel readViewModel;
    private UpdateViewModel updateViewModel;

    private FragmentHomePenjualBinding binding;
    private TransaksiPenjualAdapter adapter;
    private final LifecycleOwner OWNER = this;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomePenjualBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        readViewModel = ViewModelProviders.of(this).get(ReadViewModel.class);
        updateViewModel = ViewModelProviders.of(this).get(UpdateViewModel.class);

        binding.recyclerViewTransaksiPenjual.setHasFixedSize(true);
        binding.recyclerViewTransaksiPenjual.setLayoutManager(new LinearLayoutManager(view.getContext()));

        // ================

        readViewModel.readDataHargaEmas().observe(OWNER, new Observer<ConstantModel>() {
            @Override
            public void onChanged(ConstantModel constantModel) {
                binding.setConstant(constantModel);
            }
        });

        // ================

        readViewModel.readAllDataNameUser().observe(OWNER, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(Map<String, Object> name) {
                readViewModel.readDataTransaksiAll().observe(OWNER, new Observer<QuerySnapshot>() {
                    @Override
                    public void onChanged(QuerySnapshot value) {
                        List<OperationTransaksiModel> list = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            if(!doc.getString("id_user").equals("TEMP")) {
                                try {
                                    OperationTransaksiModel model = new OperationTransaksiModel();
                                    model.setNama(name.get(doc.getString("id_user")).toString());
                                    model.setJumlah(doc.getDouble("total"));
                                    model.setStatusBayar(doc.getBoolean("status_bayar"));
                                    model.setStatusJual(doc.getBoolean("status_jual"));
                                    model.setTanggal(doc.getString("tanggal"));
                                    list.add(model);
                                } catch (Exception e) {
                                    Log.d("ERROR", e.getMessage());
                                }
                            }
                        }

                        adapter = new TransaksiPenjualAdapter(list, true, HomePenjualFragment.this);
                        binding.recyclerViewTransaksiPenjual.setAdapter(adapter);
                    }
                });
            }
        });

        // ================

        binding.btnLihatSemua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TransaksiAllActivity.class);
                intent.putExtra("IS_READ_SELLER", true);
                startActivity(intent);
            }
        });

        // ================

        binding.constraintLayoutSimpanHargaEmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkData()) {
                    AlertProgress alertProgress = new AlertProgress(v.getContext(), "Sedang menyimpan data");
                    alertProgress.showDialog();
                    updateViewModel.updateHargaEmas(binding.getConstant().getHarga()).observe(OWNER, new Observer<Task<Void>>() {
                        @Override
                        public void onChanged(Task<Void> task) {
                            alertProgress.dismissDialog();
                            Toast.makeText(v.getContext(), "Harga emas berhasil disimpan", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        // ================

        binding.fabMasukKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), KeranjangPenjualActivity.class));
            }
        });

        // ================

        binding.txtKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(v.getContext(), "Berhasil keluar", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });

    }

    private boolean checkData() {
        int count = 0;

        if(binding.getConstant().getHarga().isEmpty()) {
            Toast.makeText(getActivity(), "Kolom harga emas masih kosong", Toast.LENGTH_SHORT).show();
            count++;
        }

        if(binding.getConstant().getHarga().equals("0")) {
            Toast.makeText(getActivity(), "Kolom harga emas tidak boleh nol (0)", Toast.LENGTH_SHORT).show();
            count++;

        }

        return (count == 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onSelected(int position) {

    }
}