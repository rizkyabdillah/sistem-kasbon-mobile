package com.android.kasbon.sistem.view.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.adapter.TransaksiPembeliAdapter;
import com.android.kasbon.sistem.adapter.TransaksiPenjualAdapter;
import com.android.kasbon.sistem.databinding.ActivityAllTransaksiBinding;
import com.android.kasbon.sistem.model.JaminanModel;
import com.android.kasbon.sistem.model.OperationTransaksiModel;
import com.android.kasbon.sistem.model.UserModel;
import com.android.kasbon.sistem.utilitas.AlertInfo;
import com.android.kasbon.sistem.utilitas.AlertProgress;
import com.android.kasbon.sistem.viewmodel.InsertViewModel;
import com.android.kasbon.sistem.viewmodel.ReadViewModel;
import com.android.kasbon.sistem.viewmodel.UpdateViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TransaksiAllActivity extends AppCompatActivity implements TransaksiPenjualAdapter.onSelectedData{

    private ReadViewModel readViewModel;
    private UpdateViewModel updateViewModel;

    private ActivityAllTransaksiBinding binding;
    private FirebaseUser firebaseUser;
    private final LifecycleOwner OWNER = this;
    private List<OperationTransaksiModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_transaksi);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final boolean isReadDataSeller = getIntent().getBooleanExtra("IS_READ_SELLER", true);

        readViewModel = ViewModelProviders.of(this).get(ReadViewModel.class);
        updateViewModel = ViewModelProviders.of(this).get(UpdateViewModel.class);

        list = new ArrayList<>();

        binding.recyclerViewAllTransaksi.setHasFixedSize(true);
        binding.recyclerViewAllTransaksi.setLayoutManager(new LinearLayoutManager(this));

        // ================

        MutableLiveData<QuerySnapshot> liveData = isReadDataSeller ?
            readViewModel.readDataTransaksiAll()
                :
            readViewModel.readDataTransaksiUser(firebaseUser.getUid()
        );

        // ================

        readViewModel.readAllDataNameUser().observe(OWNER, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(Map<String, Object> name) {
                liveData.observe(OWNER, new Observer<QuerySnapshot>() {
                    @Override
                    public void onChanged(QuerySnapshot value) {
                        list.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            if(!doc.getString("id_user").equals("TEMP")) {
                                try {
                                    OperationTransaksiModel model = new OperationTransaksiModel();
                                    model.setIdTransaksi(doc.getId());
                                    model.setIdUser(doc.getString("id_user"));
                                    model.setNama(name.get(doc.getString("id_user")).toString());
                                    model.setJumlah(doc.getDouble("total"));
                                    model.setStatusBayar(doc.getBoolean("status_bayar"));
                                    model.setStatusJual(doc.getBoolean("status_jual"));
                                    model.setTanggal(doc.getString("tanggal"));
                                    list.add(model);
                                } catch (Exception e) {
                                    Log.d("ERROR", e.getMessage());
                                }
                            }
                        }

                        binding.recyclerViewAllTransaksi.setAdapter(
                            isReadDataSeller ?
                                new TransaksiPenjualAdapter(list, false, TransaksiAllActivity.this)
                                    :
                                new TransaksiPembeliAdapter(list, false)
                        );
                    }
                });
            }
        });

        // ================

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public void onSelected(int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Konfirmasi");
        alert.setMessage("Apakah kasbon ini sudah lunas?");
        alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertProgress progress = new AlertProgress(TransaksiAllActivity.this, "Sedang mengupdate data");
                progress.showDialog();
                updateViewModel.updateBatchSetLunas(
                    list.get(position).getIdTransaksi(), list.get(position).getIdUser(),list.get(position).getJumlah()
                ).observe(OWNER, new Observer<Task<Void>>() {
                    @Override
                    public void onChanged(Task<Void> task) {
                        if(task.isSuccessful()) {
                            progress.dismissDialog();
                            dialog.dismiss();
                        } else {
                            AlertInfo info = new AlertInfo(TransaksiAllActivity.this, task.getException().getMessage());
                            info.showDialog();
                        }
                    }
                });
            }
        });
        alert.create();
        alert.show();
    }
}