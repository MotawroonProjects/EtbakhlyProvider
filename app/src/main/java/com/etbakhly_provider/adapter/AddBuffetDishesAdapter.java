package com.etbakhly_provider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.AddBuffetDishRowBinding;
import com.etbakhly_provider.model.BuffetModel;
import com.etbakhly_provider.uis.activity_add_buffet.AddBuffetActivity;

import java.util.List;

public class AddBuffetDishesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BuffetModel.Category> list;
    private Context context;
    private LayoutInflater inflater;

    public AddBuffetDishesAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AddBuffetDishRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_buffet_dish_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        BuffetDishesDetailsAdapter buffetDetailsAdapter = new BuffetDishesDetailsAdapter(context);
        myHolder.binding.recAddBuffetDetails.setLayoutManager(new GridLayoutManager(context, 2));
        buffetDetailsAdapter.updateList(list.get(position).getDishes_buffet());
        myHolder.binding.recAddBuffetDetails.setAdapter(buffetDetailsAdapter);

        myHolder.binding.llAddNew.setOnClickListener(view -> {
            AddBuffetActivity addBuffetActivity = (AddBuffetActivity) context;
            addBuffetActivity.navigateToAddNewBuffetDish();
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private AddBuffetDishRowBinding binding;

        public MyHolder(AddBuffetDishRowBinding binding) {
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
