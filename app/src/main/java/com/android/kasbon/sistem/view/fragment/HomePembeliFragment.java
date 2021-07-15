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

import com.android.kasbon.sistem.adapter.TransaksiPembeliAdapter;
import com.android.kasbon.sistem.adapter.TransaksiPenjualAdapter;
import com.android.kasbon.sistem.databinding.FragmentHomePembeliBinding;
import com.android.kasbon.sistem.model.JaminanModel;
import com.android.kasbon.sistem.model.OperationDashboardModel;
import com.android.kasbon.sistem.model.OperationTransaksiModel;
import com.android.kasbon.sistem.model.UserModel;
import com.android.kasbon.sistem.view.activity.LoginActivity;
import com.android.kasbon.sistem.view.activity.ProfilPembeliActivity;
import com.android.kasbon.sistem.view.activity.ScanQRActivity;
import com.android.kasbon.sistem.view.activity.TransaksiAllActivity;
import com.android.kasbon.sistem.viewmodel.ReadViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomePembeliFragment extends Fragment {

    private FragmentHomePembeliBinding binding;
    private ReadViewModel readViewModel;
    private TransaksiPembeliAdapter adapter;
    private final LifecycleOwner OWNER = this;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomePembeliBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        readViewModel = ViewModelProviders.of(this).get(ReadViewModel.class);

        binding.recyclerViewTransaksiPembeli.setHasFixedSize(true);
        binding.recyclerViewTransaksiPembeli.setLayoutManager(new LinearLayoutManager(view.getContext()));

        binding.textViewKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(v.getContext(), "Berhasil keluar", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        });

        // ================

        binding.floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.getDashboard().getLimit() < 1) {
                    Toast.makeText(v.getContext(), "Limit kredit anda kosong, anda tidak bisa melakukan transaksi", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(v.getContext(), ScanQRActivity.class);
                    intent.putExtra("LIMIT_KREDIT", binding.getDashboard().getLimit());
                    startActivity(intent);
                }
            }
        });

        // ================

        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ProfilPembeliActivity.class));
            }
        });

        // ================

        readViewModel.readDataUser(firebaseUser.getUid()).observe(OWNER, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                readViewModel.readDataJaminan(firebaseUser.getUid()).observe(OWNER, new Observer<JaminanModel>() {
                    @Override
                    public void onChanged(JaminanModel jaminanModel) {
                        binding.setDashboard(new OperationDashboardModel(userModel, jaminanModel));
                    }
                });
            }
        });

        // ================

        readViewModel.readDataTransaksiUser(firebaseUser.getUid()).observe(OWNER, new Observer<QuerySnapshot>() {
            @Override
            public void onChanged(QuerySnapshot value) {
                List<OperationTransaksiModel> list = new ArrayList<>();
                Log.d("=========", "onChanged: " + value.size());
                    for (QueryDocumentSnapshot doc : value) {
                        if(!doc.getString("id_user").equals("TEMP")) {
                            try {
                                OperationTransaksiModel model = new OperationTransaksiModel();
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

                adapter = new TransaksiPembeliAdapter(list, true);
                binding.recyclerViewTransaksiPembeli.setAdapter(adapter);
            }
        });

        // ================


        binding.btnLihatSemua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TransaksiAllActivity.class);
                intent.putExtra("IS_READ_SELLER", false);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}