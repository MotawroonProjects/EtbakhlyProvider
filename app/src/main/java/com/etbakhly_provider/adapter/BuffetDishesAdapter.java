package com.etbakhly_provider.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.DishBuffetRowBinding;

import java.util.List;

public class BuffetDishesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> list;
    private Context context;
    private LayoutInflater inflater;
    private AppCompatActivity appCompatActivity;


    public BuffetDishesAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        appCompatActivity = (AppCompatActivity) context;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        DishBuffetRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.dish_buffet_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;

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
        private DishBuffetRowBinding binding;

        public MyHolder(DishBuffetRowBinding binding) {
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
