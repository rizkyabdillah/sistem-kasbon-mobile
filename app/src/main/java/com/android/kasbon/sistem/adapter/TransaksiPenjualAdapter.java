package com.android.kasbon.sistem.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.databinding.ItemTransaksiPembeliBinding;
import com.android.kasbon.sistem.databinding.ItemTransaksiPenjualBinding;
import com.android.kasbon.sistem.model.TransaksiModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


public class TransaksiPenjualAdapter extends RecyclerView.Adapter<TransaksiPenjualAdapter.ViewHolder> {

    private ItemTransaksiPenjualBinding bindingPenjual;
    private List<DocumentSnapshot> list;

    public TransaksiPenjualAdapter(QuerySnapshot documentSnapshots) {
        this.list = documentSnapshots.getDocuments();
    }

    @NonNull
    @Override
    public TransaksiPenjualAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindingPenjual = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_transaksi_penjual,parent,false);
        return new ViewHolder(bindingPenjual);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull TransaksiPenjualAdapter.ViewHolder holder, int position) {
        TransaksiModel model = list.get(position).toObject(TransaksiModel.class);
        int green = ContextCompat.getColor(bindingPenjual.getRoot().getContext(), R.color.app_green);
        int red = ContextCompat.getColor(bindingPenjual.getRoot().getContext(), R.color.app_red);
        bindingPenjual.setTransaksi(model);
        bindingPenjual.textNominal.setTextColor(model.getAksi().equals("Bayar") ? green : red);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(ItemTransaksiPenjualBinding binding) {
            super(binding.getRoot());
        }
    }
}
