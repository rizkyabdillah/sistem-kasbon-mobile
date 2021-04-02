package com.android.kasbon.sistem.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.kasbon.sistem.databinding.FragmentHomePembeliBinding;
import com.android.kasbon.sistem.view.activity.LoginActivity;
import com.android.kasbon.sistem.view.activity.ProfilPembeliActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePembeliFragment extends Fragment {

    private FragmentHomePembeliBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomePembeliBinding.inflate(inflater, container, false);
        final View v = binding.getRoot();

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
                getActivity().finish();
            }
        });


        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}