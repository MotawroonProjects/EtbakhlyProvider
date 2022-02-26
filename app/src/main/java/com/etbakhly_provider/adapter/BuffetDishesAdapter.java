package com.etbakhly_provider.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.DishBuffetRowBinding;
import com.etbakhly_provider.model.DishModel;
import com.etbakhly_provider.uis.activity_dishes.DishesActivity;

import java.util.List;

public class BuffetDishesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DishModel> list;
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
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.editDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DishesActivity activity =(DishesActivity) appCompatActivity;
                activity.editDish(list.get(myHolder.getAbsoluteAdapterPosition()),myHolder.getAdapterPosition());
            }
        });
        myHolder.binding.deleteDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DishesActivity activity=(DishesActivity) appCompatActivity;
                activity.deleteDish(list.get(myHolder.getLayoutPosition()));
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
        private DishBuffetRowBinding binding;

        public MyHolder(DishBuffetRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

    }

    public void updateList(List<DishModel> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }
}
