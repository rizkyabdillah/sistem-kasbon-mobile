package com.android.kasbon.sistem.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.adapter.TransaksiPembeliAdapter;
import com.android.kasbon.sistem.adapter.TransaksiPenjualAdapter;
import com.android.kasbon.sistem.databinding.ActivityAllTransaksiBinding;
import com.android.kasbon.sistem.model.OperationTransaksiModel;
import com.android.kasbon.sistem.viewmodel.ReadViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TransaksiAllActivity extends AppCompatActivity {

    private ActivityAllTransaksiBinding binding;
    private ReadViewModel readViewModel;
    private FirebaseUser firebaseUser;
    private final LifecycleOwner OWNER = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_transaksi);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final boolean isReadDataSeller = getIntent().getBooleanExtra("IS_READ_SELLER", true);

        readViewModel = ViewModelProviders.of(this).get(ReadViewModel.class);

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
                        List<OperationTransaksiModel> list = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            if(!doc.getString("id_user").equals("TEMP")) {
                                try {
                                    OperationTransaksiModel model = new OperationTransaksiModel();
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
                                new TransaksiPenjualAdapter(list, false)
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
}