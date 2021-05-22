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
import com.android.kasbon.sistem.databinding.ItemKeranjangBinding;
import com.android.kasbon.sistem.databinding.ItemTransaksiPembeliBinding;
import com.android.kasbon.sistem.model.ItemKeranjangModel;
import com.android.kasbon.sistem.model.TransaksiModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.ViewHolder> {

    private ItemKeranjangBinding binding;
    private List<ItemKeranjangModel> list;
    private final KeranjangAdapter.onSelectedData onSelectedData;

    public interface onSelectedData{
        void onSelected(int position);
    }

    public KeranjangAdapter(List<ItemKeranjangModel> list, KeranjangAdapter.onSelectedData onSelectedData) {
        this.list = list;
        this.onSelectedData = onSelectedData;
    }

    @NonNull
    @Override
    public KeranjangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_keranjang, parent,false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull KeranjangAdapter.ViewHolder holder, int position) {
        binding.setItem(list.get(position));
        binding.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectedData.onSelected(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(ItemKeranjangBinding binding) {
            super(binding.getRoot());
        }
    }
}
