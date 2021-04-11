package com.android.kasbon.sistem.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.kasbon.sistem.model.AuthModel;
import com.android.kasbon.sistem.model.JaminanModel;
import com.android.kasbon.sistem.model.UserModel;
import com.android.kasbon.sistem.repository.UpdateRepository;
import com.google.android.gms.tasks.Task;

import java.util.Map;

public class UpdateViewModel extends AndroidViewModel {

    private final UpdateRepository repository;

    public UpdateViewModel(@NonNull Application application) {
        super(application);
        this.repository = new UpdateRepository();
    }

    public MutableLiveData<Task<Void>> updateEmailUser(AuthModel authModel, String password) {
        return repository.updateEmailUser(authModel, password);
    }

    public MutableLiveData<Task<Void>> updatePasswordUser(AuthModel authModel, String password) {
        return repository.updatePasswordUser(authModel, password);
    }

    public MutableLiveData<Task<Void>> updateBatchUserJaminan(UserModel user, JaminanModel jaminan, String idUser) {
        return repository.updateBatchUserJaminan(user, jaminan, idUser);
    }

    public MutableLiveData<Task<Void>> updateUriFoto(String uri, String idUser) {
        return repository.updateUriFoto(uri, idUser);
    }

    public MutableLiveData<Task<Void>> updateHargaEmas(String hargaEmas) {
        return repository.updateHargaEmas(hargaEmas);
    }

    public MutableLiveData<Task<Void>> updateBatchTransaksiQR(String idTransaksi, String idUser, int limitKredit) {
        return repository.updateBatchTransaksiQR(idTransaksi, idUser, limitKredit);
    }

    public MutableLiveData<Task<Void>> updateBatchSetLunas(String idTransaksi, String idUser, int limitKredit) {
        return repository.updateBatchSetLunas(idTransaksi, idUser, limitKredit);
    }


}
