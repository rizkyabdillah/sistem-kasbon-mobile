package com.android.kasbon.sistem.utilitas;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.databinding.ViewCustomDialogQrcodeBinding;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class AlertQRCode {

    private final AlertDialog alert;
    private final View views;
    private ViewCustomDialogQrcodeBinding binding;

    public AlertQRCode(Activity a, String content) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(a);
        this.views = LayoutInflater.from(a.getApplicationContext()).inflate(R.layout.view_custom_dialog_qrcode,null);
        binding = DataBindingUtil.bind(views);
        dialog.setView(binding.getRoot()).setCancelable(false);

        try {
            final BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.encodeBitmap(content, BarcodeFormat.QR_CODE, 800,800);
            binding.imageBarcode.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
        }

        binding.btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                a.finish();
            }
        });

        this.alert = dialog.create();
        this.alert.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }

    public void showDialog() {
        this.alert.show();
    }

    public void dismissDialog() {
        this.alert.dismiss();
    }
}
