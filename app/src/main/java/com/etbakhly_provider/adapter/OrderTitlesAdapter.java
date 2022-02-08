package com.etbakhly_provider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.OrderTitlesRowBinding;
import com.etbakhly_provider.model.OrderModel;

import java.util.List;

public class OrderTitlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> list;
    private Context context;
    private LayoutInflater inflater;
    private OrderDetailsAdapter detailsAdapter;


    public OrderTitlesAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderTitlesRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.order_titles_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;

        detailsAdapter = new OrderDetailsAdapter(context);
        myHolder.binding.recyclerOrderDetails.setLayoutManager(new GridLayoutManager(context, 2));
        myHolder.binding.recyclerOrderDetails.setAdapter(detailsAdapter);

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 3;
        }
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private OrderTitlesRowBinding binding;

        public MyHolder(OrderTitlesRowBinding binding) {
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
