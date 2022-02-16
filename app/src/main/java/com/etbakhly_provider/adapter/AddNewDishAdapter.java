package com.etbakhly_provider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.AddDishRowBinding;


import java.util.List;

public class AddNewDishAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Object> list;
    private Context context;
    private LayoutInflater inflater;

    public AddNewDishAdapter(Context context) {
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AddDishRowBinding binding= DataBindingUtil.inflate(inflater, R.layout.add_dish_row,parent,false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder=(MyHolder) holder;
    }

    @Override
    public int getItemCount() {
        if (list!=null){
            return list.size();
        }else {
            return 7;
        }
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        AddDishRowBinding binding;

        public MyHolder(AddDishRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public void updateList(List<Object> list) {
        if (list != null) {
            this.list = list;
        } else {
            this.list.clear();
        }
        notifyDataSetChanged();
    }
}
