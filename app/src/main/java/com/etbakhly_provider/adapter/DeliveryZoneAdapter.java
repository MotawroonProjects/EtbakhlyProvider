package com.etbakhly_provider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.DeliveryZoneRowBinding;


import java.util.List;

public class DeliveryZoneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Object> list;
    private Context context;
    private LayoutInflater inflater;

    public DeliveryZoneAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DeliveryZoneRowBinding binding= DataBindingUtil.inflate(inflater, R.layout.delivery_zone_row,parent,false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder=(MyHolder) holder;
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 10;
        }
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private DeliveryZoneRowBinding binding;

        public MyHolder(DeliveryZoneRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
    public void updateList(List<Object> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }
}