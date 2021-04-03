package com.android.kasbon.sistem.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.kasbon.sistem.adapter.TransaksiPenjualAdapter;
import com.android.kasbon.sistem.databinding.FragmentHomePenjualBinding;
import com.android.kasbon.sistem.viewmodel.ReadViewModel;
import com.google.firebase.firestore.QuerySnapshot;

public class HomePenjualFragment extends Fragment {

    private FragmentHomePenjualBinding binding;
    private ReadViewModel readViewModel;
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

        binding.recyclerViewTransaksiPenjual.setHasFixedSize(true);
        binding.recyclerViewTransaksiPenjual.setLayoutManager(new LinearLayoutManager(view.getContext()));

        readViewModel.readDataTransaksiAll().observe(OWNER, new Observer<QuerySnapshot>() {
            @Override
            public void onChanged(QuerySnapshot queryDocumentSnapshots) {
                adapter = new TransaksiPenjualAdapter(queryDocumentSnapshots);
                binding.recyclerViewTransaksiPenjual.setAdapter(adapter);
            }
        });

    }
}