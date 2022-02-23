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
import com.etbakhly_provider.databinding.AddCategoryRowBinding;
import com.etbakhly_provider.databinding.SelectedDishCategoryBinding;
import com.etbakhly_provider.model.BuffetModel;
import com.etbakhly_provider.uis.activity_dishes.DishesActivity;

import java.util.List;

public class CategoryDishesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BuffetModel.Category> list;
    private Context context;
    private LayoutInflater inflater;
    private AppCompatActivity appCompatActivity;
    private int currentPos = 0;
    private int oldPos = currentPos;
    private RecyclerView.ViewHolder oldHolder;

    public CategoryDishesAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        appCompatActivity = (AppCompatActivity) context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SelectedDishCategoryBinding binding = DataBindingUtil.inflate(inflater, R.layout.selected_dish_category, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        if (oldHolder == null) {
            oldHolder = myHolder;
        }
        myHolder.itemView.setOnClickListener(view -> {
            if (oldHolder != null) {
                BuffetModel.Category oldCategory = list.get(oldPos);
                oldCategory.setSelected(false);
                list.set(oldPos, oldCategory);
                MyHolder oHolder = (MyHolder) oldHolder;
                oHolder.binding.setModel(oldCategory);

            }
            currentPos = myHolder.getAdapterPosition();
            BuffetModel.Category category = list.get(currentPos);
            category.setSelected(true);
            list.set(currentPos, category);
            myHolder.binding.setModel(category);

            oldHolder = myHolder;
            oldPos = currentPos;

            if (appCompatActivity instanceof DishesActivity) {
                DishesActivity activity = (DishesActivity) appCompatActivity;
                activity.setItemCategory(category, currentPos);
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
        private SelectedDishCategoryBinding binding;

        public MyHolder(SelectedDishCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

    }

    public static class AddHolder extends RecyclerView.ViewHolder {
        private AddCategoryRowBinding binding;

        public AddHolder(AddCategoryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

    }


    public void updateList(List<BuffetModel.Category> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }
}
