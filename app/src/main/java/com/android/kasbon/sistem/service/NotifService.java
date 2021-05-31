package com.android.kasbon.sistem.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.ViewModelProviders;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.view.activity.MainActivity;
import com.android.kasbon.sistem.viewmodel.InsertViewModel;
import com.android.kasbon.sistem.viewmodel.ReadViewModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class NotifService extends Service {

    private static final String TAG = "NotifService";
    private final String CHANNEL_ID = "ChannelNotificationKasbonApp";
//    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        Log.d(TAG, "onStartCommand: SERVICE RUNNING");
        Intent intentNotif = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 312213, intentNotif, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("PERINGATAN!!!")
                .setContentText("JANGAN LUPA UNTUK MEMBAYAR TAGIHAN")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(312213, notification);

        final String ID_TRANSAKSI = intent.getStringExtra("ID_TRANSAKSI");
        Log.d(TAG, "onStartCommand: " + ID_TRANSAKSI);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("transaksi").document(ID_TRANSAKSI).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.getBoolean("status_bayar")) {
                    Log.d(TAG, "onEvent: SERVICE STOPPED");
                    Intent i = new Intent(getApplicationContext(), NotifService.class);
                    stopService(i);
                }
            }
        });
        return START_STICKY;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID, "Foreground Service Channel", NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
