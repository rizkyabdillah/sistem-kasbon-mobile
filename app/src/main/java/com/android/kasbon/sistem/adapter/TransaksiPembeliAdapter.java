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
import com.android.kasbon.sistem.model.OperationTransaksiModel;
import com.android.kasbon.sistem.model.TransaksiModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


public class TransaksiPembeliAdapter extends RecyclerView.Adapter<TransaksiPembeliAdapter.ViewHolder> {

    private ItemTransaksiPembeliBinding bindingPembeli;
    private List<OperationTransaksiModel> list;
    private Boolean isLimit = false;

    public TransaksiPembeliAdapter(List<OperationTransaksiModel> list, Boolean isLimit) {
        this.list = list;
        this.isLimit = isLimit;
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
        bindingPembeli.setOperation(list.get(position));
    }

    @Override
    public int getItemCount() {
        return isLimit ? (Math.min(list.size(), 5)) : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(ItemTransaksiPembeliBinding binding) {
            super(binding.getRoot());
        }
    }
}
