package com.android.kasbon.sistem.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.databinding.ActivityResultSuksesBinding;

public class ResultSuksesActivity extends AppCompatActivity {

    private ActivityResultSuksesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_result_sukses);
        binding.constraintLayoutMenuUtama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}