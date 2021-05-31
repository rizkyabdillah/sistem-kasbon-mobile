package com.android.kasbon.sistem.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.databinding.ActivityScanQRBinding;
import com.android.kasbon.sistem.service.AlarmReceiver;
import com.android.kasbon.sistem.utilitas.AlertProgress;
import com.android.kasbon.sistem.viewmodel.UpdateViewModel;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.Result;

import java.util.Calendar;

public class ScanQRActivity extends AppCompatActivity {

    private UpdateViewModel updateViewModel;

    private ActivityScanQRBinding binding;
    private CodeScanner mCodeScanner;
    private final Activity THIS = ScanQRActivity.this;
    private final LifecycleOwner OWNER = this;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scan_q_r);

        updateViewModel = ViewModelProviders.of(this).get(UpdateViewModel.class);

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
                        if(!resultText[0].substring(0,2).equals("TR")) {
                            Toast.makeText(THIS, "Barcode salah", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(THIS, ResultGagalActivity.class));
                            finish();
                        } else {
                            final Double TOTAL = Double.parseDouble(resultText[1]);
                            if (TOTAL > LIMIT_KREDIT) {
                                Toast.makeText(THIS, "Limit kredit anda kurang", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(THIS, ResultGagalActivity.class));
                            } else {
                                AlertProgress progress = new AlertProgress(THIS, "Sedang melakukan pembayaran");
                                progress.showDialog();
                                final int LIMIT_LESS = (int) (LIMIT_KREDIT - TOTAL);
                                updateViewModel.updateBatchTransaksiQR(resultText[0], firebaseUser.getUid(), LIMIT_LESS).observe(OWNER, new Observer<Task<Void>>() {
                                    @Override
                                    public void onChanged(Task<Void> task) {
                                        progress.dismissDialog();
                                        if (task.isSuccessful()) {
                                            Intent alarmIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
                                            alarmIntent.putExtra("ID_TRANSAKSI", resultText[0]);
                                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 123, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                            Calendar cal = Calendar.getInstance();
                                            cal.add(Calendar.MINUTE, 1);
                                            cal.set(Calendar.SECOND, 0);
                                            AlarmManager manager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (android.os.Build.VERSION.SDK_INT >= 23) {
                                                        manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                                                        Log.d("==============", "SET ALARM WHILE IDDLE");
                                                    } else if (android.os.Build.VERSION.SDK_INT >= 19) {
                                                        manager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                                                        Log.d("==============", "SET ALARM EXACT");
                                                    } else {
                                                        manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                                                        Log.d("==============", "SET ALARM");
                                                    }
                                                }
                                            }, 2000);

                                            Log.d("==============", "onChanged: NOTIF CHANGED AT " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE)) ;
                                            startActivity(new Intent(THIS, ResultSuksesActivity.class));
                                        } else {
                                            Toast.makeText(THIS, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                        finish();
                                    }
                                });
                            }
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