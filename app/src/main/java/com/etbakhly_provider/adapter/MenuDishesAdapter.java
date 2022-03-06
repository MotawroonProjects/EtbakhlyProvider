package com.etbakhly_provider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.DishBuffetRow2Binding;
import com.etbakhly_provider.model.DishModel;
import com.etbakhly_provider.uis.activity_buffet_details.BuffetDetailsActivity;

import java.util.List;

public class MenuDishesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DishModel> list;
    private Context context;
    private BuffetDetailsActivity activity;
    private int mainCategoryPos;

    public MenuDishesAdapter(Context context,int mainCategoryPos) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        activity = (BuffetDetailsActivity) context;
        this.mainCategoryPos = mainCategoryPos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DishBuffetRow2Binding binding = DataBindingUtil.inflate(inflater, R.layout.dish_buffet_row2, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.itemView.setOnClickListener(v -> {
            activity.navigateToUpdateDish(list.get(myHolder.getAdapterPosition()),mainCategoryPos);
        });

        myHolder.binding.imageDelete.setOnClickListener(v -> {
            activity.deleteDish(list.get(myHolder.getAdapterPosition()),mainCategoryPos,myHolder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public static class MyHolder extends RecyclerView.ViewHolder {
        private DishBuffetRow2Binding binding;

        public MyHolder(DishBuffetRow2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

    private LayoutInflater inflater;

    public void updateList(List<DishModel> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }

}
