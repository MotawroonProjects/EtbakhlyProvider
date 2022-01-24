package com.etbakhly_provider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.OrderProductRow2Binding;
import com.etbakhly_provider.databinding.OrderProductRowBinding;
import com.etbakhly_provider.model.OrderModel;

import java.util.List;

public class Order2ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<OrderModel.Detials> list;
    private Context context;
    private LayoutInflater inflater;


    public Order2ProductAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        //  this.fragment_main=fragment_main;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        OrderProductRow2Binding binding = DataBindingUtil.inflate(inflater, R.layout.order_product_row2, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;


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

    public class MyHolder extends RecyclerView.ViewHolder {
        public OrderProductRow2Binding binding;

        public MyHolder(@NonNull OrderProductRow2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<OrderModel.Detials> list) {
        if (list != null) {
            this.list = list;

        }
        notifyDataSetChanged();
    }


}
