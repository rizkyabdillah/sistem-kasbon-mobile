package com.android.kasbon.sistem.utilitas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.databinding.ViewCustomDialogInfoBinding;
import com.intentfilter.androidpermissions.PermissionManager;

public class AlertInfo {

    private final AlertDialog alert;
    private final View views;
    private ViewCustomDialogInfoBinding binding;

    public AlertInfo(Activity a) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(a);
        this.views = LayoutInflater.from(a.getApplicationContext()).inflate(R.layout.view_custom_dialog_info,null);
        binding = DataBindingUtil.bind(views);
        dialog.setView(binding.getRoot()).setCancelable(false);

        this.alert = dialog.create();
        this.alert.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }

    public AlertInfo(Activity a, String textCustom) {
        this(a);
        binding.textCustom.setText(textCustom);
        binding.btnDialogInformasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
    }

    public AlertInfo(Activity a, String textCustom, boolean isFinish) {
        this(a);
        binding.textCustom.setText(textCustom);
        binding.btnDialogInformasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                if(isFinish) {
                    a.finish();
                }
            }
        });
    }

    public AlertInfo(Activity a, String textCustom, Intent intent) {
        this(a);
        binding.textCustom.setText(textCustom);
        binding.btnDialogInformasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                a.finish();
                a.startActivity(intent);
            }
        });
    }

    public AlertInfo(Activity a, PermissionManager permissionManager) {
        this(a);
        binding.textCustom.setText("Aplikasi ini membutuhkan akses kamera dan storage, enable permission?");
        binding.btnDialogInformasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", a.getPackageName(), null);
                intent.setData(uri);
                a.startActivityForResult(intent, permissionManager.getResultCode());
                a.finish();
            }
        });
    }

    public void showDialog() {
        this.alert.show();
    }

    public boolean isDialogShowing() {
        return this.alert.isShowing();
    }

    public void dismissDialog() {
        this.alert.dismiss();
    }


}
