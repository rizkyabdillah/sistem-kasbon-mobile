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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.kasbon.sistem.adapter.TransaksiPembeliAdapter;
import com.android.kasbon.sistem.databinding.FragmentHomePembeliBinding;
import com.android.kasbon.sistem.model.UserModel;
import com.android.kasbon.sistem.view.activity.LoginActivity;
import com.android.kasbon.sistem.view.activity.ProfilPembeliActivity;
import com.android.kasbon.sistem.view.activity.TransaksiAllActivity;
import com.android.kasbon.sistem.viewmodel.ReadViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QuerySnapshot;

public class HomePembeliFragment extends Fragment {

    private FragmentHomePembeliBinding binding;
    private ReadViewModel readViewModel;
    private TransaksiPembeliAdapter adapter;
    private FirebaseUser firebaseUser;
    private final LifecycleOwner OWNER = this;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomePembeliBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

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

        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ProfilPembeliActivity.class));
            }
        });

        readViewModel.readDataTransaksiUser(firebaseUser.getUid()).observe(OWNER, new Observer<QuerySnapshot>() {
            @Override
            public void onChanged(QuerySnapshot queryDocumentSnapshots) {

                adapter = new TransaksiPembeliAdapter(queryDocumentSnapshots);
                binding.recyclerViewTransaksiPembeli.setAdapter(adapter);
            }
        });

        readViewModel.readDataUser(firebaseUser.getUid()).observe(OWNER, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                binding.setUserModel(userModel);
            }
        });

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