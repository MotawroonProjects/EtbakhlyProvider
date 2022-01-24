package com.etbakhly_provider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.etbakhly_provider.R;
import com.etbakhly_provider.databinding.LatestProductRowBinding;
import com.etbakhly_provider.databinding.ProductOfferRowBinding;
import com.etbakhly_provider.model.ProductModel;
import com.etbakhly_provider.model.UserModel;
import com.etbakhly_provider.uis.activity_home.fragments_home_navigaion.FragmentHome;

import java.util.List;

public class OfferProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ProductModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;
    private UserModel userModel;

    public OfferProductAdapter(Context context, Fragment fragment, UserModel userModel) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.fragment = fragment;
        this.userModel = userModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductOfferRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.product_offer_row, parent, false);
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
                if (fragment instanceof FragmentHome) {
                    FragmentHome fragmentHome = (FragmentHome) fragment;
                    fragmentHome.showProductDetials(list.get(holder.getLayoutPosition()).getId());
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
        public ProductOfferRowBinding binding;

        public MyHolder(ProductOfferRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<ProductModel> list) {
        if (list != null) {
            this.list = list;

        }
        notifyDataSetChanged();
    }
}
