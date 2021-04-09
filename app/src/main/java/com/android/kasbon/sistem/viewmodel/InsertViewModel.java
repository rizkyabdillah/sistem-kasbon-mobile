package com.android.kasbon.sistem.viewmodel;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.kasbon.sistem.model.JaminanModel;
import com.android.kasbon.sistem.model.UserModel;
import com.android.kasbon.sistem.repository.InsertRepository;
import com.google.android.gms.tasks.Task;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class InsertViewModel extends AndroidViewModel {

    private final InsertRepository repository;
    private final Context context;

    public InsertViewModel(@NonNull Application application) {
        super(application);
        this.repository = new InsertRepository();
        this.context = application.getApplicationContext();
    }

    public MutableLiveData<Task<Void>> insertBatchUserJaminan(UserModel userModel, JaminanModel jaminan, String idUser) {
        return repository.insertBatchUserJaminan(userModel, jaminan, idUser);
    }

    public MutableLiveData<Task<Uri>> insertFoto(Uri uri){
        return repository.insertFoto(getByteBitmap(uri));
    }



    private byte[] getByteBitmap(Uri uri) {
        Bitmap b = null;
        try{
            b = MediaStore.Images.Media.getBitmap(this.context.getContentResolver(), uri);
        } catch (Exception e) {
            Log.e("Error convert images : ", e.getMessage());
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Bitmap scale = scaleDown(b);
        scale.compress(Bitmap.CompressFormat.PNG, 100, out);
        return out.toByteArray();
    }

    public Bitmap scaleDown(Bitmap b) {
        final double ratio = Math.min((double) 600 / b.getWidth(), (double) 600 / b.getHeight());
        final double width = Math.round(ratio * b.getWidth());
        final double height = Math.round(ratio * b.getHeight());
        return Bitmap.createScaledBitmap(b, (int) width, (int) height, true);
    }

}
