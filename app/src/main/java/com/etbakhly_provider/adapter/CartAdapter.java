package com.etbakhly_provider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.CartRowBinding;
import com.etbakhly_provider.model.ItemCartModel;
import com.etbakhly_provider.uis.activity_cart.CartActivity;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private CartActivity activity;
    private List<ItemCartModel> list;
    private Context context;
    private LayoutInflater inflater;


    public CartAdapter(List<ItemCartModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        //  this.fragment_main=fragment_main;
        activity = (CartActivity) context;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        CartRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.cart_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;


        myHolder.binding.setModel(list.get(position));


        myHolder.binding.imageIncrease.setOnClickListener(v -> {
            ItemCartModel model2 = list.get(myHolder.getAdapterPosition());

            int amount = model2.getQty() + 1;
            model2.setQty(amount);
            model2.setTotal_price((model2.getProduct_price() * amount) + model2.getWay_price() + model2.getWrap_price());
            activity.increase_decrease(model2, myHolder.getAdapterPosition());
        });

        myHolder.binding.imageDecrease.setOnClickListener(v -> {
            ItemCartModel model2 = list.get(myHolder.getAdapterPosition());

            if (model2.getQty() > 1) {
                int amount = model2.getQty() - 1;
                model2.setQty(amount);
                model2.setTotal_price((model2.getProduct_price() * amount) + model2.getWay_price() + model2.getWrap_price());
                activity.increase_decrease(model2, myHolder.getAdapterPosition());
            }

        });

        myHolder.binding.imgRemove.setOnClickListener(v -> {
            activity.deleteItem(myHolder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public CartRowBinding binding;

        public MyHolder(@NonNull CartRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
