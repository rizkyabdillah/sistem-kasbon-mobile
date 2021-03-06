package com.android.kasbon.sistem.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.databinding.ActivityResultGagalBinding;
import com.android.kasbon.sistem.service.AlarmReceiver;

import java.util.Calendar;

public class ResultGagalActivity extends AppCompatActivity {

    private ActivityResultGagalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_result_gagal);

        binding.constraintLayoutUlangi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), ScanQRActivity.class));
                finish();
            }
        });

        binding.textViewMenuUtama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}