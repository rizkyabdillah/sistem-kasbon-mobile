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
import android.view.View;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.adapter.TransaksiPembeliAdapter;
import com.android.kasbon.sistem.adapter.TransaksiPenjualAdapter;
import com.android.kasbon.sistem.databinding.ActivityAllTransaksiBinding;
import com.android.kasbon.sistem.viewmodel.ReadViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QuerySnapshot;

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

        MutableLiveData<QuerySnapshot> liveData = isReadDataSeller ?
            readViewModel.readDataTransaksiAll()
                :
            readViewModel.readDataTransaksiUser(firebaseUser.getUid());

        liveData.observe(OWNER, new Observer<QuerySnapshot>() {
            @Override
            public void onChanged(QuerySnapshot documentSnapshots) {
                binding.recyclerViewAllTransaksi.setAdapter(
                    isReadDataSeller ?
                        new TransaksiPenjualAdapter(documentSnapshots)
                            :
                        new TransaksiPembeliAdapter(documentSnapshots)
                );
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}