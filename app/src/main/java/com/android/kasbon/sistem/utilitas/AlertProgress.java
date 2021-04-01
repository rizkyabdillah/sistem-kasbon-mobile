package com.android.kasbon.sistem.utilitas;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.android.kasbon.sistem.R;

public class AlertProgress {

    private final AlertDialog alert;
    private final View views;

    public AlertProgress(View v) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
        this.views = LayoutInflater.from(v.getContext()).inflate(R.layout.view_custom_dialog_progress,null);

        dialog.setView(views).setCancelable(false);

        this.alert = dialog.create();
        this.alert.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

    }

    public AlertProgress(Context c) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(c);
        this.views = LayoutInflater.from(c).inflate(R.layout.view_custom_dialog_progress,null);

        dialog.setView(views).setCancelable(false);

        this.alert = dialog.create();
        this.alert.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

    }

    public AlertProgress(View v, String textCustom) {
        this(v);
        TextView tv = views.findViewById(R.id.textCustom);
        tv.setText(textCustom);
    }

    public AlertProgress(Context c, String textCustom) {
        this(c);
        TextView tv = views.findViewById(R.id.textCustom);
        tv.setText(textCustom);
    }

    public void showDialog() {
        this.alert.show();
    }

    public void dismissDialog() {
        this.alert.dismiss();
    }

    public boolean isDialogShowing() {
        return this.alert.isShowing();
    }

}
