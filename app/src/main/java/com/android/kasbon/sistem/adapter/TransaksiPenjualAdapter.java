package com.android.kasbon.sistem.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.kasbon.sistem.R;
import com.android.kasbon.sistem.databinding.ItemTransaksiPenjualBinding;
import com.android.kasbon.sistem.model.OperationTransaksiModel;

import java.util.List;


public class TransaksiPenjualAdapter extends RecyclerView.Adapter<TransaksiPenjualAdapter.ViewHolder> {

    private ItemTransaksiPenjualBinding bindingPenjual;
    private List<OperationTransaksiModel> list;
    private Boolean isLimit = false;

    public TransaksiPenjualAdapter(List<OperationTransaksiModel> list, Boolean isLimit) {
        this.list = list;
        this.isLimit = isLimit;
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
        bindingPenjual.setOperation(list.get(position));
    }

    @Override
    public int getItemCount() {
        return isLimit ? (Math.min(list.size(), 5)) : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(ItemTransaksiPenjualBinding binding) {
            super(binding.getRoot());
        }
    }
}
