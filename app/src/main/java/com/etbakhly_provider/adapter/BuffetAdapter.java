package com.etbakhly_provider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.BuffetRowBinding;
import com.etbakhly_provider.model.BuffetModel;
import com.etbakhly_provider.uis.activity_buffet.BuffetActivity;
import com.etbakhly_provider.uis.activity_feats.FeastsActivity;

import java.util.List;

public class BuffetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BuffetModel> list;
    private Context context;
    private LayoutInflater inflater;
    private AppCompatActivity appCompatActivity;

    public BuffetAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        appCompatActivity = (AppCompatActivity) context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BuffetRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.buffet_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.itemView.setOnClickListener(view -> {
            if (appCompatActivity instanceof BuffetActivity){
                BuffetActivity activity=(BuffetActivity) appCompatActivity;
                activity.setItemData(list.get(myHolder.getAbsoluteAdapterPosition()),myHolder.getAdapterPosition());
            }else if (appCompatActivity instanceof FeastsActivity){
                FeastsActivity activity=(FeastsActivity) appCompatActivity;
                activity.setItemData(list.get(myHolder.getAbsoluteAdapterPosition()),myHolder.getAdapterPosition());
            }
        });
        myHolder.binding.llEdit.setOnClickListener(view -> {
            if (appCompatActivity instanceof BuffetActivity){
                BuffetActivity activity=(BuffetActivity) appCompatActivity;
                activity.editBuffet(list.get(myHolder.getAbsoluteAdapterPosition()),myHolder.getAdapterPosition());
            }else if (appCompatActivity instanceof FeastsActivity){
                FeastsActivity activity=(FeastsActivity) appCompatActivity;
                activity.editFeast(list.get(myHolder.getAbsoluteAdapterPosition()),myHolder.getAdapterPosition());
            }
        });
        myHolder.binding.llDelete.setOnClickListener(view -> {
            if (appCompatActivity instanceof BuffetActivity){
                BuffetActivity activity=(BuffetActivity) appCompatActivity;
                activity.deleteBuffet(list.get(myHolder.getLayoutPosition()));
            }else if (appCompatActivity instanceof FeastsActivity){
                FeastsActivity activity=(FeastsActivity) appCompatActivity;
                activity.deleteFeast(list.get(myHolder.getLayoutPosition()));
            }

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
        BuffetRowBinding binding;

        public MyHolder(BuffetRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void updateList(List<BuffetModel> list) {
        if (list != null) {
            this.list = list;
        } else {
            this.list.clear();
        }
        notifyDataSetChanged();
    }
}
