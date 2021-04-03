package com.android.kasbon.sistem.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.databinding.ItemTransaksiPenjualBinding;
import com.android.kasbon.sistem.model.TransaksiModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


public class TransaksiPenjualAdapter  extends RecyclerView.Adapter<TransaksiPenjualAdapter.ViewHolder> {

    private ItemTransaksiPenjualBinding binding;
    private List<DocumentSnapshot> list;

    public TransaksiPenjualAdapter(QuerySnapshot documentSnapshots) {
        list = documentSnapshots.getDocuments();
    }

    @NonNull
    @Override
    public TransaksiPenjualAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_transaksi_penjual,parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull TransaksiPenjualAdapter.ViewHolder holder, int position) {
        TransaksiModel model = list.get(position).toObject(TransaksiModel.class);
        binding.setTransaksi(model);
        int green = ContextCompat.getColor(binding.getRoot().getContext(), R.color.app_green);
        int red = ContextCompat.getColor(binding.getRoot().getContext(), R.color.app_red);
        binding.textNominal.setTextColor(model.getAksi().equals("Bayar") ? green : red);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(ItemTransaksiPenjualBinding binding) {
            super(binding.getRoot());
        }
    }
}
