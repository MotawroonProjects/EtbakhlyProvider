package com.etbakhly_provider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.FavouriteRowBinding;
import com.etbakhly_provider.model.ProductModel;
import com.etbakhly_provider.model.UserModel;
import com.etbakhly_provider.uis.activity_favourite.FavouriteActivity;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ProductModel> list;
    private Context context;
    private LayoutInflater inflater;

    private UserModel userModel;
    public FavouriteAdapter(Context context,UserModel userModel) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.userModel=userModel;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FavouriteRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.favourite_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.setUsermodel(userModel);
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(context instanceof FavouriteActivity){
                    FavouriteActivity favouriteActivity=(FavouriteActivity) context;
                    favouriteActivity.showdetials(list.get(holder.getLayoutPosition()));
                }
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
        public FavouriteRowBinding binding;

        public MyHolder(FavouriteRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<ProductModel> list) {
        if (list != null) {
            this.list = list;

        }
        else{
            this.list .clear();
        }
        notifyDataSetChanged();
    }
}
