package com.etbakhly_provider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly_provider.R;

import com.etbakhly_provider.databinding.CompletedOrderItemBinding;
import com.etbakhly_provider.model.OrderModel;
import com.etbakhly_provider.uis.activities_home.fragments.FragmentCompletedOrders;

import java.util.List;

public class CompletedOrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<OrderModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;

    public CompletedOrdersAdapter(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CompletedOrderItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.completed_order_item, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.llCompletedDetails.setOnClickListener(view -> {
            FragmentCompletedOrders fragmentCompletedOrders = (FragmentCompletedOrders) fragment;
            fragmentCompletedOrders.navigateToDetails(list.get(myHolder.getAdapterPosition()));
        });
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
        public CompletedOrderItemBinding binding;

        public MyHolder(CompletedOrderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void updateList(List<OrderModel> list) {
        if (list != null) {
            this.list = list;

        } else {
            this.list.clear();
        }
        notifyDataSetChanged();
    }
}
