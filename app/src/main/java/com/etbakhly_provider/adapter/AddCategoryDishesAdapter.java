package com.etbakhly_provider.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.AddCategoryRowBinding;
import com.etbakhly_provider.databinding.AddSelectedDishCategoryBinding;
import com.etbakhly_provider.databinding.SelectedDishCategoryBinding;
import com.etbakhly_provider.model.BuffetModel;
import com.etbakhly_provider.uis.activity_dishes.DishesActivity;

import java.util.List;

public class AddCategoryDishesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int ADD_TYPE = 1;
    private final int NORMAL_TYPE = 2;

    private List<BuffetModel.Category> list;
    private Context context;
    private LayoutInflater inflater;
    private AppCompatActivity appCompatActivity;
    private int currentPos = 1;
    private int oldPos = currentPos;
    private MyHolder oldHolder;

    public AddCategoryDishesAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        appCompatActivity = (AppCompatActivity) context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == NORMAL_TYPE) {
            AddSelectedDishCategoryBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_selected_dish_category, parent, false);
            return new MyHolder(binding);
        } else {
            AddCategoryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_category_row, parent, false);
            return new AddHolder(binding);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyHolder) {
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

            myHolder.binding.imageEdit.setOnClickListener(view -> {
                currentPos = myHolder.getAdapterPosition();
                BuffetModel.Category category = list.get(currentPos);
                if (appCompatActivity instanceof DishesActivity) {
                    DishesActivity activity = (DishesActivity) appCompatActivity;
                    activity.openSheet(category, currentPos);
                }
            });
            myHolder.binding.imageDelete.setOnClickListener(view -> {
                currentPos = myHolder.getAdapterPosition();
                BuffetModel.Category category = list.get(currentPos);
                if (appCompatActivity instanceof DishesActivity) {
                    DishesActivity activity = (DishesActivity) appCompatActivity;
                    activity.deleteItemCategory(category, currentPos);
                }
            });


        } else if (holder instanceof AddHolder) {
            AddHolder addHolder = (AddHolder) holder;
            addHolder.itemView.setOnClickListener(view -> {
                if (appCompatActivity instanceof DishesActivity) {
                    DishesActivity activity = (DishesActivity) appCompatActivity;
                    activity.navigateToAddCategory();
                }
            });
        }


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
        private AddSelectedDishCategoryBinding binding;

        public MyHolder(AddSelectedDishCategoryBinding binding) {
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


    @Override
    public int getItemViewType(int position) {
        BuffetModel.Category category = list.get(position);
        if (category == null) {
            return ADD_TYPE;
        } else {
            return NORMAL_TYPE;
        }
    }

    public void updateList(List<BuffetModel.Category> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }

    public void updateItem(BuffetModel.Category category, int pos) {
        if (list != null && pos != -1) {
            list.set(pos, category);
            notifyItemChanged(pos);
        }

    }

    public void deleteItem(int pos) {
        if (list != null && pos != -1) {
            notifyItemRemoved(pos);

        }
    }


}
