package com.android.kasbon.sistem.service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, NotifService.class);
        i.putExtra("ID_TRANSAKSI", intent.getStringExtra("ID_TRANSAKSI"));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(i);
    }
}
