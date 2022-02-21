package com.etbakhly_provider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly_provider.R;

import com.etbakhly_provider.databinding.NewOrderItemBinding;
import com.etbakhly_provider.model.OrderModel;
import com.etbakhly_provider.uis.activities_home.fragments.FragmentNewOrders;

import java.util.List;

public class NewOrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<OrderModel> list;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private Fragment fragment;


    public NewOrdersAdapter(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewOrderItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.new_order_item, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.llNewDetails.setOnClickListener(view -> {
            FragmentNewOrders fragmentNewOrders=(FragmentNewOrders) fragment;
            fragmentNewOrders.navigateToDetails();
        });
        myHolder.binding.btnAccepted.setOnClickListener(view -> {
            FragmentNewOrders fragmentNewOrders = (FragmentNewOrders) fragment;
            fragmentNewOrders.changeStatus(list.get(holder.getLayoutPosition()), "approval");
        });
        myHolder.binding.btnReject.setOnClickListener(view -> {
            FragmentNewOrders fragmentNewOrders = (FragmentNewOrders) fragment;
            fragmentNewOrders.changeStatus(list.get(holder.getLayoutPosition()), "refusal");
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
        public NewOrderItemBinding binding;

        public MyHolder(NewOrderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void updateList(List<OrderModel> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }

}
