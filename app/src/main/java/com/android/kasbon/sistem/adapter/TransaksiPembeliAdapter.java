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


public class TransaksiPembeliAdapter extends RecyclerView.Adapter<TransaksiPembeliAdapter.ViewHolder> {

    private ItemTransaksiPembeliBinding bindingPembeli;
    private List<DocumentSnapshot> list;

    public TransaksiPembeliAdapter(QuerySnapshot documentSnapshots) {
        this.list = documentSnapshots.getDocuments();
    }

    @NonNull
    @Override
    public TransaksiPembeliAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindingPembeli = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_transaksi_pembeli,parent,false);
        return new ViewHolder(bindingPembeli);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull TransaksiPembeliAdapter.ViewHolder holder, int position) {
        TransaksiModel model = list.get(position).toObject(TransaksiModel.class);
        int green = ContextCompat.getColor(bindingPembeli.getRoot().getContext(), R.color.app_green);
        int red = ContextCompat.getColor(bindingPembeli.getRoot().getContext(), R.color.app_red);
        bindingPembeli.setTransaksi(model);
        bindingPembeli.textNominal.setTextColor(model.getAksi().equals("Bayar") ? green : red);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(ItemTransaksiPembeliBinding binding) {
            super(binding.getRoot());
        }
    }
}
