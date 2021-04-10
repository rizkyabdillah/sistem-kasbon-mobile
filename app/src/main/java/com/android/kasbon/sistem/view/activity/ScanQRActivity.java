package com.android.kasbon.sistem.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.databinding.ActivityScanQRBinding;
import com.android.kasbon.sistem.utilitas.AlertProgress;
import com.budiyev.android.codescanner.BarcodeUtils;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

public class ScanQRActivity extends AppCompatActivity {

    private ActivityScanQRBinding binding;
    private CodeScanner mCodeScanner;
    private final Activity THIS = ScanQRActivity.this;
    private final LifecycleOwner OWNER = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scan_q_r);

        final Double LIMIT_KREDIT = getIntent().getDoubleExtra("LIMIT_KREDIT", 0.0);

        mCodeScanner = new CodeScanner(this, binding.scannerView);
        mCodeScanner.setAutoFocusEnabled(true);
        mCodeScanner.setTouchFocusEnabled(true);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final String[] resultText = result.getText().split("/");
                        final Double TOTAL = Double.parseDouble(resultText[1]);
                        if(!resultText[0].substring(0,2).equals("TR")) {
                            Toast.makeText(THIS, "QR Code tidak sah", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(THIS, ResultGagalActivity.class));
                            finish();
                        } else if(TOTAL > LIMIT_KREDIT) {
                            Toast.makeText(THIS, "Limit kredit anda kurang", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(THIS, ResultGagalActivity.class));
                            finish();
                        } else {
                            AlertProgress progress = new AlertProgress(THIS, "Sedang melakukan pembayaran");
                            progress.showDialog();

                        }
                    }
                });
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}