package com.android.kasbon.sistem.repository;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.view.activity.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {

    private final String CHANNEL_ID = "ChannelNotificationKasbonApp";

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel(context);
        Intent intentNotif = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1212, intentNotif, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("PERINGATAN!!  ")
                .setContentText("Jangan lupa bayar tagihanmu ya!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();
        NotificationManagerCompat compat = NotificationManagerCompat.from(context);
        compat.notify(1212, notification);
    }

    private void createNotificationChannel(Context c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID, "Foreground Service Channel", NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = c.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
