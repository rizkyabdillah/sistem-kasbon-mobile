package com.android.kasbon.sistem.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.kasbon.sistem.databinding.FragmentHomePembeliBinding;

public class HomePembeliFragment extends Fragment {

    private FragmentHomePembeliBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomePembeliBinding.inflate(inflater, container, false);
        final View v = binding.getRoot();





        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}