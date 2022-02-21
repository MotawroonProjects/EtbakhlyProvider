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
import com.etbakhly_provider.databinding.PendingOrderItemBinding;
import com.etbakhly_provider.model.OrderModel;
import com.etbakhly_provider.uis.activities_home.fragments.FragmentPendingOrders;

import java.util.List;

public class PendingOrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<OrderModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;

    public PendingOrdersAdapter(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PendingOrderItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.pending_order_item,parent,false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder=(MyHolder)holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.llPendingDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentPendingOrders fragmentPendingOrders=(FragmentPendingOrders) fragment;
                fragmentPendingOrders.navigateToDetails();
            }
        });
        myHolder.binding.btnPrepared.setOnClickListener(view -> {
            FragmentPendingOrders fragmentPendingOrders =(FragmentPendingOrders) fragment;
            OrderModel orderModel = list.get(holder.getLayoutPosition());
            String status ="";
            if (orderModel.getStatus_order().equals("approval")){
                status ="making";
            }else if (orderModel.getStatus_order().equals("making")){
                status = "delivery";
            }

            fragmentPendingOrders.changeStatus(orderModel.id,status);
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

    public static class MyHolder extends RecyclerView.ViewHolder{
        public PendingOrderItemBinding binding;
        public MyHolder(PendingOrderItemBinding binding)
        {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
    public void updateList(List<OrderModel> list) {
        if (list != null) {
            this.list = list;

        }
        else{
            this.list .clear();
        }
        notifyDataSetChanged();
    }


}
