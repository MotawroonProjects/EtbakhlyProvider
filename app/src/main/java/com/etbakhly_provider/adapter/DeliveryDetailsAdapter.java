package com.etbakhly_provider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.DeliveryDetailsRowBinding;
import com.etbakhly_provider.model.RateModel;
import com.etbakhly_provider.model.ZoneCover;

import java.util.List;

public class DeliveryDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<ZoneCover> list;
    private Context context;
    private LayoutInflater inflater;

    public DeliveryDetailsAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DeliveryDetailsRowBinding binding= DataBindingUtil.inflate(inflater, R.layout.delivery_details_row,parent,false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder=(MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        DeliveryDetailsRowBinding binding;

        public MyHolder(DeliveryDetailsRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public void updateList(List<ZoneCover> list) {
        if (list != null) {
            this.list = list;
        } else {
            this.list.clear();
        }
        notifyDataSetChanged();
    }
}
